package cn.cjf.pay.service;

import cn.cjf.pay.weixin.domain.UnifiedOrderRequestDto;
import cn.cjf.pay.weixin.domain.UnifiedOrderResponseDto;

import java.util.Map;

/**
 * @author chenjunfan
 * @date 2019/5/16
 */
public interface WeixinService {
    /**
     * 作用：统一下单<br>
     * 场景：公共号支付、扫码支付、APP支付
     */
    UnifiedOrderResponseDto unifiedOrder(UnifiedOrderRequestDto requestDto) throws Exception;

    /**
     * 作用：查询订单<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     */
    Map<String, String> orderQuery(Map<String, String> reqData);

    /**
     * 作用：申请退款<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     */
    Map<String, String> refund(Map<String, String> reqData);
}
