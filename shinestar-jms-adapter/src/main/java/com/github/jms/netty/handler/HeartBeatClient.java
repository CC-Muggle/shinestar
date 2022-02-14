package com.github.jms.netty.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Random;

/**
 * @author yangcj
 */
public class HeartBeatClient {

    int port;
    Random random ;

    public HeartBeatClient(int port){
        this.port = port;
        random = new Random();
    }

    public void start(){
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            bootstrap.group(worker)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .channel(NioSocketChannel.class)
                    .handler(new HeartBeatClientInitializer());

            Channel channel = bootstrap.connect("127.0.0.1", this.port)
                    .sync()
                    .channel();
            String text = "I am alive";

            while (channel.isActive()){
                int num = 1;
                Thread.sleep(num * 1000);
                channel.writeAndFlush(text);
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        HeartBeatClient client = new HeartBeatClient(8090);
        client.start();
    }
}
