package cn.cjf.pay.service.impl;

import cn.cjf.pay.service.WeixinService;
import cn.cjf.pay.util.HttpClientUtil;
import cn.cjf.pay.util.WeixinUtils;
import cn.cjf.pay.weixin.domain.UnifiedOrderRequestDto;
import cn.cjf.pay.weixin.domain.UnifiedOrderResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjunfan
 * @date 2019/5/16
 */
@Service
public class WeixinServiceImpl implements WeixinService {

    private final String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";


    @Override
    public UnifiedOrderResponseDto unifiedOrder(UnifiedOrderRequestDto requestDto) throws Exception {
        Map<String, String> param = new HashMap<>();

        unifiedOrderNecessaryParam(param, requestDto);

        String strXML = HttpClientUtil.post(unifiedorder, WeixinUtils.mapToXml(param));

        Map<String, String> resMap = WeixinUtils.xmlToMap(strXML);

        return unifiedOrderResult2Dto(resMap);
    }

    private UnifiedOrderResponseDto unifiedOrderResult2Dto(Map<String, String> resMap) {
        UnifiedOrderResponseDto dto = new UnifiedOrderResponseDto();

        dto.setReturnCode(resMap.get("return_code"));
        dto.setReturnMsg(resMap.get("return_msg"));

        dto.setAppid(resMap.get("appid"));
        dto.setMchId(resMap.get("mch_id"));
        dto.setDeviceInfo(resMap.get("device_info"));
        dto.setNonceStr(resMap.get("nonce_str"));
        dto.setSign(resMap.get("sign"));
        dto.setResultCode(resMap.get("result_code"));

        dto.setErrCode(resMap.get("err_code"));
        dto.setErrCodeDes(resMap.get("err_code_des"));

        dto.setTradeType(resMap.get("trade_type"));
        dto.setPrepayId(resMap.get("prepay_id"));
        dto.setCodeUrl(resMap.get("code_url"));
        return dto;
    }

    /**
     * 统一下单必传参数
     */
    private void unifiedOrderNecessaryParam(Map<String, String> param, UnifiedOrderRequestDto requestDto) {
        param.put("appid", requestDto.getAppid());
        param.put("mch_id", requestDto.getMchId());
        param.put("nonce_str", requestDto.getNonceStr());
        param.put("sign", requestDto.getSign());
        param.put("body", requestDto.getBody());
        param.put("out_trade_no", requestDto.getOutTradeNo());
        param.put("total_fee", requestDto.getTotalFee() + "");
        param.put("spbill_create_ip", requestDto.getSpbillCreateIp());
        param.put("notify_url", requestDto.getNotifyUrl());
        param.put("trade_type", requestDto.getTradeType());
    }

    @Override
    public Map<String, String> orderQuery(Map<String, String> reqData) {
        return null;
    }

    @Override
    public Map<String, String> refund(Map<String, String> reqData) {
        return null;
    }
}
