package cn.cjf.model;

import lombok.Data;

import java.util.Date;

/**
 * 预售扩展信息
 * @author chenjunfan
 * @date 2019/2/20
 */
@Data
public class ItemEtd {
    /*
    《预售界面优化及新增N天后发货，适合更多预售场景》
    通过 Item 的 etdStatus 字段，开启和关闭预售功能。
    id ，Item 编号，1：1 指向对应的 Item 。
    根据不同的 etdType ，使用 etdStartDate 或 etdDays 。

    字段以 Date 结尾，用于仅有日期格式的时间。
    字段以 Time 结尾，用于带有时间格式的时间。
     */
    /**
     * Item 编号
     *
     * {@link Item#id}
     */
    private Integer id;
    /**
     * 发货类型
     *
     * 0 - xxx 时间开始发货
     * 1 - 付款 n 天后发货。
     */
    private Integer etdType;
    /**
     * 预计发货开始时间, 字符串格式的时间，格式如：2018-01-01
     */
    private Date etdStartDate;
    /**
     * 付款成功 后发货天数, 默认0
     */
    private Integer etdDays;
}
