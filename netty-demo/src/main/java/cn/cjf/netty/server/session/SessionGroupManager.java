package cn.cjf.chat.server.session;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 群组session管理
 *
 * @author CJF
 */
public final class SessionGroupManager {
    private final Map<String, Set<String>> groupMapping = new HashMap<>();


    public void addGroupMapping(String groupId, String userId) {
        Set<String> userIdSet;
        if (groupMapping.containsKey(groupId)) {
            userIdSet = groupMapping.get(groupId);
        } else {
            userIdSet = new HashSet<>();
            groupMapping.put(groupId, userIdSet);
        }

        userIdSet.add(userId);
    }

    public Set<String> getGroupMapping(String groupId) {
        return groupMapping.get(groupId);
    }

    private static class SingleHolder {
        private static SessionGroupManager instance;

        static {
            instance = new SessionGroupManager();
        }
    }

    public static SessionGroupManager getInstance() {
        return SingleHolder.instance;
    }
}
