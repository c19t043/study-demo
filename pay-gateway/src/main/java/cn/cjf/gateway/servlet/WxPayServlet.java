package cn.cjf.gateway.servlet;

import cn.cjf.gateway.config.weixin.WeiXinConfig;
import org.apache.commons.codec.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class WxPayServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(WxPayServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //获取要购买的商品
        req.setCharacterEncoding(CharEncoding.UTF_8);
        //用户要购买的商品
        String body = req.getParameter("body");
        //商品价格单位分
        String price = "1";
        //商户订单号
        String outTradeNo = "123";
        //生成二维码
        //得到二维码的原始字符
        try {
            String resultStr = PayCommonUtil.weixin_pay(body, price, outTradeNo);
            BufferedImage image = ZxingUtils.createImage(resultStr, 300, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将原始字符转成二维码

        //跳转到支付页面，显示二维码
    }


    public void weixin_notify(HttpServletRequest request,HttpServletResponse response) throws Exception{

        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XMLUtil.doXMLParse(sb.toString());

        //过滤空 设置 TreeMap
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        // 账号信息
        String key = WeiXinConfig.API_KEY; // key

        //判断签名是否正确
        if(PayCommonUtil.isTenpaySign("UTF-8", packageParams,key)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String)packageParams.get("mch_id");
                String openid = (String)packageParams.get("openid");
                String is_subscribe = (String)packageParams.get("is_subscribe");
                String out_trade_no = (String)packageParams.get("out_trade_no");

                String total_fee = (String)packageParams.get("total_fee");

                logger.info("mch_id:"+mch_id);
                logger.info("openid:"+openid);
                logger.info("is_subscribe:"+is_subscribe);
                logger.info("out_trade_no:"+out_trade_no);
                logger.info("total_fee:"+total_fee);

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
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            logger.info("通知签名验证失败");
        }

    }
}
