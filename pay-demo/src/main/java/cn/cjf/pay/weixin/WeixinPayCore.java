package cn.cjf.pay.weixin;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjunfan
 * @date 2019/5/9
 */
public class WeixinPayCore {

    /**
     * 创建微信支付预订单并构造返回APP端参数
     *
     * @param orderSubject
     * @param orderVo
     * @param clientIP
     * @return
     * @throws Exception Map<String,Object>
     * @author: Bobbie.Qi
     * @time:2017年2月15日
     */
    @Deprecated
    public static Map<String, Object> createWechatPayOrder(Map<String, String> orderSubject, OrderVo orderVo, String clientIP) {
        try {
            Map<String, Object> restmap = null;
            Map<String, Object> parm = new HashMap<String, Object>();
            parm.put("appid", WechatPayConfig.APP_ID);
            parm.put("mch_id", WechatPayConfig.MCH_ID);
            parm.put("device_info", WechatPayConfig.DEVICE_INFO);
            parm.put("nonce_str", WechatPayUtil.getNonceStr());
            parm.put("body", getBody(orderSubject.get("subject")));
            parm.put("detail", orderSubject.get("orderInfo"));
            parm.put("out_trade_no", orderVo.getOrderId());
            parm.put("total_fee", orderVo.getReceiptsMoney());
//	        parm.put("total_fee", 1);
            parm.put("spbill_create_ip", clientIP);
            // 2017-05-02 add begin
            parm.put("time_start", DateUtil.dateToString(orderVo.getCreateTime(), DateUtil.DATE_FORMAT_D));
            parm.put("time_expire", DateUtil.dateToString(DateUtil.stringToDate(orderVo.getExpireTime()), DateUtil.DATE_FORMAT_D));
            // 2017-05-02 add end
            parm.put("notify_url", Property.BASE + WechatPayConfig.NOTIFY_URL);
            parm.put("trade_type", WechatPayConfig.TRADE_TYPE);
            String sign = WechatPayUtil.getSign(parm, WechatPayConfig.API_SECRET);
            parm.put("sign", sign);

            log.info("### WECHATPAY: post param ### " + JSONObject.toJSONString(parm));

            String restxml = HttpUtils.post(WechatPayConfig.ORDER_PAY, XmlUtil.xmlFormat(parm, false));
            log.info("### WECHATPAY: return xml body ### " + restxml);

            restmap = XmlUtil.xmlParse(restxml);
            log.info("### WECHATPAY: parse xml to map ### " + JSONObject.toJSONString(restmap));

            Map<String, Object> payMap = null;
            if (CollectionUtil.isNotEmpty(restmap) && WechatPayConfig.RESULT_SUCCESS.equals(restmap.get("return_code"))) {
                // 检查APP_ID
                if (WechatPayConfig.APP_ID.equals(restmap.get("appid")) && WechatPayConfig.MCH_ID.equals(restmap.get("mch_id"))) {
                    if (WechatPayConfig.RESULT_SUCCESS.equals(restmap.get("result_code"))) {
                        payMap = new HashMap<String, Object>();
                        payMap.put("appid", WechatPayConfig.APP_ID);
                        payMap.put("partnerid", WechatPayConfig.MCH_ID);
                        payMap.put("prepayid", restmap.get("prepay_id"));
                        payMap.put("package", "Sign=WXPay");// 原名称：package
                        payMap.put("noncestr", WeixinPayUtils.getNonceStr());
                        payMap.put("timestamp", WeixinPayUtils.payTimestamp());
                        payMap.put("sign", WechatPayUtil.getSign(payMap, WechatPayConfig.API_SECRET));
                        log.info("### WECHATPAY: check return xml success. return app map data ### " + JSONObject.toJSONString(payMap));
                    } else {
                        log.info("### WECHATPAY: check return xml failure. return result_code ### " + restmap.get("result_code"));
                    }
                } else {
                    log.info("### WECHATPAY: check return xml failure. return appid or mch_id error ### ");
                }
            }

            return payMap;
        } catch (Exception e) {
            log.error("### WECHATPAY: get sign error ### ");
            e.printStackTrace();
            return null;
        }
    }
}
