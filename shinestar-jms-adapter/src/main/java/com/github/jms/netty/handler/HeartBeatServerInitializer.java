package com.github.jms.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 *
 * 新建责任链
 *
 * 1.在inbound接口下从前向后新增，在outbound模式下，从后向前新增
 *
 *
 * @author yangcj
 */
public class HeartBeatServerInitializer extends ChannelInitializer<Channel> {


    @Override
    protected void initChannel(Channel channel) throws Exception {
        // 初始化通信流水线
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        // readerIdle 读空闲时间， writeIdleTime写空闲时间， allIdleTime总空闲时间
        pipeline.addLast(new IdleStateHandler(2,2,2, TimeUnit.SECONDS));

        pipeline.addLast(new HeartBeatServerHandler());

    }
}
