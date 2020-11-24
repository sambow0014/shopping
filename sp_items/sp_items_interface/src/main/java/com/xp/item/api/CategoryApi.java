package com.xp.item.api;

import com.xp.item.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("category")
public interface CategoryApi {
    /**
     * 通过id查询分类
     * @param ids
     * @return
     */
    @GetMapping("names")
    public List<String>findNamesByIds(@RequestParam("ids")List<Long> ids);
}
