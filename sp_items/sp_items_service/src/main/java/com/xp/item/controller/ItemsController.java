package com.xp.item.controller;

import com.xp.common.Exception.SPException;
import com.xp.common.enums.ExceptionEnum;
import com.xp.item.entity.Items;
import com.xp.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("item")
public class ItemsController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("save")
    public ResponseEntity<Items> saveItems(Items items){
        //校验价格是否为空
        if (items.getPrice()==null)
        {
            throw new SPException(ExceptionEnum.PRICE_CANNOT_BE_NULL);
        }
        Items item = itemService.saveItem(items);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
}
