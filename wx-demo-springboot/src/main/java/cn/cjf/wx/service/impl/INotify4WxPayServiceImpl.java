package cn.cjf.wx.service.impl;

import cn.cjf.common.config.ConstantConfig;
import cn.cjf.common.log.MyLog;
import cn.cjf.wx.constant.PayConstant;
import cn.cjf.wx.enumm.RetEnum;
import cn.cjf.wx.model.PayChannel;
import cn.cjf.wx.model.PayOrder;
import cn.cjf.wx.service.INotify4WxPayService;
import cn.cjf.wx.service.Notify4BasePay;
import cn.cjf.wx.utils.ObjectValidUtil;
import cn.cjf.wx.utils.WxPayUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjunfan 176158750@qq.com
 * @date 2018-11-09
 */
@Service
public class INotify4WxPayServiceImpl extends Notify4BasePay implements INotify4WxPayService {
    private static final MyLog _log = MyLog.getLog(INotify4WxPayServiceImpl.class);
    private ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(() -> new HashMap<>(ConstantConfig.MAP_INITIAL_CAPACITY));

    @Override
    public String handleWxPayNotify(String xmlResult) {
        String logPrefix = "【处理微信支付回调】";
        _log.info("====== 开始处理微信支付回调通知 ======");
        try {
            //校验
            if (ObjectValidUtil.isInvalid(xmlResult)) {
                _log.warn("处理微信支付回调失败, {}", RetEnum.RET_PARAM_INVALID.getMessage());
                return WxPayNotifyResponse.fail(RetEnum.RET_PARAM_INVALID.getMessage());
            }
            //解析xml
            WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlResult);

            Map<String, Object> payContext = threadLocal.get();
            // 验证业务数据是否正确
            if (!verifyWxPayParams(result)) {
                return WxPayNotifyResponse.fail((String) payContext.get("retMsg"));
            }

            PayOrder payOrder = (PayOrder) payContext.get("payOrder");
            WxPayConfig wxPayConfig = (WxPayConfig) payContext.get("wxPayConfig");
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);

            // 处理订单
            byte payStatus = payOrder.getStatus();
            // 0：订单生成，1：支付中，-1：支付失败，2：支付成功，3：业务处理完成，-2：订单过期
            if (payStatus != PayConstant.PAY_STATUS_SUCCESS && payStatus != PayConstant.PAY_STATUS_COMPLETE) {
                int updatePayOrderRows = this.baseUpdateStatus4Success(payOrder.getPayOrderId(), result.getTransactionId());
                if (updatePayOrderRows != 1) {
                    _log.error("{}更新支付状态失败,将payOrderId={},更新payStatus={}失败",
                            logPrefix, payOrder.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
                    return WxPayNotifyResponse.fail("处理订单失败");
                }
                _log.error("{}更新支付状态成功,将payOrderId={},更新payStatus={}成功", logPrefix, payOrder.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
                payOrder.setStatus(PayConstant.PAY_STATUS_SUCCESS);
                payOrder.setChannelOrderNo(result.getTransactionId());
            }

            // 业务系统后端通知
            doNotify(payOrder);
            _log.info("====== 完成处理微信支付回调通知 ======");
            return WxPayNotifyResponse.success("OK");
        } catch (Exception e) {
            //出现业务错误
            _log.error(e, "微信回调结果异常,异常原因");
            _log.info("{}请求数据result_code=FAIL", logPrefix);
            return WxPayNotifyResponse.fail(e.getMessage());
        }
    }

    /**
     * 验证微信支付通知参数
     *
     * @return
     */
    public boolean verifyWxPayParams(WxPayOrderNotifyResult result) {
        Map<String, Object> payContext = threadLocal.get();

        //校验结果是否成功
        if (!PayConstant.RETURN_VALUE_SUCCESS.equalsIgnoreCase(result.getResultCode())
                || !PayConstant.RETURN_VALUE_SUCCESS.equalsIgnoreCase(result.getResultCode())) {
            _log.error("returnCode={},resultCode={},errCode={},errCodeDes={}",
                    result.getReturnCode(), result.getResultCode(),
                    result.getErrCode(), result.getErrCodeDes());
            payContext.put("retMsg", "notify data failed");
            return false;
        }
        // 总金额
        Integer total_fee = result.getTotalFee();
        // 商户系统订单号
        String out_trade_no = result.getOutTradeNo();

        // 查询payOrder记录
        String payOrderId = out_trade_no;
        PayOrder payOrder = this.baseSelectPayOrder(payOrderId);
        if (payOrder == null) {
            _log.error("Can't found payOrder form db. payOrderId={}, ", payOrderId);
            payContext.put("retMsg", "Can't found payOrder");
            return false;
        }

        // 查询payChannel记录
        String mchId = payOrder.getMchId();
        String channelId = payOrder.getChannelId();
        PayChannel payChannel = this.baseSelectPayChannel(mchId, channelId);
        if (payChannel == null) {
            _log.error("Can't found payChannel form db. mchId={} channelId={}, ", payOrderId, mchId, channelId);
            payContext.put("retMsg", "Can't found payChannel");
            return false;
        }

        payContext.put("wxPayConfig", WxPayUtil.getWxPayConfig(payChannel.getParam()));

        // 核对金额
        long wxPayAmt = new BigDecimal(total_fee).longValue();
        long dbPayAmt = payOrder.getAmount().longValue();
        if (dbPayAmt != wxPayAmt) {
            _log.error("db payOrder record payPrice not equals total_fee. total_fee={},payOrderId={}", total_fee, payOrderId);
            payContext.put("retMsg", "total_fee is not the same");
            return false;
        }

        payContext.put("payOrder", payOrder);
        return true;
    }
}
