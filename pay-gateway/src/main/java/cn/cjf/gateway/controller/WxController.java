package cn.cjf.gateway.controller;

import cn.cjf.gateway.config.weixin.WeiXinConfig;
import cn.cjf.gateway.servlet.PayCommonUtil;
import cn.cjf.gateway.servlet.WxPayServlet;
import cn.cjf.gateway.servlet.XMLUtil;
import org.apache.commons.codec.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class WxController {

    private Logger logger = LoggerFactory.getLogger(WxPayServlet.class);

    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //读取参数
        StringBuffer sb = new StringBuffer();
        try (InputStream inputStream = request.getInputStream();
             BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, CharEncoding.UTF_8))) {
            String s;
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
        }


        //解析xml成map
        Map<String, String> m = XMLUtil.doXMLParse(sb.toString());

        //过滤空串 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<>();
        for (Map.Entry<String, String> entry : m.entrySet()) {
            if (null != entry.getValue()) {
                packageParams.put(entry.getKey(), entry.getValue().trim());
            }
        }

        // API秘钥
        String key = WeiXinConfig.API_KEY;

        //判断签名是否正确
        if (PayCommonUtil.isTenpaySign(CharEncoding.UTF_8, packageParams, key)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml;
            if ("SUCCESS".equals(packageParams.get("result_code"))) {
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String) packageParams.get("mch_id");
                String openid = (String) packageParams.get("openid");
                String is_subscribe = (String) packageParams.get("is_subscribe");
                String out_trade_no = (String) packageParams.get("out_trade_no");

                String total_fee = (String) packageParams.get("total_fee");

                logger.info("mch_id:" + mch_id);
                logger.info("openid:" + openid);
                logger.info("is_subscribe:" + is_subscribe);
                logger.info("out_trade_no:" + out_trade_no);
                logger.info("total_fee:" + total_fee);

                //////////执行自己的业务逻辑////////////////

                logger.info("支付成功");
                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

            } else {
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------

            try (OutputStream outputStream = response.getOutputStream();
                 BufferedOutputStream out = new BufferedOutputStream(outputStream)) {
                out.write(resXml.getBytes());
                out.flush();
            }
        } else {
            logger.info("通知签名验证失败");
        }

    }
}
