package cn.cjf.wx.servlet;

import cn.cjf.wx.constant.MessageType;
import cn.cjf.wx.utils.MyStringUtil;
import cn.cjf.wx.utils.WxServiceUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 处理微信服务器发来的消息
 */
@WebServlet("/wx")
public class WxAuth extends HttpServlet {
    private final static Logger LOG = LoggerFactory.getLogger("wx_WxAuth");
    /**
     * 微信工具类
     */
    private final WxMpService wxService = WxServiceUtil.getWxService();
    private final String SUCCESS = "success";

    /**
     * 微信接口验证
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestXml = MyStringUtil.parseFromIO_In(request);
        LOG.info("调用用于验证是否是微信服务器发来的消息：{}", requestXml);
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        boolean flag = wxService.checkSignature(timestamp, nonce, signature);

        if (flag) {
            print(echostr, response);
        }
    }

    /**
     * 处理微信消息
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestXml = MyStringUtil.parseFromIO_In(request);
        LOG.info("调用用于验证是否是微信服务器发来的消息：{}", requestXml);
        //获取消息流
        WxMpXmlMessage message = WxMpXmlMessage.fromXml(requestXml);
        doHandle(message, response);
    }

    private void doHandle(WxMpXmlMessage message, HttpServletResponse response) {
        //消息处理
        switch (message.getMsgType()) {
            case MessageType.TEXT:
                doHandleText(message, response);
                break;
            case MessageType.IMAGE:
                doHandleImage(message, response);
                break;
            default:
                print(SUCCESS, response);
        }
    }

    private void doHandleImage(WxMpXmlMessage message, HttpServletResponse response) {
//        <xml>
//             <ToUserName><![CDATA[公众号]]></ToUserName>
//             <FromUserName><![CDATA[粉丝号]]></FromUserName>
//             <CreateTime>1460536575</CreateTime>
//             <MsgType><![CDATA[image]]></MsgType>
//             <PicUrl><![CDATA[http://mmbiz.qpic.cn/xxxxxx /0]]></PicUrl>
//             <MsgId>6272956824639273066</MsgId>
//             <MediaId><![CDATA[gyci5a-xxxxx-OL]]></MediaId>
//         </xml>

        //PicUrl: 这个参数是微信系统把“粉丝“发送的图片消息自动转化成url。 这个url可用浏览器打开查看到图片
        //MediaId: 是微信系统产生的id 用于标记该图片

        String fromUser = message.getFromUser();
        String toUser = message.getToUser();
        String mediaId = message.getMediaId();
        WxMpXmlOutImageMessage image = WxMpXmlOutImageMessage.IMAGE().toUser(fromUser).fromUser(toUser).mediaId(mediaId).build();

        String xml = image.toXml();
        print(xml, response);

//        <xml>
//             <ToUserName><![CDATA[粉丝号]]></ToUserName>
//             <FromUserName><![CDATA[公众号]]></FromUserName>
//             <CreateTime>1460536576</CreateTime>
//             <MsgType><![CDATA[image]]></MsgType>
//             <Image>
//             <MediaId><![CDATA[gyci5oxxxxxxv3cOL]]></MediaId>
//             </Image>
//         </xml>
    }

    private void doHandleText(WxMpXmlMessage message, HttpServletResponse response) {
//        <xml>
        //         <ToUserName><![CDATA[公众号]]></ToUserName>
        //         <FromUserName><![CDATA[粉丝号]]></FromUserName>
        //         <CreateTime>1460537339</CreateTime>
        //         <MsgType><![CDATA[text]]></MsgType>
        //         <Content><![CDATA[欢迎开启公众号开发者模式]]></Content>
        //         <MsgId>6272960105994287618</MsgId>
//         </xml>
        String fromUser = message.getFromUser();
        String toUser = message.getToUser();


//        1）ToUserName（接受者）、FromUserName(发送者) 字段请实际填写。
//        2）createTime 只用于标记开发者回复消息的时间，微信后台发送此消息都是不受这个字段约束。
//        3）text : 用于标记 此次行为是发送文本消息 （当然可以是image/voice等类型）。
//        4）文本换行 ‘\n’。

        //收到粉丝消息后不想或者不能5秒内回复时，需回复“success”字符串
        //假如服务器无法保证在五秒内处理回复，则必须回复“success”或者“”（空串），否则微信后台会发起三次重试。

        WxMpXmlOutTextMessage text = WxMpXmlOutTextMessage.TEXT().toUser(fromUser).fromUser(toUser).content("我是文本消息").build();

        String xml = text.toXml();
        print(xml, response);

//        <xml>
//             <ToUserName><![CDATA[粉丝号]]></ToUserName>
//             <FromUserName><![CDATA[公众号]]></FromUserName>
//             <CreateTime>1460541339</CreateTime>
//             <MsgType><![CDATA[text]]></MsgType>
//             <Content><![CDATA[test]]></Content>
//         </xml>
    }

    private void print(String xml, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            LOG.info("xml:{}", xml);
            out.print(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}