package com.github.jms.netty.handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 * netty服务端启动类
 *
 * boss线程（负责接收）和worker线程（处理业务逻辑）
 *
 * @author yangcj
 */
public class HeartBeatServer {

    private int port ;

    public HeartBeatServer(int port){
        this.port = port;
    }

    public void start(){

        // 创建服务端接收启动器
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            bootstrap.group(boss,worker)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HeartBeatServerInitializer());

            // 通道绑定端口
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HeartBeatServer server = new HeartBeatServer(8090);
        server.start();
    }
}
