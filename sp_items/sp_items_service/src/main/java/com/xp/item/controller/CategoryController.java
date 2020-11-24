package com.xp.item.controller;

import com.xp.item.entity.Category;


import com.xp.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> findCategoryByPid(@RequestParam("pid") Long pid){
        return  ResponseEntity.ok(categoryService.findCategoryByPid(pid));
    }
    @RequestMapping("addCategory")
    public String addCategory(Category category){
        categoryService.addCategory(category);
        return "redirect:list";
    }
    @GetMapping("names")
    public ResponseEntity<List<String>>findNamesByIds(@RequestParam("ids")List<Long> ids){
        List<String> names=categoryService.findNamesByIds(ids);
        if (CollectionUtils.isEmpty(names)){

            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(names);
        }
    }
}
