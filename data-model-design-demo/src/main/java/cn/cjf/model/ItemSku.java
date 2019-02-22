package cn.cjf.model;

import lombok.Data;

import java.util.Date;

/**
 * 商品 SKU
 * @author chenjunfan
 * @date 2019/2/20
 */
@Data
public class ItemSku {
    /*
    uniqueCode ，SKU 唯一编码，格式为 ${shopId}${skuId}。注意，在分销场景下，引入商品的 skuId 相同，而 shopId 不同。通过该字段，保证唯一引入。
    skuId ，SKU 编号，自增，非唯一，参见分销场景。
    itemId ，Item 编号，N：1 指向对应的 Item 。
    status ，SKU 状态。编辑商品时，当规格属性发生变化时，优先重用( 保留 )正常状态的 SKU ，标记删除移除的 SKU ，例如：

    properties ，商品规格，字符串拼接格式。
        绝大多数情况下，数据库里的该字段，不存在检索的需求，更多的时候，是查询整体记录，在内存中解析使用。
        少部分情况，灵活的检索，使用 Elasticsearch 进行解决。
    quantity ，库存数量。在分销场景下，因为 skuId 相同，所以根据 skuId 可以批量修改，解决分销库存的同步问题。
    withHoldQuantity ，商品在付款减库存的状态下( 例如秒杀场景 )，该 SKU 上未付款的订单数量。
    itemNo ，商品货号。商家为商品设置的外部编号，例如，ERP 系统。通过该字段，打通不同系统的信息。
     */
    /**
     * 唯一编码，店铺Id 和 商品skuId 组合
     *
     * 分销场景下，skuId 多个店铺相同，uniqueCode 不同
     */
    private String uniqueCode;
    /**
     * sku 编号
     *
     * 非唯一
     */
    private Integer skuId;
    /**
     * 商品编号
     */
    private Integer itemId;
    /**
     * 店铺编号
     */
    private Integer shopId;
    /**
     * 状态
     *
     * 1-正常
     * 2-删除
     */
    private Integer status;
    /**
     * 图片地址
     */
    private String imageURL;
    /**
     * 商品规格
     *
     * 格式：kid[0]-vid[0],kid[1]-vid[1]...kid[n]-vid[n]
     * 例如：20000-3275069,1753146-3485013
     */
    private String properties;
    /**
     * 商品货号（商家为商品设置的外部编号）
     */
    private String itemNo;
    /**
     * 价格，单位分
     */
    private Integer price;
    /**
     * 库存数量
     */
    private Integer quantity;
    /**
     * 商品在付款减库存的状态下，该Sku上未付款的订单数量
     */
    private Integer withHoldQuantity;
    /**
     * 销量
     */
    private Integer soldNum;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
