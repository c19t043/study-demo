package cn.cjf.gateway.service.wxpay.impl;

import cn.cjf.gateway.domain.param.weixin.WeiXinPayCallbackRequest;
import cn.cjf.gateway.service.wxpay.WxPayService;
import cn.cjf.gateway.utils.ConverterUtil;

public class WxPayServiceImpl implements WxPayService {
    @Override
    public WeiXinPayCallbackRequest wxCallback(String requestXml) {
        return ConverterUtil.convertXml2Object(requestXml, WeiXinPayCallbackRequest.class);
    }
}
