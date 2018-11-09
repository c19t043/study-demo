package cn.cjf.common.result;

import lombok.Data;
import lombok.ToString;

/**
 * Created on 2017/3/15.
 *
 * @author StormMa
 *
 * @Description: 对请求结果进行封装
 */
@Data
@ToString
public class Request<T> {

    /**
     * error code :错误是1、成功是0
     */
    private Integer code;

    /**
     * 要返回的数据
     */
    private T data;

    /**
     * 本次请求的说明信息
     */
    private String msg;
}
