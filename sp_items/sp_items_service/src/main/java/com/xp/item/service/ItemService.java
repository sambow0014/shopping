package com.xp.item.service;

import com.xp.item.entity.Items;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ItemService {
    public Items saveItem(Items items){
        //商品新增
        int id = new Random().nextInt(100);
        items.setId(id);
        return items;
    }
}
