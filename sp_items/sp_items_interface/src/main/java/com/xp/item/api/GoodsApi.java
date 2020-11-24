package com.xp.item.api;

import com.xp.common.vo.PageResult;
import com.xp.item.entity.SKU;
import com.xp.item.entity.SPU;
import com.xp.item.entity.SPUDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodsApi {
    /**
     * 分页查询SPU
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/spu/page")
    public PageResult<SPU> findSPUByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key

    );

    /**
     * 根据supId查询商品详情（detail）
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{id}")
    public SPUDetail findDetailById(@PathVariable("id") Long spuId);


    /**
     * 根据spuId查询sku集合
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    public List<SKU> findSkuBySpuId(@RequestParam("id") Long id);
}
