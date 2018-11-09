package cn.cjf.wx.controller;

import cn.cjf.common.log.MyLog;
import cn.cjf.wx.service.INotify4WxPayService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 接收处理微信通知
 * @author chenjunfan 176158750@qq.com
 * @date 2018-11-09
 */
@RestController
public class Notify4WxPayController {

	private static final MyLog LOG = MyLog.getLog(Notify4WxPayController.class);

	@Autowired
	private INotify4WxPayService notify4WxPayService;

	/**
	 * 微信支付(统一下单接口)后台通知响应
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
     */
	@RequestMapping("/notify/pay/wxPayNotifyRes.htm")
	@ResponseBody
	public String wxPayNotifyRes(HttpServletRequest request) throws ServletException, IOException {
		LOG.info("====== 开始接收微信支付回调通知 ======");
		String notifyRes = doWxPayRes(request);
		LOG.info("响应给微信:{}", notifyRes);
		LOG.info("====== 完成接收微信支付回调通知 ======");
		return notifyRes;
	}

	private String doWxPayRes(HttpServletRequest request) throws ServletException, IOException {
		String logPrefix = "【微信支付回调通知】";
		String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
		LOG.info("{}通知请求数据:reqStr={}", logPrefix, xmlResult);
		return notify4WxPayService.handleWxPayNotify(xmlResult);
	}

}
