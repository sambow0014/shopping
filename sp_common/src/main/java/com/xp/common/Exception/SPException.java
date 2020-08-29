package com.xp.common.Exception;

import com.xp.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SPException extends RuntimeException {

    private ExceptionEnum exceptionEnum;
}
