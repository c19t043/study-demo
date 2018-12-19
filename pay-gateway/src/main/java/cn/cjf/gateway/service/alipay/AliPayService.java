package cn.cjf.gateway.service.alipay;


import cn.cjf.gateway.domain.param.ali.*;
import com.alipay.api.response.*;

public interface AliPayService {
    /**
     * 支付宝服务器异步通知
     */
    Object notifyUrl(Object obj);

    /**
     * <scene>
     * alipay.trade.precreate(统一收单线下交易预创建)
     * <p>
     * 收银员通过收银台或商户后台调用支付宝接口，生成二维码后，展示给用户，由用户扫描二维码完成订单支付。
     * <p>
     * 支付宝当面付 条码支付
     * </scene>
     * 预下单
     */
    AlipayTradePrecreateResponse preCreate(AlipayTradePrecreateRequestMy requestMy);

    /**
     * <scene>
     * alipay.trade.fastpay.refund.query(统一收单交易退款查询)
     * <p>
     * 商户可使用该接口查询自已通过alipay.trade.refund提交的退款请求是否执行成功。
     * 该接口的返回码10000，仅代表本次查询操作成功，不代表退款成功。
     * 如果该接口返回了查询数据，则代表退款成功，如果没有查询到则代表未退款成功，可以调用退款接口进行重试。
     * 重试时请务必保证退款请求号一致。
     * <p>
     * 商户订单号与支付宝交易号二选一，如果您点击“退款查询”按钮，即表示您同意该次的执行操作
     * </scene>
     * 退款查询
     */
    AlipayTradeFastpayRefundQueryResponse refundQuery(AlipayTradeFastpayRefundQueryRequestMy requestMy);

    /**
     * <scene>
     * alipay.trade.close(统一收单交易关闭接口)
     * <p>
     * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。
     * <p>
     * 商户订单号与支付宝交易号二选一，如果您点击“交易关闭”按钮，即表示您同意该次的执行操作
     * </scene>
     * 交易关闭
     */
    AlipayTradeCloseResponse close(AlipayTradeCloseRequestMy requestMy);

    /**
     * <scene>
     * alipay.trade.page.pay(统一收单下单并支付页面接口)
     * <p>
     * PC场景下单并支付
     * </scene>
     * 统一收单下单并支付页面接口
     */
    AlipayTradePagePayResponse pagePay(AlipayTradePagePayRequestMy requestMy);

    /**
     * <scene>
     * alipay.trade.query(统一收单线下交易查询)
     * <p>
     * 该接口提供所有支付宝支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况： 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * 调用支付接口后，返回系统错误或未知交易状态情况；
     * 调用alipay.trade.pay，返回INPROCESS的状态；
     * 调用alipay.trade.cancel之前，需确认支付状态；
     * <p>
     * 商户订单号与支付宝交易号二选一，如果您点击“交易查询”按钮，即表示您同意该次的执行操作。
     * </scene>
     * 交易查询
     */
    AlipayTradeQueryResponse query(AlipayTradeQueryRequestMy requestMy);

    /**
     * <scene>
     * alipay.trade.refund(统一收单交易退款接口)
     * <p>
     * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，
     * 支付宝将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。
     * 交易超过约定时间（签约时设置的可退款时间）的订单无法进行退款
     * 支付宝退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。
     * 一笔退款失败后重新提交，要采用原来的退款单号。总退款金额不能超过用户实际支付金额
     * <p>
     * 商户订单号与支付宝交易号二选一，如果您点击“退款”按钮，即表示您同意该次的执行操作。
     * </scene>
     * 退款
     */
    AlipayTradeRefundResponse refund(AlipayTradeRefundRequestMy requestMy);
}
