package demo;

public class Test2 {
    /**
     * 获取页面上weixin支付JS所需的参数
     * @return
     */
    private WxPay getWxPayInfo(String prepay_id) {
        String nonce = CommonUtil.create_nonce_str().replace("-", "");
        String timeStamp = CommonUtil.create_timestamp();
        //再算签名
        String newPrepay_id = "prepay_id="+prepay_id;
        String args = "appId="+Constants.appid
                +"&nonceStr="+nonce
                +"&package="+newPrepay_id
                +"&signType=MD5"
                +"&timeStamp="+timeStamp
                +"&key="+Constants.key;
        String paySign = SignUtil.getSign(args, "MD5");
        WxPay wxPay = new WxPay();
        wxPay.setNonce_str(nonce);
        wxPay.setPaySign(paySign);
        wxPay.setPrepay_id(newPrepay_id);
        wxPay.setTimeStamp(timeStamp);
        return wxPay;
    }
}
