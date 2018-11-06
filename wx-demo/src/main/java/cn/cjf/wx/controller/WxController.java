package cn.cjf.wx.controller;

import cn.cjf.wx.constant.MessageType;
import cn.cjf.wx.domain.RequestDomain;
import cn.cjf.wx.domain.ResponseDomain;
import cn.cjf.wx.utils.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WxController {
    private final static Logger LOG = LoggerFactory.getLogger("wx_WxController");
    private final String SUCCESS = "success";

    @RequestMapping("/message/autoReply")
    @ResponseBody
    public String wxTextHandle(String msg) {
        LOG.info("调用处理微信文本消息接口：{}", msg);
        if (StringUtils.isNotBlank(msg)) {
            RequestDomain requestDomain = XmlUtil.parseRequestXml(msg);
            if (requestDomain != null) {
                if (MessageType.TEXT.equalsIgnoreCase(requestDomain.getMsgType())) {
                    return XmlUtil.formatResponse2Xml_Text(
                            new ResponseDomain(requestDomain.getFromUserName(), requestDomain.getToUserName(),
                                    System.currentTimeMillis() + "",
                                    MessageType.TEXT, "text"));
                }
//            else if (MessageType.IMAGE.equalsIgnoreCase(requestDomain.getMsgType())) {
//                XmlUtil.formatResponse2Xml_Text(
//                        new ResponseDomain(requestDomain.getFromUserName(),
//                                requestDomain.getMsgType(), System.currentTimeMillis() + "",
//                                MessageType.IMAGE, "text"));
//            }
            }
        }
        return SUCCESS;
    }
}
