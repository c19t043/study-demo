package cn.cjf.wx.utils;

import cn.cjf.wx.domain.RequestDomain;
import cn.cjf.wx.domain.ResponseDomain;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUtil {
    private final static Logger LOG = LoggerFactory.getLogger("wx_xmlUtil");

    /**
     * 解析微信消息
     *
     * @param msg
     * @return
     */
    public static RequestDomain parseRequestXml(String msg) {
        // 将字符串转为XML
        try {
            Document doc = DocumentHelper.parseText(msg);
            // 获取根节点
            Element rootElt = doc.getRootElement();
            String toUserName = rootElt.element("ToUserName").getText();
            String fromUserName = rootElt.element("FromUserName").getText();
            String createTime = rootElt.element("CreateTime").getText();
            String msgType = rootElt.element("MsgType").getText();
            String content = rootElt.element("Content").getText();
            String msgId = rootElt.element("MsgId").getText();
            return new RequestDomain(toUserName, fromUserName, createTime,
                    msgType, content, msgId);
        } catch (DocumentException e) {
            LOG.error("解析xml失败,xml为：{}", msg);
        }
        return null;
    }

    /**
     * 格式化微信响应文本消息
     */
    public static String formatResponse2Xml_Text(ResponseDomain responseDomain) {
        String xml = "<xml>" +
                "<ToUserName><![CDATA[@ToUserName]]></ToUserName>" +
                "<FromUserName><![CDATA[@FromUserName]]></FromUserName>" +
                "<CreateTime>@CreateTime</CreateTime>" +
                "<MsgType><![CDATA[@MsgType]]></MsgType>" +
                "<Content><![CDATA[@Content]]></Content>" +
                "</xml>";
        return xml.replace("@ToUserName", responseDomain.getToUserName())
                .replace("@FromUserName", responseDomain.getFromUserName())
                .replace("@CreateTime", responseDomain.getCreateTime())
                .replace("@MsgType", responseDomain.getMsgType())
                .replace("@Content", responseDomain.getContent());
    }
    /**
     * 格式化微信响应图片消息
     */
    public static String formatResponse2Xml_Image(ResponseDomain responseDomain) {
        String xml = "<xml>" +
                "<ToUserName><![CDATA[@ToUserName]]></ToUserName>" +
                "<FromUserName><![CDATA[@FromUserName]]></FromUserName>" +
                "<CreateTime>@CreateTime</CreateTime>" +
                "<MsgType><![CDATA[@MsgType]]></MsgType>" +
                "<Image>" +
                "<MediaId><![CDATA[{MediaId}]]></MediaId>\n" +
                "</Image>" +
                "</xml>";
        return xml.replace("@ToUserName", responseDomain.getToUserName())
                .replace("@FromUserName", responseDomain.getFromUserName())
                .replace("@CreateTime", responseDomain.getCreateTime())
                .replace("@MsgType", responseDomain.getMsgType())
                .replace("@Content", responseDomain.getContent());
    }
//    <xml>
//        <ToUserName><![CDATA[{ToUserName}]]></ToUserName>
//        <FromUserName><![CDATA[{FromUserName}]]></FromUserName>
//        <CreateTime>{CreateTime}</CreateTime>
//        <MsgType><![CDATA[image]]></MsgType>
//
//    </xml>


    public static void main(String[] args) {
//        String msg = "<xml><ToUserName><![CDATA[公众号]]></ToUserName><FromUserName><![CDATA[粉丝号]]></FromUserName>"
//                + "<CreateTime>1460537339</CreateTime><MsgType><![CDATA[text]]></MsgType>"
//                + "<Content><![CDATA[欢迎开启公众号开发者模式]]></Content>"
//                + "<MsgId>6272960105994287618</MsgId></xml>";
//        System.out.println(parseRequestXml(msg));

//        <xml>
        // <ToUserName><![CDATA[粉丝号]]></ToUserName>
        // <FromUserName><![CDATA[公众号]]></FromUserName>
        // <CreateTime>1460541339</CreateTime>
        // <MsgType><![CDATA[text]]></MsgType>
        // <Content><![CDATA[test]]></Content>
        // </xml>

//        System.out.println(formatResponse2Xml(new ResponseDomain("粉丝号", "公众号",
//                "1460541339", "text", "test")));
    }
}
