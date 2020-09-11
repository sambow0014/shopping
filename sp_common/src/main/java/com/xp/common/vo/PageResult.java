package com.xp.common.vo;

import lombok.Data;

import java.util.List;

//通用分页工具
@Data
public class PageResult<T> {
    private Long total;//总条数
    private Integer totalPage;//总页数
    private List<T> items;//当前页页数

    public PageResult(){

    }

    public PageResult(Long total,List<T>items){
        this.total=total;
        this.items=items;
    }

    public PageResult(Long total,List<T>items,Integer totalPage ){
        this.total=total;
        this.items=items;
        this.totalPage=totalPage;
    }
}
