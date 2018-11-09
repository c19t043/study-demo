package demo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUtil {
    /**
     * 获取签名
     * @param payInfo
     * @return
     * @throws Exception
     */
    public String getSign(PayInfo payInfo) throws Exception {
        String signTemp = "appid="+payInfo.getAppid()
                +"&attach="+payInfo.getAttach()
                +"&body="+payInfo.getBody()
                +"&device_info="+payInfo.getDevice_info()
                +"&mch_id="+payInfo.getMch_id()
                +"&nonce_str="+payInfo.getNonce_str()
                +"&notify_url="+payInfo.getNotify_url()
                +"&openid="+payInfo.getOpenid()
                +"&out_trade_no="+payInfo.getOut_trade_no()
                +"&spbill_create_ip="+payInfo.getSpbill_create_ip()
                +"&total_fee="+payInfo.getTotal_fee()
                +"&trade_type="+payInfo.getTrade_type()
                +"&key="+Constants.key; //这个key注意

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.reset();
        md5.update(signTemp.getBytes("UTF-8"));
        String sign = CommonUtil.byteToStr(md5.digest()).toUpperCase();
        return sign;
    }

    public static String getSign(String args, String md5Str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(md5Str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.reset();
        try {
            md5.update(args.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sign = CommonUtil.byteToStr(md5.digest()).toUpperCase();
        return sign;
    }
}
