package cn.cjf.netty.server.session;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * @author CJF
 */
@Data
@AllArgsConstructor
public class SessionVo {
    private String userId;
    private Channel channel;

}
