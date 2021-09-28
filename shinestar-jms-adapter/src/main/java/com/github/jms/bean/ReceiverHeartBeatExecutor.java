package com.github.jms.bean;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 心跳接收
 * @author yangcj
 *
 */
public class ReceiverHeartBeatExecutor {


    private ThreadPoolExecutor poolExecutor = new java.util.concurrent.ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new SynchronousQueue<>());

    public static void main(String[] args) {
        new ReceiverHeartBeatExecutor().start();

    }

    public void start(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            bootstrap.group(boss, worker)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HeartBeatInitializer());
            ChannelFuture future = bootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
