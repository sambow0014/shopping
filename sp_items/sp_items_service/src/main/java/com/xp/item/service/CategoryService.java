package com.xp.item.service;

import com.xp.common.Exception.SPException;
import com.xp.common.enums.ExceptionEnum;
import com.xp.item.entity.Category;
import com.xp.item.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
