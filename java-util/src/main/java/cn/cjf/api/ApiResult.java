package cn.cjf.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenjunfan
 * @date 2019/4/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult {
    private Object data;
    private Integer code;
    private String msg;
}
