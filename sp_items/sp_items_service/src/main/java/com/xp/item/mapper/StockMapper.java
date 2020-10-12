package com.xp.item.mapper;

import com.xp.common.mapper.XPMapper;
import com.xp.item.entity.Stock;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface StockMapper extends Mapper<Stock>,InsertListMapper<Stock>, IdListMapper<Stock,Long> {
}
