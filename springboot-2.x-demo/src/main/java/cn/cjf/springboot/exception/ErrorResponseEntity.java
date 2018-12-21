package cn.cjf.springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Levin
 * @since 2018/6/1 0001
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseEntity {

    private int code;
    private String message;
    // 省略 get set 
}