package demo1;

import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Hashtable;

@Controller
public class WxPayController {
    /**
     * 创建二维码
     */
    @RequestMapping("createQRCode")
    public void createQRCode(String orderId, HttpServletResponse response) {

        //生成订单
        String orderInfo = createOrderInfo(orderId);
        //调统一下单API
        String code_url = httpOrder(orderInfo);
        //将返回预支付交易链接（code_url）生成二维码图片
        //这里使用的是zxing   <span style="color:#ff0000;"><strong>说明1(见文末)</strong></span>
        try {
            int width = 200;
            int height = 200;
            String format = "png";
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(code_url, BarcodeFormat.QR_CODE, width, height, hints);
            OutputStream out = null;
            out = response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, format, out);
            out.flush();
            out.close();
        } catch (Exception e) {
        }

    }

    /**
     * 生成订单
     *
     * @param orderId
     * @return
     */
    private String createOrderInfo(String orderId) {
        //生成订单对象
        UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
        unifiedOrderRequest.setAppid("xxxxxxxxxxxxx");//公众账号ID
        unifiedOrderRequest.setMch_id("xxxxxxxxx");//商户号
        unifiedOrderRequest.setNonce_str(StringUtil.makeUUID());//随机字符串       <span style="color:#ff0000;"><strong>说明2(见文末)</strong></span>
        unifiedOrderRequest.setBody("xxxxxx");//商品描述
        unifiedOrderRequest.setOut_trade_no(orderId);//商户订单号
        unifiedOrderRequest.setTotal_fee("x");    //金额需要扩大100倍:1代表支付时是0.01
        unifiedOrderRequest.setSpbill_create_ip("xxxxxxxxxxxxx");//终端IP
        unifiedOrderRequest.setNotify_url("xxxxxxxxxxxxxx");//通知地址
        unifiedOrderRequest.setTrade_type("NATIVE");//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
        unifiedOrderRequest.setSign(createSign(unifiedOrderRequest));//签名<span style="color:#ff0000;"><strong>说明5(见文末，签名方法一并给出)</strong></span>
        //将订单对象转为xml格式
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_"))); //<span style="color:#ff0000;"><strong>说明3(见文末)</strong></span>
        xStream.alias("xml", UnifiedOrderRequest.class);//根元素名需要是xml
        return xStream.toXML(unifiedOrderRequest);
    }
}
