package com.xp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xp.client.BrandClient;
import com.xp.client.CategoryClient;
import com.xp.client.GoodsClient;
import com.xp.client.SpecificationClient;
import com.xp.entity.Goods;
import com.xp.item.entity.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    private static final ObjectMapper MAPPER=new ObjectMapper();

    public Goods buildGoods(SPU spu) throws JsonProcessingException {
        //创建goods对象
        Goods goods = new Goods();

        //根据分类的id查询分类名称
        List<String> names = categoryClient.findNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

        //根据品牌的id查询品牌的名称
        Brand brand = brandClient.findBrandById(spu.getBrandId());

        //根据spuId查询所有的sku
        List<SKU> skus = goodsClient.findSkuBySpuId(spu.getId());
        //初始化价格集合来收集所有sku的价格
        List<Long> prices=new ArrayList<>();
        //收集sku的必要字段信息
        List<Map<String,Object>>skuMapList=new ArrayList<>();
        skus.forEach(sku -> {
            prices.add(sku.getPrice());

            Map<String,Object>map=new HashMap<>();
            map.put("id",sku.getId());
            map.put("title",sku.getTitle());
            map.put("price",sku.getPrice());
            //获取sku中的图片，数据库中的图片可能是多张，多张是以逗号进行分割，所以也以逗号进行切割返回图片数组，获取第一张图片
            map.put("image",StringUtils.isBlank(sku.getImages())?"":StringUtils.split(sku.getImages(),"")[0]);

            skuMapList.add(map);
        });
        //根据spu中的cid3查询出所有的规格参数
        List<SpecParam> params = specificationClient.findParamList(null, spu.getCid3(), null, true);

        //根据spuId查询spuDetail
        SPUDetail spuDetail = goodsClient.findDetailById(spu.getId());
        //把通用的规格参数值，进行反序列化
        Map<String, Object> genericSpecMap = MAPPER.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<String, Object>>() {});
        //把特殊的规格参数，进行反序列化
        Map<String, List<Object>> specialSpecMap = MAPPER.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<String, List<Object>>>() {
        });
        // 定义map接收{规格参数名，规格参数值}
        Map<String, Object> specs = new HashMap<>();
        params.forEach(param->{
            //如果是通用类型的参数，是否是通用的规格参数
            if(param.getGeneric()){
                //如果是通用类型的参数，从genericSpecMap获取规格参数值
                String value = genericSpecMap.get(param.getId().toString()).toString();
                //判断是否是数值类型，如果是数值类型，应该返回一个区间
                if (param.getNumeric()){
                    value=chooseSegment(value,param);
                }
                specs.put(param.getName(),value);
            }
            else {
                //如果是特殊的规格参数，从specialSpecMap中获取值
                List<Object> value = specialSpecMap.get(param.getId().toString());
                specs.put(param.getName(),value);
            }

        });

        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());
        //拼接All字段，需要分类名称以及品牌名称
        goods.setAll(spu.getTitle()+" "+ StringUtils.join(names," ") +" "+brand);
        //获取spu下的所有价格
        goods.setPrice(prices);
        //获取spu下的所有sku，并转换为json格式
        goods.setSkus(MAPPER.writeValueAsString(skuMapList));
        //获取所有的详细信息规格参数{name：value}
        goods.setSpecs(specs);
        //获取品牌id
        goods.setBrandId(goods.getBrandId());
        return goods;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }
}
