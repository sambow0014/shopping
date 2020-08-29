package com.xp.common.vo;

import com.xp.common.enums.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResult {
    private Integer code;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em)
    {
        this.code=em.getCode();
        this.message=em.getMsg();
        this.timestamp=System.currentTimeMillis();
    }
}
