package cn.cjf.pay.weixin;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;

import java.util.Map;

/**
 * @author chenjunfan
 * @date 2019/5/9
 */
public class WeixinPayCore {
    private WXPayConfig wxPayConfig;
    private String notifyUrl;

    /**
     * 作用：提交刷卡支付<br>
     * 场景：刷卡支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> microPay(Map<String, String> reqData) throws Exception {
        return new WXPay(wxPayConfig, notifyUrl).microPay(reqData);
    }

    /**
     * 作用：统一下单<br>
     * 场景：公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> unifiedOrder(Map<String, String> reqData) throws Exception {
        return new WXPay(wxPayConfig, notifyUrl).unifiedOrder(reqData);
    }

    /**
     * 作用：查询订单<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> orderQuery(Map<String, String> reqData) throws Exception {
        return new WXPay(wxPayConfig, notifyUrl).orderQuery(reqData);
    }

    /**
     * 作用：撤销订单<br>
     * 场景：刷卡支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> reverse(Map<String, String> reqData) throws Exception {
        return new WXPay(wxPayConfig, notifyUrl).reverse(reqData);
    }

    /**
     * 作用：关闭订单<br>
     * 场景：公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> closeOrder(Map<String, String> reqData) throws Exception {
        return new WXPay(wxPayConfig, notifyUrl).closeOrder(reqData);
    }


    /**
     * 作用：申请退款<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> refund(Map<String, String> reqData) throws Exception {
        return new WXPay(wxPayConfig, notifyUrl).refund(reqData);
    }

    /**
     * 作用：退款查询<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> refundQuery(Map<String, String> reqData) throws Exception {
        return new WXPay(wxPayConfig, notifyUrl).refundQuery(reqData);
    }

    /**
     * 作用：对账单下载（成功时返回对账单数据，失败时返回XML格式数据）<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> downloadBill(Map<String, String> reqData) throws Exception {
        return new WXPay(wxPayConfig, notifyUrl).downloadBill(reqData);
    }

    /**
     * 作用：交易保障<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> report(Map<String, String> reqData) throws Exception {
        return new WXPay(wxPayConfig, notifyUrl).report(reqData);
    }

    /**
     * 作用：转换短链接<br>
     * 场景：刷卡支付、扫码支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> shortUrl(Map<String, String> reqData) throws Exception {
        return new WXPay(wxPayConfig, notifyUrl).shortUrl(reqData);
    }

    /**
     * 作用：授权码查询OPENID接口<br>
     * 场景：刷卡支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> authCodeToOpenid(Map<String, String> reqData) throws Exception {
        return new WXPay(wxPayConfig, notifyUrl).authCodeToOpenid(reqData);
    }
}
