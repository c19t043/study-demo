package cn.cjf.model;

import lombok.Data;

import java.util.Date;

/**
 * Item SKU 规格属性。
 * @author chenjunfan
 * @date 2019/2/20
 */
@Data
public class ItemSkuProperty {
    /*
    id ，属性编号。
    name ，属性文本。注意，当规格名( PropertyKey )和规格值( PropertyValue )使用相同文本，对应的编号相同。例如，
    PropertyKey 与 PropertyValue 通过 「3.3.2 ItemSkuPropertyValueReference」 关联。
    有赞的规格属性，这样的设定感觉有丢丢怪怪的，笔者后面调研了淘宝和微店的 Item SKU 规格属性的设计。
     */
    /*
    微店:分成 ItemSkuPropertyKey 和 ItemSkuPropertyValue 两个表，并且 ItemSkuPropertyValue 相同字符串时，不同编号。
    淘宝:不同类目固定可选的商品规格名，并且部分商品规格不能自定义规格值。
     */
    /**
     * 属性编号
     */
    private Integer id;
    /**
     * 属性文本
     */
    private String name;
    /**
     * 添加时间
     */
    private Date addTime;
}
