package cn.cjf.chat.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 协议配置文件
 *
 * @author CJF
 */
public interface Protocol {
    /**
     * 定义协议：魔法数字(int) + 协议版本(byte) + 数据包序列化算法type(byte)
     *  + 数据包type(byte) +数据包长度(int) + 数据包byte数组
     */
    /**
     * 魔法数字(int)
     */
    int MAGIC_NUMBER = 1;
    /**
     * 协议版本(byte)
     */
    Byte PROTOCAL_VERSION = 1;
}
