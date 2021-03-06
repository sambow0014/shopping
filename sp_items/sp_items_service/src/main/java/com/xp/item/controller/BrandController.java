package com.xp.item.controller;

import com.xp.common.vo.PageResult;
import com.xp.item.entity.Brand;
import com.xp.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> findBrandByPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",defaultValue = "false") Boolean desc
    ){
        PageResult<Brand> result=brandService.findBrandByPage(key,page,rows,sortBy,desc);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>>findBrandByCategory(@PathVariable("cid")Long cid){
        return ResponseEntity.ok(brandService.findBrandByCategory(cid));
    }

    /**
     * 根据ID查询品牌
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand>findBrandById(@PathVariable("id")Long id){
        Brand brand= brandService.findBrandById(id);
        if (brand==null){
            return  ResponseEntity.notFound().build();
        }
        else {
            return  ResponseEntity.ok(brand);
        }
    }
}
