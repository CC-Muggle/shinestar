package com.github.jms.bean;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.example.http.helloworld.HttpHelloWorldServerHandler;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 *
 * @author yangcj
 */
public class HeartBeatInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast("httpServerCodec", new HttpServerCodec());

        pipeline.addLast("httpServerHandler", new HttpHelloWorldServerHandler());

    }
}
