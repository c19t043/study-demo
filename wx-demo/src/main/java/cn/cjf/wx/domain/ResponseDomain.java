package cn.cjf.wx.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ResponseDomain {
    private String toUserName;
    private String fromUserName;
    private String createTime;
    private String msgType;
    private String content;
}
