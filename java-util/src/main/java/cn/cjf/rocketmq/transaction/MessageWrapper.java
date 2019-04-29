package cn.cjf.rocketmq.transaction;

import com.alibaba.rocketmq.common.message.Message;

/**
 * Created by error on 2017/4/19.
 */
public class MessageWrapper {
    private Message message;

    MessageWrapper() {

    }

    public MessageWrapper(Message message) {
        this.message = message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
