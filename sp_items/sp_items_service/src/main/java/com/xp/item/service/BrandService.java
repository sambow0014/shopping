package com.xp.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xp.common.Exception.SPException;
import com.xp.common.enums.ExceptionEnum;
import com.xp.common.vo.PageResult;
import com.xp.item.entity.Brand;
import com.xp.item.mapper.BrandMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;


import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;


    public PageResult<Brand> findBrandByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //分页,利用分页助手分页
        PageHelper.startPage(page,rows);
//       select * from tb_brand where name like "%x%";
        //过滤
        Example example=new Example(Brand.class);
        if(StringUtils.isNotBlank(key))
        {
            example.createCriteria().orLike("name","%"+key+"%").orEqualTo("letter",key.toUpperCase());
        }
        //排序
//        if (StringUtils.isNotBlank(sortBy))
//        {
//            String orderByClause=sortBy + (desc ? "desc" :  "asc");
//            example.setOrderByClause(orderByClause);
//        }
        //查询
        List<Brand> list = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list))
        {
            throw new SPException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //解析分页结果
        PageInfo<Brand> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(),list);
    }
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        int count = brandMapper.insert(brand);
        if(count!=1)
        {
            throw new SPException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        for (Long cid : cids) {
            int i = brandMapper.saveBrandCategory(cid, brand.getId());
            if(i!=1){
                throw new SPException(ExceptionEnum.CATEGORY_BRAND_SAVE_ERROR);
            }
        }
    }
}
