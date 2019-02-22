package cn.cjf.model;

import lombok.Data;

import java.util.Date;

/**
 * Item SKU 规格名引用。用于和店铺关联，不同店铺有不同的规格名引用数据。
 * @author chenjunfan
 * @date 2019/2/20
 */
@Data
public class ItemSkuPropertyKeyReference {
    /**
     * 编号
     */
    private Integer id;
    /**
     * 店铺编号
     */
    private Integer shopId;
    /**
     * 属性键编号
     * <p>
     * {@link ItemSkuProperty#id}
     */
    private Integer keyId;
    /**
     * 添加时间
     */
    private Date addTime;
}
