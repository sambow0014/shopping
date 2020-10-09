package com.xp.item.mapper;

import com.xp.item.entity.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category,Long> {
//    @Select("select * from tb_category where parent_id")
    public Category findCategoryByPid();

    public Category addCategory();
}
