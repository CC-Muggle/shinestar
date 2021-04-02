package com.github.jms.template;

/**
 * 通过创建统一化接口从而实现对发送信息的监控
 *
 *
 */
public interface ShineJmsTemplate {

    /**
     * 发送消息
     * @return
     */
    Object sendMessage();
}
