package cn.cjf.netty.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author CJF
 */
@Data
public abstract class Packet implements Serializable {
    /**
     * 数据包类型
     *
     * @return Byte
     */
    public abstract Byte getType();

    public abstract String getMessageId();

    public abstract void setMessageId(String messageId);
}
