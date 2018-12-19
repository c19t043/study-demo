package cn.cjf.gateway.service.alipay.impl;

import cn.cjf.gateway.config.ali.AlipayConfig;
import cn.cjf.gateway.domain.param.ali.*;
import cn.cjf.gateway.service.alipay.AliPayService;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import org.springframework.stereotype.Service;

@Service
public class AliPayServiceImpl implements AliPayService {
    /**
     * 获得初始化的AlipayClient
     */
    private AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.MERCHANT_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);


    @Override
    public Object notifyUrl(Object obj) {
        return null;
    }

    /*
    {
    "alipay_trade_precreate_response": {
        "code": "10000",
        "msg": "Success",
        "out_trade_no": "6823789339978248",
        "qr_code": "https://qr.alipay.com/bavh4wjlxf12tper3a"
    },
    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
}

{
    "alipay_trade_precreate_response": {
        "code": "20000",
        "msg": "Service Currently Unavailable",
        "sub_code": "isp.unknow-error",
        "sub_msg": "系统繁忙"
    },
    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
}
     */
    @Override
    public AlipayTradePrecreateResponse preCreate(AlipayTradePrecreateRequestMy requestMy) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "app_id", "your private_key", "json", "GBK", "alipay_public_key", "RSA2");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"20150320010101001\"," +
                "\"seller_id\":\"2088102146225135\"," +
                "\"total_amount\":88.88," +
                "\"discountable_amount\":8.88," +
                "\"subject\":\"Iphone6 16G\"," +
                "      \"goods_detail\":[{" +
                "        \"goods_id\":\"apple-01\"," +
                "\"goods_name\":\"ipad\"," +
                "\"quantity\":1," +
                "\"price\":2000," +
                "\"goods_category\":\"34543238\"," +
                "\"categories_tree\":\"124868003|126232002|126252004\"," +
                "\"body\":\"特价手机\"," +
                "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
                "        }]," +
                "\"body\":\"Iphone6 16G\"," +
                "\"operator_id\":\"yx_001\"," +
                "\"store_id\":\"NJ_001\"," +
                "\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"terminal_id\":\"NJ_T_001\"," +
                "\"extend_params\":{" +
                "\"sys_service_provider_id\":\"2088511833207846\"," +
                "\"industry_reflux_info\":\"{\\\\\\\"scene_code\\\\\\\":\\\\\\\"metro_tradeorder\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"xxxx\\\\\\\",\\\\\\\"scene_data\\\\\\\":{\\\\\\\"asset_name\\\\\\\":\\\\\\\"ALIPAY\\\\\\\"}}\"," +
                "\"card_type\":\"S0JP0000\"" +
                "    }," +
                "\"timeout_express\":\"90m\"," +
                "\"settle_info\":{" +
                "        \"settle_detail_infos\":[{" +
                "          \"trans_in_type\":\"cardSerialNo\"," +
                "\"trans_in\":\"A0001\"," +
                "\"summary_dimension\":\"A0001\"," +
                "\"settle_entity_id\":\"2088xxxxx;ST_0001\"," +
                "\"settle_entity_type\":\"SecondMerchant、Store\"," +
                "\"amount\":0.1" +
                "          }]" +
                "    }," +
                "\"merchant_order_no\":\"20161008001\"," +
                "\"business_params\":\"{\\\"data\\\":\\\"123\\\"}\"," +
                "\"qr_code_timeout_express\":\"90m\"" +
                "  }");
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return null;
    }

    /*
     {
    "alipay_trade_fastpay_refund_query_response": {
        "code": "10000",
        "msg": "Success",
        "trade_no": "2014112611001004680073956707",
        "out_trade_no": "20150320010101001",
        "out_request_no": "20150320010101001",
        "refund_reason": "用户退款请求",
        "total_amount": 100.2,
        "refund_amount": 12.33,
        "refund_royaltys": [
            {
                "refund_amount": 10,
                "royalty_type": "transfer",
                "result_code": "SUCCESS",
                "trans_out": "2088102210397302",
                "trans_out_email": "alipay-test03@alipay.com",
                "trans_in": "2088102210397302",
                "trans_in_email": "zen_gwen@hotmail.com"
            }
        ],
        "gmt_refund_pay": "2014-11-27 15:45:57",
        "refund_detail_item_list": [
            {
                "fund_channel": "ALIPAYACCOUNT",
                "amount": 10,
                "real_amount": 11.21,
                "fund_type": "DEBIT_CARD"
            }
        ],
        "send_back_fee": "88",
        "refund_charge_amount": "8.88",
        "refund_settlement_id": "2018101610032004620239146945",
        "present_refund_buyer_amount": "88.88",
        "present_refund_discount_amount": "88.88",
        "present_refund_mdiscount_amount": "88.88"
    },
    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
}

 {
    "alipay_trade_fastpay_refund_query_response": {
        "code": "20000",
        "msg": "Service Currently Unavailable",
        "sub_code": "isp.unknow-error",
        "sub_msg": "系统繁忙"
    },
    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
}
     */

    /**
     * @return
     */
    @Override
    public AlipayTradeFastpayRefundQueryResponse refundQuery(AlipayTradeFastpayRefundQueryRequestMy requestMy) {
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
        alipayRequest.setBizContent("{" +
                "\"trade_no\":\"20150320010101001\"," +
                "\"out_trade_no\":\"2014112611001004680073956707\"," +
                "\"out_request_no\":\"2014112611001004680073956707\"," +
                "\"org_pid\":\"2088101117952222\"" +
                "  }");
        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            response = alipayClient.execute(alipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
//        if (response.isSuccess()) {
//            System.out.println("调用成功");
//        } else {
//            System.out.println("调用失败");
//        }
        return response;
    }

    /*
 {
 "alipay_trade_close_response": {
     "code": "10000",
     "msg": "Success",
     "trade_no": "2013112111001004500000675971",
     "out_trade_no": "YX_001"
 },
 "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
 }
{
    "alipay_trade_close_response": {
        "code": "20000",
        "msg": "Service Currently Unavailable",
        "sub_code": "isp.unknow-error",
        "sub_msg": "系统繁忙"
    },
    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
}
  */

    /**
     */
    @Override
    public AlipayTradeCloseResponse close(AlipayTradeCloseRequestMy requestMy) {
        //设置请求参数
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = requestMy.getOutTradeNo();
        //支付宝交易号
        String trade_no = requestMy.getTradeNo();
        //请二选一设置

        alipayRequest.setBizContent(JSON.toJSONString(requestMy));
        AlipayTradeCloseResponse response = null;
        try {
            response = alipayClient.execute(alipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }
/*
{
    "alipay_trade_page_pay_response": {
        "code": "10000",
        "msg": "Success",
        "trade_no": "2013112011001004330000121536",
        "out_trade_no": "6823789339978248",
        "seller_id": "2088111111116894",
        "total_amount": 128
    },
    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
}

{
    "alipay_trade_page_pay_response": {
        "code": "20000",
        "msg": "Service Currently Unavailable",
        "sub_code": "isp.unknow-error",
        "sub_msg": "系统繁忙"
    },
    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
}
 */

    /**
     * @param requestMy
     * @return
     */
    @Override
    public AlipayTradePagePayResponse pagePay(AlipayTradePagePayRequestMy requestMy) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"20150320010101001\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "\"total_amount\":88.88," +
                "\"subject\":\"Iphone6 16G\"," +
                "\"body\":\"Iphone6 16G\"," +
                "\"time_expire\":\"2016-12-31 10:05\"," +
                "      \"goods_detail\":[{" +
                "        \"goods_id\":\"apple-01\"," +
                "\"alipay_goods_id\":\"20010001\"," +
                "\"goods_name\":\"ipad\"," +
                "\"quantity\":1," +
                "\"price\":2000," +
                "\"goods_category\":\"34543238\"," +
                "\"body\":\"特价手机\"," +
                "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
                "        }]," +
                "\"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "\"extend_params\":{" +
                "\"sys_service_provider_id\":\"2088511833207846\"," +
                "\"hb_fq_num\":\"3\"," +
                "\"hb_fq_seller_percent\":\"100\"," +
                "\"industry_reflux_info\":\"{\\\\\\\"scene_code\\\\\\\":\\\\\\\"metro_tradeorder\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"xxxx\\\\\\\",\\\\\\\"scene_data\\\\\\\":{\\\\\\\"asset_name\\\\\\\":\\\\\\\"ALIPAY\\\\\\\"}}\"," +
                "\"card_type\":\"S0JP0000\"" +
                "    }," +
                "\"goods_type\":\"0\"," +
                "\"timeout_express\":\"90m\"," +
                "\"promo_params\":\"{\\\"storeIdType\\\":\\\"1\\\"}\"," +
                "\"royalty_info\":{" +
                "\"royalty_type\":\"ROYALTY\"," +
                "        \"royalty_detail_infos\":[{" +
                "          \"serial_no\":1," +
                "\"trans_in_type\":\"userId\"," +
                "\"batch_no\":\"123\"," +
                "\"out_relation_id\":\"20131124001\"," +
                "\"trans_out_type\":\"userId\"," +
                "\"trans_out\":\"2088101126765726\"," +
                "\"trans_in\":\"2088101126708402\"," +
                "\"amount\":0.1," +
                "\"desc\":\"分账测试1\"," +
                "\"amount_percentage\":\"100\"" +
                "          }]" +
                "    }," +
                "\"sub_merchant\":{" +
                "\"merchant_id\":\"19023454\"," +
                "\"merchant_type\":\"alipay: 支付宝分配的间连商户编号, merchant: 商户端的间连商户编号\"" +
                "    }," +
                "\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"store_id\":\"NJ_001\"," +
                "\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"qr_pay_mode\":\"1\"," +
                "\"qrcode_width\":100," +
                "\"settle_info\":{" +
                "        \"settle_detail_infos\":[{" +
                "          \"trans_in_type\":\"cardSerialNo\"," +
                "\"trans_in\":\"A0001\"," +
                "\"summary_dimension\":\"A0001\"," +
                "\"settle_entity_id\":\"2088xxxxx;ST_0001\"," +
                "\"settle_entity_type\":\"SecondMerchant、Store\"," +
                "\"amount\":0.1" +
                "          }]" +
                "    }," +
                "\"invoice_info\":{" +
                "\"key_info\":{" +
                "\"is_support_invoice\":true," +
                "\"invoice_merchant_name\":\"ABC|003\"," +
                "\"tax_num\":\"1464888883494\"" +
                "      }," +
                "\"details\":\"[{\\\"code\\\":\\\"100294400\\\",\\\"name\\\":\\\"服饰\\\",\\\"num\\\":\\\"2\\\",\\\"sumPrice\\\":\\\"200.00\\\",\\\"taxRate\\\":\\\"6%\\\"}]\"" +
                "    }," +
                "\"agreement_sign_params\":{" +
                "\"personal_product_code\":\"GENERAL_WITHHOLDING_P\"," +
                "\"sign_scene\":\"INDUSTRY|CARRENTAL\"," +
                "\"external_agreement_no\":\"test\"," +
                "\"external_logon_id\":\"13852852877\"," +
                "\"sign_validity_period\":\"2m\"," +
                "\"third_party_type\":\"PARTNER\"," +
                "\"buckle_app_id\":\"1001164\"," +
                "\"buckle_merchant_id\":\"268820000000414397785\"," +
                "\"promo_params\":\"{\\\"key\\\",\\\"value\\\"}\"" +
                "    }," +
                "\"integration_type\":\"PCWEB\"," +
                "\"request_from_url\":\"https://\"," +
                "\"business_params\":\"{\\\"data\\\":\\\"123\\\"}\"," +
                "\"ext_user_info\":{" +
                "\"name\":\"李明\"," +
                "\"mobile\":\"16587658765\"," +
                "\"cert_type\":\"IDENTITY_CARD\"," +
                "\"cert_no\":\"362334768769238881\"," +
                "\"min_age\":\"18\"," +
                "\"fix_buyer\":\"F\"," +
                "\"need_check_info\":\"F\"" +
                "    }" +
                "  }");
        AlipayTradePagePayResponse response = null;
        try {
            response = alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return null;
    }

    /*
    {
    "alipay_trade_page_pay_response": {
        "code": "10000",
        "msg": "Success",
        "trade_no": "2013112011001004330000121536",
        "out_trade_no": "6823789339978248",
        "seller_id": "2088111111116894",
        "total_amount": 128
    },
    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
}
{
    "alipay_trade_page_pay_response": {
        "code": "20000",
        "msg": "Service Currently Unavailable",
        "sub_code": "isp.unknow-error",
        "sub_msg": "系统繁忙"
    },
    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
}
     */

    /**
     * @param requestMy
     * @return
     */
    @Override
    public AlipayTradeQueryResponse query(AlipayTradeQueryRequestMy requestMy) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "app_id", "your private_key", "json", "GBK", "alipay_public_key", "RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"20150320010101001\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "\"total_amount\":88.88," +
                "\"subject\":\"Iphone6 16G\"," +
                "\"body\":\"Iphone6 16G\"," +
                "\"time_expire\":\"2016-12-31 10:05\"," +
                "      \"goods_detail\":[{" +
                "        \"goods_id\":\"apple-01\"," +
                "\"alipay_goods_id\":\"20010001\"," +
                "\"goods_name\":\"ipad\"," +
                "\"quantity\":1," +
                "\"price\":2000," +
                "\"goods_category\":\"34543238\"," +
                "\"body\":\"特价手机\"," +
                "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
                "        }]," +
                "\"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "\"extend_params\":{" +
                "\"sys_service_provider_id\":\"2088511833207846\"," +
                "\"hb_fq_num\":\"3\"," +
                "\"hb_fq_seller_percent\":\"100\"," +
                "\"industry_reflux_info\":\"{\\\\\\\"scene_code\\\\\\\":\\\\\\\"metro_tradeorder\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"xxxx\\\\\\\",\\\\\\\"scene_data\\\\\\\":{\\\\\\\"asset_name\\\\\\\":\\\\\\\"ALIPAY\\\\\\\"}}\"," +
                "\"card_type\":\"S0JP0000\"" +
                "    }," +
                "\"goods_type\":\"0\"," +
                "\"timeout_express\":\"90m\"," +
                "\"promo_params\":\"{\\\"storeIdType\\\":\\\"1\\\"}\"," +
                "\"royalty_info\":{" +
                "\"royalty_type\":\"ROYALTY\"," +
                "        \"royalty_detail_infos\":[{" +
                "          \"serial_no\":1," +
                "\"trans_in_type\":\"userId\"," +
                "\"batch_no\":\"123\"," +
                "\"out_relation_id\":\"20131124001\"," +
                "\"trans_out_type\":\"userId\"," +
                "\"trans_out\":\"2088101126765726\"," +
                "\"trans_in\":\"2088101126708402\"," +
                "\"amount\":0.1," +
                "\"desc\":\"分账测试1\"," +
                "\"amount_percentage\":\"100\"" +
                "          }]" +
                "    }," +
                "\"sub_merchant\":{" +
                "\"merchant_id\":\"19023454\"," +
                "\"merchant_type\":\"alipay: 支付宝分配的间连商户编号, merchant: 商户端的间连商户编号\"" +
                "    }," +
                "\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"store_id\":\"NJ_001\"," +
                "\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"qr_pay_mode\":\"1\"," +
                "\"qrcode_width\":100," +
                "\"settle_info\":{" +
                "        \"settle_detail_infos\":[{" +
                "          \"trans_in_type\":\"cardSerialNo\"," +
                "\"trans_in\":\"A0001\"," +
                "\"summary_dimension\":\"A0001\"," +
                "\"settle_entity_id\":\"2088xxxxx;ST_0001\"," +
                "\"settle_entity_type\":\"SecondMerchant、Store\"," +
                "\"amount\":0.1" +
                "          }]" +
                "    }," +
                "\"invoice_info\":{" +
                "\"key_info\":{" +
                "\"is_support_invoice\":true," +
                "\"invoice_merchant_name\":\"ABC|003\"," +
                "\"tax_num\":\"1464888883494\"" +
                "      }," +
                "\"details\":\"[{\\\"code\\\":\\\"100294400\\\",\\\"name\\\":\\\"服饰\\\",\\\"num\\\":\\\"2\\\",\\\"sumPrice\\\":\\\"200.00\\\",\\\"taxRate\\\":\\\"6%\\\"}]\"" +
                "    }," +
                "\"agreement_sign_params\":{" +
                "\"personal_product_code\":\"GENERAL_WITHHOLDING_P\"," +
                "\"sign_scene\":\"INDUSTRY|CARRENTAL\"," +
                "\"external_agreement_no\":\"test\"," +
                "\"external_logon_id\":\"13852852877\"," +
                "\"sign_validity_period\":\"2m\"," +
                "\"third_party_type\":\"PARTNER\"," +
                "\"buckle_app_id\":\"1001164\"," +
                "\"buckle_merchant_id\":\"268820000000414397785\"," +
                "\"promo_params\":\"{\\\"key\\\",\\\"value\\\"}\"" +
                "    }," +
                "\"integration_type\":\"PCWEB\"," +
                "\"request_from_url\":\"https://\"," +
                "\"business_params\":\"{\\\"data\\\":\\\"123\\\"}\"," +
                "\"ext_user_info\":{" +
                "\"name\":\"李明\"," +
                "\"mobile\":\"16587658765\"," +
                "\"cert_type\":\"IDENTITY_CARD\"," +
                "\"cert_no\":\"362334768769238881\"," +
                "\"min_age\":\"18\"," +
                "\"fix_buyer\":\"F\"," +
                "\"need_check_info\":\"F\"" +
                "    }" +
                "  }");
        AlipayTradePagePayResponse response = null;
        try {
            response = alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return null;
    }

    /*
    {
    "alipay_trade_refund_response": {
        "code": "10000",
        "msg": "Success",
        "trade_no": "支付宝交易号",
        "out_trade_no": "6823789339978248",
        "buyer_logon_id": "159****5620",
        "fund_change": "Y",
        "refund_fee": 88.88,
        "refund_currency": "USD",
        "gmt_refund_pay": "2014-11-27 15:45:57",
        "refund_detail_item_list": [
            {
                "fund_channel": "ALIPAYACCOUNT",
                "amount": 10,
                "real_amount": 11.21,
                "fund_type": "DEBIT_CARD"
            }
        ],
        "store_name": "望湘园联洋店",
        "buyer_user_id": "2088101117955611",
        "refund_preset_paytool_list": {
            "amount": [
                12.21
            ],
            "assert_type_code": "盒马礼品卡:HEMA；抓猫猫红包:T_CAT_COUPON"
        },
        "refund_charge_amount": "8.88",
        "refund_settlement_id": "2018101610032004620239146945",
        "present_refund_buyer_amount": "88.88",
        "present_refund_discount_amount": "88.88",
        "present_refund_mdiscount_amount": "88.88"
    },
    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
}

{
    "alipay_trade_refund_response": {
        "code": "20000",
        "msg": "Service Currently Unavailable",
        "sub_code": "isp.unknow-error",
        "sub_msg": "系统繁忙"
    },
    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
}
     */

    /**
     *
     */
    @Override
    public AlipayTradeRefundResponse refund(AlipayTradeRefundRequestMy requestMy) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "app_id", "your private_key", "json", "GBK", "alipay_public_key", "RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"20150320010101001\"," +
                "\"trade_no\":\"2014112611001004680073956707\"," +
                "\"refund_amount\":200.12," +
                "\"refund_currency\":\"USD\"," +
                "\"refund_reason\":\"正常退款\"," +
                "\"out_request_no\":\"HZ01RF001\"," +
                "\"operator_id\":\"OP001\"," +
                "\"store_id\":\"NJ_S_001\"," +
                "\"terminal_id\":\"NJ_T_001\"," +
                "      \"goods_detail\":[{" +
                "        \"goods_id\":\"apple-01\"," +
                "\"alipay_goods_id\":\"20010001\"," +
                "\"goods_name\":\"ipad\"," +
                "\"quantity\":1," +
                "\"price\":2000," +
                "\"goods_category\":\"34543238\"," +
                "\"categories_tree\":\"124868003|126232002|126252004\"," +
                "\"body\":\"特价手机\"," +
                "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
                "        }]," +
                "      \"refund_royalty_parameters\":[{" +
                "        \"royalty_type\":\"transfer\"," +
                "\"trans_out\":\"2088101126765726\"," +
                "\"trans_out_type\":\"userId\"," +
                "\"trans_in_type\":\"userId\"," +
                "\"trans_in\":\"2088101126708402\"," +
                "\"amount\":0.1," +
                "\"amount_percentage\":100," +
                "\"desc\":\"分账给2088101126708402\"" +
                "        }]," +
                "\"org_pid\":\"2088101117952222\"" +
                "  }");
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return null;
    }
}
