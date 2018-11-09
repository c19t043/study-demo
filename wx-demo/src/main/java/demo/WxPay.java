package demo;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信支付对象
 * @author rory.wu
 *
 */
@Data
public class WxPay implements Serializable {
    private static final long serialVersionUID = 3843862351717555525L;
    private String paySign;
    private String prepay_id;
    private String nonce_str;
    private String timeStamp;
    
    //get，set方法
}