package cn.cjf.model;

import lombok.Data;

import java.util.Date;

/**
 * 商品
 * @author chenjunfan
 * @date 2019/2/20
 */
@Data
public class Item {
    //===============================基础字段
    /*
    id ，Item 编号，自增。数据类型是使用 Integer 还是 Long ，胖友可以根据自己的系统需要做调整。
    alias ，别名，系统自动生成，作为唯一标识。例如，"2fpa62tbmsl9h" 。目前有赞商城商品详情地址使用别名而不使用编号，防止无脑抓取。例如，https://h5.youzan.com/v2/goods/3f1o7jxm0gshh 。
    shopId ，店铺编号。有赞是基于 Sass 模式，支持多商户( 店铺 )。
     */
    /**
     * 编号
     */
    private Integer id;
    /**
     * 别名
     * <p>
     * 系统生成，作为唯一标识。例如，2fpa62tbmsl9h
     */
    private String alias;
    /**
     * 店铺编号
     */
    private Integer shopId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 状态
     * <p>
     * 1-正常
     * 2-删除
     */
    private Integer status;
    //======================================基本信息
    /*
    desc ，商品描述，HTML 富文本。考虑到数据库性能，单独成 ItemContent 表，在 《数据库实体设计 —— 商品（1.2）之商品详情》 有详细分享。
    cid ，商品分类的叶子类目编号。有赞——店铺主营类目和商品类目对应表：https://bbs.youzan.com/forum.php?mod=viewthread&tid=25252 。
    itemType ，商品类型。不同的商品类型，会有相关的自定义逻辑。
    goodsType ，商品类型（已经不知道咋翻译好，和 itemType 无关系）。店铺可以从分销市场引入其他店铺的商品进行分销。TODO 6002：商品分销文章解析。
     */
    /**
     * 商品标题
     *
     * 不能超过100字，受违禁词控制
     */
    private String title;
    /**
     * 副标题，分享链接时显示
     */
    private String summary;
    /**
     * 商品描述。
     *
     * 字数要大于5个字符，小于25000个字符 ，受违禁词控制
     */
    @Deprecated
    private String desc;
    /**
     * 商品分类的叶子类目编号
     *
     * 有赞——店铺主营类目和商品类目对应表：https://bbs.youzan.com/forum.php?mod=viewthread&tid=25252
     */
    private Integer cid;
    /**
     * 商品主图地址
     *
     * 数组，以逗号分隔
     *
     * 建议尺寸：800*800像素，你可以拖拽图片调整顺序，最多上传15张
     */
    private String picURLs;
    /**
     * 商品类型
     *
     * 0：普通商品（物流发货）
     * 3：UMP降价拍
     * 5：外卖商品
     * 10：分销商品
     * 20：会员卡商品
     * 21：礼品卡商品
     * 22：团购券
     * 25：批发商品
     * 30：收银台商品
     * 31：知识付费商品
     * 35：酒店商品（无需物流）
     * 40：美业商品
     * 60：虚拟商品（无需物流）
     * 61：电子卡券（无需物流）
     */
    private Integer itemType;
    /**
     * 商品类型(来源)
     *
     * 0：自营商品
     * 10：分销商品
     */
    private Integer goodsType;
    //=================================价格库存
    /*
    Item SKU 相关，有赞分成两种情况：
        第一种，无 SKU 的情况，price、itemWeight、quantity、soldNum、itemNo 对应这个商品的价格库存等信息。
        第二种，有 SKU 的情况，price、itemWeight、quantity、soldNum、itemNo 对应这个商品 SKU 的整体情况。其中，price 对应 SKU 最低价格。
        ps：关于第一种的情况，也可以通过虚拟没有规格的 SKU，这样和第二种的情况就可以等价了。
    price ，价格，单位为分，避免 Double 在根据营销优惠信息计算价格时，精度丢失。注意，如果胖友一定要使用单位为元，在 Java 里请使用 BigDecimal 。
    joinLevelDiscount ，是否参加会员折扣。有赞支持会员卡功能，可以对商品进行优惠打折。
    itemNo ，商品货号。商家为商品设置的外部编号，例如，ERP 系统。通过该字段，打通不同系统的信息。
     */
    /**
     * 价格，单位分
     */
    private Integer price;
    /**
     * 商品重量，没有SKU时用
     */
    private Double itemWeight;
    /**
     * 商品货号（商家为商品设置的外部编号）
     */
    private String itemNo;
    /**
     * 总库存
     *
     * 基于 sku 的库存数量累加
     */
    private Integer quantity;
    /**
     * 总销量
     */
    private Integer soldNum;
    /**
     * 是否隐藏商品库存。在商品展示时不显示商品的库存。
     *
     * 0 - 显示库存（默认）
     * 1 - 不显示库存
     */
    private Integer hideStock;
    /**
     * 商品划线价格，可以自定义。例如 促销价：888
     *
     * 商品没有优惠的情况下，划线价在商品详情会以划线形式显示。
     */
    private Double originPrice;
    /**
     * 是否参加会员折扣。
     *
     * 1 - 参加会员折扣（默认）
     * 0 - 不参加会员折扣
     */
    private Integer joinLevelDiscount;
    //===========================================运费信息
    /*
    根据不同的 postType ，使用 postFee 或 deliveryTemplateId 。
     */
    /**
     * 运费类型
     *
     * 1-统一运费
     * 2-运费模板
     */
    private Integer postType;
    /**
     * 运费，单位分
     */
    private Integer postFee;
    /**
     * 运费模版id
     */
    private Integer deliveryTemplateId;
    //=============================================其他信息
    /*
    isListing ，是否上架商品。
    true ，已上架，用户可见，直到售罄( quantity = 0 )。售罄时，该状态不变，通过库存判断。
    false ，已下架，在仓库，用户不可见
    order ，排序字段。手动填写数字设置，序号越大越靠前。
     */
    /**
     * 是否上架商品。
     *
     * true 为已上架
     * false 为已下架
     */
    private Boolean isListing;
    /**
     * 排序字段
     */
    private Integer order;
    /**
     * 开始出售时间。
     *
     * 没设置则为空
     */
    private Date autoListingTime;
    /**
     * 商品是否锁定。
     *
     * true 为已锁定
     * false 为未锁定
     */
    private Boolean isLock;
    /**
     * 留言表单数组配置
     *
     * JSON 字符串 [{
     *     name: // 表单名，String
     *     required: // 是否必填，Integer，1-必填；0-选填
     *     type: // 表单类型，String，枚举：文本格式/数字格式/邮件/日期/时间/身份证号/图片
     *     multiple: // 是否多行，Integer，1-多行，0-单行
     *     datetime：// 是否包含日期，用于 `type=时间`
     * }]
     */
    private String messages;
}
