package com.xp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  ExceptionEnum{
//    PRICE_CANNOT_BE_NULL(400,"价格不能为空"),
    CATEGORY_NOT_FOND(404,"未查询到产品分类"),
    BRAND_NOT_FOUND(400,"未查询到产品品牌"),
    SPEC_GROUP_NOT_FOND(404,"商品规格组未查询到"),
    SPEC_PARAM_NOT_FOND(404,"商品参数未查询到"),
    GOODS_NOT_FOND(404,"商品未查询到"),
    GOODS_DETAIL_NOT_FOUND(404,"商品详情没有查询到"),
    GOODS_SKU_NOT_FOUND(404,"商品SKU不存在"),
    GOODS_STOCK_NOT_FOUND(404,"商品库存不存在"),
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    CATEGORY_BRAND_SAVE_ERROR(500,"添加品牌分类中间表失败"),
    UPLOAD_FILE_ERROR(500,"上传文件失败"),
    UPLOAD_FILE_TYPE_ERROR(500,"上传文件类型错误"),
    UPLOAD_FILE_CONTENT_ERROR(500,"上传文件内容不合法"),
    GOODS_SAVE_ERROR(500,"新增商品失败"),
    GOODS_UPDATE_ERROR(500,"更新商品信息失败")
    ;
    private int code;
    private String msg;

}
