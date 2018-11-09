package demo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayInfo  implements Serializable{
    
    private static final long serialVersionUID = 5637164279924222380L;
    private String appid;
    private String mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String body;
    private String attach;
    private String out_trade_no;
    private int total_fee;
    private String spbill_create_ip;
    private String notify_url;
    private String trade_type;
    private String openid;
        
    //下面是get,set方法       
}