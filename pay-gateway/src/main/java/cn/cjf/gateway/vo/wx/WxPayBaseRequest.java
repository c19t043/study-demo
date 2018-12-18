package cn.cjf.gateway.vo.wx;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class WxPayBaseRequest {
    @XStreamAlias("appid")
    protected String appid;
    @XStreamAlias("mch_id")
    protected String mchId;
    @XStreamAlias("sub_appid")
    protected String subAppId;
    @XStreamAlias("sub_mch_id")
    protected String subMchId;
    @XStreamAlias("nonce_str")
    protected String nonceStr;
    @XStreamAlias("sign")
    protected String sign;
    @XStreamAlias("sign_type")
    private String signType;
}
