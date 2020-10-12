package com.xp.item.controller;

import com.xp.common.vo.PageResult;
import com.xp.item.entity.SKU;
import com.xp.item.entity.SPU;
import com.xp.item.entity.SPUDetail;
import com.xp.item.mapper.SPUDetailMapper;
import com.xp.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查询SPU
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SPU>> findSPUByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key

    ){
        return ResponseEntity.ok(goodsService.findSPUByPage(key,page,rows,saleable));
    }

    /**
     * 商品新增
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SPU spu){
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改商品
     * @param spu
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SPU spu){
        goodsService.updateGoods(spu);
        return ResponseEntity.noContent().build();
    }

    /**
     * 根据supId查询商品详情（detail）
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{id}")
    public ResponseEntity<SPUDetail> findDetailById(@PathVariable("id") Long spuId){
        return ResponseEntity.ok(goodsService.findDetailById(spuId));
    }

    @GetMapping("sku/list")
    public ResponseEntity<List<SKU>> findSkuBySpuId(@RequestParam("id") Long id) {
        List<SKU> skus = goodsService.findSkuBySpuId(id);
        if (skus == null || skus.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skus);
    }
}
