package cn.cjf.model;

import lombok.Data;

/**
 * 分销扩展信息
 * @author chenjunfan
 * @date 2019/2/20
 */
@Data
public class ItemFenxiao {
    /*
    通过 Item 的 goodsType = 10 判断为分销商品。
    id ，Item 编号，1：1 指向对应的 Item 。
     */
    /**
     * Item 编号
     *
     * {@link Item#id}
     */
    private Integer id;
    /**
     * 供货店铺Id
     */
    private Integer supplierShopId;
    /**
     * 供货商品Id
     */
    private Integer supplierItemId;

}
