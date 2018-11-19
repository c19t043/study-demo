package cn.cjf.gateway.service.wxpay;

import cn.cjf.gateway.domain.param.weixin.*;

public interface WeiXinPayHttpService {
    /**
     * <scene>
     * 除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，
     * 返回正确的预支付交易会话标识后再按扫码、JSAPI、APP等不同场景生成交易串调起支付。
     * </scene>
     * 统一下单
     */
    WeiXinUnifiedOrderResponse unifiedOrder(WeiXinUnifiedOrderRequest request);

    /**
     * <scene>
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况：
     * 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * 调用支付接口后，返回系统错误或未知交易状态情况；
     * 调用付款码支付API，返回USERPAYING的状态；
     * 调用关单或撤销接口API之前，需确认支付状态；
     * </scene>
     * 查询订单
     */
    WeiXinQueryOrderResponse queryOrder(WeiXinQueryOrderRequest request);

    /**
     * <scene>
     * 以下情况需要调用关单接口：
     * 商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
     * 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
     * </scene>
     * 关闭订单
     */
    WeiXinCloseOrderResponse closeOrder(WeiXinCloseOrderRequest request);

    /**
     * <scene>
     * 请求需要双向证书。 详见证书使用
     * <p>
     * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家
     * 微信支付将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。
     * 注意：
     * 1、交易时间超过一年的订单无法提交退款
     * 2、微信支付退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。
     * 申请退款总金额不能超过订单金额。
     * 一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号
     * 3、请求频率限制：150qps，即每秒钟正常的申请退款请求次数不超过150次
     * 错误或无效请求频率限制：6qps，即每秒钟异常或错误的退款申请请求不超过6次
     * 4、每个支付订单的部分退款次数不能超过50次
     * </scene>
     * 申请退款
     */
    WeiXinRefundResponse refundOrder(WeiXinRefundRequest request);

    /**
     * <scene>
     * 提交退款申请后，通过调用该接口查询退款状态。
     * 退款有一定延时，用零钱支付的退款20分钟内到账，银行卡支付的退款3个工作日后重新查询退款状态。
     * 分页查询
     * 当一个订单部分退款超过10笔后，商户用微信订单号或商户订单号调退款查询API查询退款时，
     * 默认返回前10笔和total_refund_count（订单总退款次数）。
     * 商户需要查询同一订单下超过10笔的退款单时，可传入订单号及offset来查询，
     * 微信支付会返回offset及后面的10笔，以此类推。当商户传入的offset超过total_refund_count，
     * 则系统会返回报错PARAM_ERROR。
     * 举例：
     * 一笔订单下的退款单有36笔，当商户想查询第25笔时，可传入订单号及offset=24，
     * 微信支付平台会返回第25笔到第35笔的退款单信息，或商户可直接传入退款单号查询退款
     * </scene>
     * 查询退款
     */
    WeiXinQueryRefundResponse queryRefundOrder(WeiXinQueryRefundRequest request);

    /**
     * <scene>
     * 收银员使用扫码设备读取微信用户付款码授权码以后，二维码或条码信息传送至商户收银台，
     * 由商户收银台或者商户后台调用该接口发起支付。
     * <p>
     * 提醒1：提交支付请求后微信会同步返回支付结果。当返回结果为“系统错误”时，商户系统等待5秒后调用【查询订单API】，
     * 查询支付实际交易结果；当返回结果为“USERPAYING”时，商户系统可设置间隔时间(建议10秒)重新查询支付结果，
     * 直到支付成功或超时(建议30秒)；
     * <p>
     * 提醒2：在调用查询接口返回后，如果交易状况不明晰，请调用【撤销订单API】，
     * 此时如果交易失败则关闭订单，该单不能再支付成功；如果交易成功，则将扣款退回到用户账户。
     * 当撤销无返回或错误时，请再次调用。
     * 注意：请勿扣款后立即调用【撤销订单API】,建议至少15秒后再调用。撤销订单API需要双向证书。
     * </scene>
     * 提交付款码支付
     */
    WeiXinMicroPayResponse microPay(WeiXinMicroPayRequest request);

    /**
     * <scene>
     * 请求需要双向证书。 详见证书使用
     * <p>
     * 支付交易返回失败或支付系统超时，调用该接口撤销交易。
     * 如果此订单用户支付失败，微信支付系统会将此订单关闭；
     * 如果用户支付成功，微信支付系统会将此订单资金退还给用户。
     * <p>
     * 注意：7天以内的交易单可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。
     * 提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。
     * </scene>
     * 撤销订单
     */
    WeiXinReverseResponse reverse(WeiXinReverseRequest request);
}
