package com.xp.item.service;

import com.xp.common.Exception.SPException;
import com.xp.common.enums.ExceptionEnum;
import com.xp.item.entity.Category;
import com.xp.item.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> findCategoryByPid(Long pid) {
        Category t=new Category();
        t.setParentId(pid);
        List<Category> list = categoryMapper.select(t);
        if (list==null||list.isEmpty())
        {
            throw new SPException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        return list;
    }
    public void addCategory(Category category){
        categoryMapper.insert(category);
    }

    public List<Category> findByIds(List<Long>ids){
        List<Category> categories = categoryMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(categories))
        {
            throw new SPException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        return categories;
    }


    public List<String> findNamesByIds(List<Long> ids) {
        List<Category> categories = categoryMapper.selectByIdList(ids);
        return categories.stream().map(Category::getName).collect(Collectors.toList());
    }
}
