package com.xp.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xp.common.Exception.SPException;
import com.xp.common.enums.ExceptionEnum;
import com.xp.common.vo.PageResult;
import com.xp.item.entity.*;
import com.xp.item.mapper.SKUMapper;
import com.xp.item.mapper.SPUDetailMapper;
import com.xp.item.mapper.SPUMapper;
import com.xp.item.mapper.StockMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GoodsService {
    @Autowired
    private SPUMapper spuMapper;
    @Autowired
    private SPUDetailMapper detailMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SKUMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;
    public PageResult<SPU> findSPUByPage(String key, Integer page, Integer rows, Boolean saleable) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(SPU.class);
        Example.Criteria criteria = example.createCriteria();
        //根据title字段过滤
        if (StringUtils.isNotBlank(key))
        {
            criteria.andLike("title","%"+key+"%");
        }
        //上下架过滤
        if (saleable != null){
            criteria.andEqualTo("saleable",saleable);
        }
        //默认排序
        example.setOrderByClause("last_update_time DESC");
        //查询
        List<SPU> spus = spuMapper.selectByExample(example);
        //判断是否查询到值
        if(CollectionUtils.isEmpty(spus))
        {
            throw new SPException(ExceptionEnum.GOODS_NOT_FOND);
        }
        //解析分类和品牌的名称
        loadCategoryAndBrandName(spus);

        //解析分页结果
        PageInfo<SPU> info = new PageInfo<>(spus);
        //返回结果
        return new PageResult<>(info.getTotal(),spus);
    }

    private void loadCategoryAndBrandName(List<SPU> spus) {
        for (SPU spu : spus) {
            //处理分类名称
            List<String> names = categoryService.findByIds(Arrays.asList(spu.getCid1(), spu.getCid2(),
                    spu.getCid3())).stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names,"/"));
            //处理品牌名称
            spu.setBname(brandService.findByIds(spu.getBrandId()).getName());
        }
    }
    @Transactional
    public void saveGoods(SPU spu) {
        //新增spu
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(false);
        spu.setValid(false);
        int count = spuMapper.insert(spu);
        if (count != 1 ){
            throw new SPException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
        //新增detail
        SPUDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetail.getGenericSpec();
        detailMapper.insert(spuDetail);
        //定义库存集合
        List<Stock> stocks=new ArrayList<>();
        //新增sku
        List<SKU> skus = spu.getSkus();
        for (SKU sku : skus) {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());
             count = skuMapper.insert(sku);
            if (count!= 1){
                throw new SPException(ExceptionEnum.GOODS_SAVE_ERROR);
            }
            //新增stock
            Stock stock =new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            //批量新增库存
           stocks.add(stock);
        }
        count = stockMapper.insertList(stocks);
        if (count!= stocks.size()){
            throw new SPException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
    }


    private void saveSkuAndStock(SPU spu) {
        int count;//新增sku
        List<SKU> skus = spu.getSkus();
        List<Stock> stockList = new ArrayList<>();
        for (SKU sku : skus) {
            sku.setId(null);
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());

            count = skuMapper.insert(sku);
            if(count!=1)
                throw new SPException(ExceptionEnum.GOODS_SAVE_ERROR);

            //新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }
        //批量新增库存
        count = stockMapper.insertList(stockList);
        if(count!=stockList.size())
            throw new SPException(ExceptionEnum.GOODS_SAVE_ERROR);
    }



    /**
     * 更新商品信息
     * @param spu
     */
    public void updateGoods(SPU spu) {
        if (spu.getId() == null)
            throw new SPException(ExceptionEnum.GOODS_UPDATE_ERROR);
        SKU sku = new SKU();
        sku.setSpuId(spu.getId());
        //查询sku
        List<SKU> skuList = skuMapper.select(sku);
        if (!CollectionUtils.isEmpty(skuList)) {
            //删除sku
            skuMapper.delete(sku);
            //删除库存
            List<Long> ids = skuList.stream().map(SKU::getId).collect(Collectors.toList());
            stockMapper.deleteByIdList(ids);
        }
        //新增sku和库存
        saveSkuAndStock(spu);

        //修改spu
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setCreateTime(null);
        spu.setLastUpdateTime(new Date());

        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count != 1)
            throw new SPException(ExceptionEnum.GOODS_UPDATE_ERROR);
        //修改detail
        detailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        if (count != 1)
            throw new SPException(ExceptionEnum.GOODS_UPDATE_ERROR);


    }



    public SPUDetail findDetailById(Long spuId) {
        SPUDetail spuDetail = detailMapper.selectByPrimaryKey(spuId);
        if (spuDetail == null){
            throw new SPException(ExceptionEnum.GOODS_DETAIL_NOT_FOUND);
        }
        return spuDetail;
    }


    public List<SKU> findSkuBySpuId(Long spuId) {
        //查询sku
        SKU sku = new SKU();
        sku.setSpuId(spuId);
        List<SKU> skuList = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skuList))
            throw new SPException(ExceptionEnum.GOODS_SKU_NOT_FOUND);

        //查询库存
        List<Long> ids = skuList.stream().map(SKU::getId).collect(Collectors.toList());
        List<Stock> stockList = stockMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(stockList))
            throw new SPException(ExceptionEnum.GOODS_SKU_NOT_FOUND);

        //把stock变成一个map，其key：skuId,值：库存值
        Map<Long, Integer> stockMap = stockList.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skuList.forEach(s ->s.setStock(stockMap.get(s.getId())));
        return skuList;
    }

//    public List<SKU> findSkuBySpuId(Long spuId) {
//        //查询SKU
//        SKU sku=new SKU();
//        sku.setSpuId(spuId);
//        List<SKU> skuList = skuMapper.select(sku);
//        if (CollectionUtils.isEmpty(skuList))
//        {
//            throw new SPException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
//        }
//        //查询库存
////        for (SKU s : skus) {
////            Stock stock = stockMapper.selectByPrimaryKey(s.getId());
////            if (stock==null){
////                throw new SPException(ExceptionEnum.GOODS_STOCK_NOT_FOUND);
////            }
////            s.setStock(stock.getStock());
////        }
//
//        //库存查询
//        List<Long> ids = skuList.stream().map(SKU::getId).collect(Collectors.toList());
//        List<Stock> stockList = stockMapper.selectByIdList(ids);
//        if (CollectionUtils.isEmpty(stockList)) {
//            throw new SPException(ExceptionEnum.GOODS_STOCK_NOT_FOUND);
//        }
//        //把stock变成一个map，其key是:sku的id，值是库存值
//        Map<Long, Integer> stockMap = stockList.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
//        skuList.forEach(s -> s.setStock(stockMap.get(s.getId())));
//        return skuList;
//    }

}
