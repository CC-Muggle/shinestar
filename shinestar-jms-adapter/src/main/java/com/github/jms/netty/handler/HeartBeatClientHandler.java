package com.github.jms.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author yangcj
 */
public class HeartBeatClientHandler extends SimpleChannelInboundHandler<String> {


    /**
     * 接收到的字符串
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(" ====== > [client] message received : " + s);
        if(s!= null && s.equals("you are out")) {
            System.out.println(" server closed connection , so client will close too");
            channelHandlerContext.channel().closeFuture();
        }
    }
}
