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
}
