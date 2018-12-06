package cn.cjf.chat.server.session;

import cn.cjf.chat.config.ConstantConfig;
import cn.cjf.chat.domain.LoginPacket;
import io.netty.channel.Channel;
import io.netty.util.internal.ConcurrentSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author CJF
 */
public final class SessionManager {
    private final Set<SessionVo> sessionVoSet = new ConcurrentSet<SessionVo>();
    private final Map<String, Channel> channelMap = new HashMap<>(ConstantConfig.MAP_INITIAL_CAPACITY);
    private final Map<String, LoginPacket> userMap = new HashMap<>(ConstantConfig.MAP_INITIAL_CAPACITY);

    public void addSession(SessionVo sessionVo) {
        sessionVoSet.add(sessionVo);
        channelMap.put(sessionVo.getUserId(), sessionVo.getChannel());
    }

    public Channel getChannel(String userId) {
        return channelMap.get(userId);
    }

    public void putUser(String userId, LoginPacket user) {
        userMap.put(userId, user);
    }

    public LoginPacket getUser(String userId) {
        return userMap.get(userId);
    }

    public Set<SessionVo> getSessions() {
        return sessionVoSet;
    }

    private SessionManager() {
    }

    private static class SingleHolder {
        private static SessionManager instance;

        static {
            instance = new SessionManager();
        }
    }

    public static SessionManager getInstance() {
        return SingleHolder.instance;
    }
}
