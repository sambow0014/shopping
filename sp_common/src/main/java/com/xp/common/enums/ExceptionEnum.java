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
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    CATEGORY_BRAND_SAVE_ERROR(500,"添加品牌分类中间表失败"),
    UPLOAD_FILE_ERROR(500,"上传文件失败"),
    UPLOAD_FILE_TYPE_ERROR(500,"上传文件类型错误"),
    UPLOAD_FILE_CONTENT_ERROR(500,"上传文件内容不合法")
    ;
    private int code;
    private String msg;

}
