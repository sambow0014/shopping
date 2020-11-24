package com.xp.item.api;

import com.xp.item.entity.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("brand")
public interface BrandApi {
    /**
     * 根据ID查询品牌
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Brand findBrandById(@PathVariable("id")Long id);
}
