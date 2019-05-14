package cn.cjf.validation.domain;

import cn.cjf.validation.annotation.CheckTimeInterval;
import cn.cjf.validation.group.GroupCouponAdd;
import cn.cjf.validation.group.GroupCouponEdit;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author chenjunfan
 * @date 2019/5/14
 */
@Data
@CheckTimeInterval(startTime = "releaseStartTime",
        endTime = "releaseEndTime",
        message = "发送开始时间不能大于发送结束时间")
public class CouponForm {
    // 优惠券id
    @NotNull(groups = {GroupCouponEdit.class},
            message = "优惠券ID不能为空")
    private Long couponId;

    // 优惠券名称
    private String couponName;

    // 优惠券类型
    @NotNull(groups = {GroupCouponAdd.class,
            GroupCouponEdit.class},
            message = "优惠券类型不能为空")
    private Integer couponType;

    // 优惠券面值
    @NotNull
    private Integer value;

    // 发送数量
    @NotNull
    private Integer quantity;

    // 商户id
    @NotNull(groups = {GroupCouponAdd.class, GroupCouponEdit.class},
            message = "商户ID不能为空")
    private Integer merchantId;

    // 发布开始时间
    private Date releaseStartTime;
    // 发布结束时间
    private Date releaseEndTime;
}
