package cn.cjf.gateway.service.wxpay.impl;

import cn.cjf.gateway.config.weixin.WeiXinConfig;
import cn.cjf.gateway.domain.param.weixin.*;
import cn.cjf.gateway.service.wxpay.WeiXinPayHttpService;
import cn.cjf.gateway.utils.ConverterUtil;
import cn.cjf.gateway.utils.HttpUtil;

public class WeiXinPayHttpServiceImpl implements WeiXinPayHttpService {

    @Override
    public WeiXinUnifiedOrderResponse unifiedOrder(WeiXinUnifiedOrderRequest request) {
        String requestXml = ConverterUtil.convertObject2Xml(request);
        String responseStr = HttpUtil.doPost(WeiXinConfig.UNIFIED_ORDER_URL, requestXml);
        return ConverterUtil.convertXml2Object(responseStr, WeiXinUnifiedOrderResponse.class);
    }

    @Override
    public WeiXinQueryOrderResponse queryOrder(WeiXinQueryOrderRequest request) {
        String requestXml = ConverterUtil.convertObject2Xml(request);
        String responseStr = HttpUtil.doPost(WeiXinConfig.QUERY_ORDER_URL, requestXml);
        return ConverterUtil.convertXml2Object(responseStr, WeiXinQueryOrderResponse.class);
    }

    @Override
    public WeiXinCloseOrderResponse closeOrder(WeiXinCloseOrderRequest request) {
        String requestXml = ConverterUtil.convertObject2Xml(request);
        String responseStr = HttpUtil.doPost(WeiXinConfig.CLOSE_ORDER_URL, requestXml);
        return ConverterUtil.convertXml2Object(responseStr, WeiXinCloseOrderResponse.class);
    }

    @Override
    public WeiXinRefundResponse refundOrder(WeiXinRefundRequest request) {
        String requestXml = ConverterUtil.convertObject2Xml(request);
        String responseStr = HttpUtil.doPost(WeiXinConfig.REFUND_URL, requestXml);
        return ConverterUtil.convertXml2Object(responseStr, WeiXinRefundResponse.class);
    }

    @Override
    public WeiXinQueryRefundResponse queryRefundOrder(WeiXinQueryRefundRequest request) {
        String requestXml = ConverterUtil.convertObject2Xml(request);
        String responseStr = HttpUtil.doPost(WeiXinConfig.QUERY_REFUND_URL, requestXml);
        return ConverterUtil.convertXml2Object(responseStr, WeiXinQueryRefundResponse.class);
    }

    @Override
    public WeiXinMicroPayResponse microPay(WeiXinMicroPayRequest request) {
        String requestXml = ConverterUtil.convertObject2Xml(request);
        String responseStr = HttpUtil.doPost(WeiXinConfig.MICRO_PAY_URL, requestXml);
        return ConverterUtil.convertXml2Object(responseStr, WeiXinMicroPayResponse.class);
    }

    @Override
    public WeiXinReverseResponse reverse(WeiXinReverseRequest request) {
        String requestXml = ConverterUtil.convertObject2Xml(request);
        String responseStr = HttpUtil.doPost(WeiXinConfig.REVERSE_URL, requestXml);
        return ConverterUtil.convertXml2Object(responseStr, WeiXinReverseResponse.class);
    }
}
