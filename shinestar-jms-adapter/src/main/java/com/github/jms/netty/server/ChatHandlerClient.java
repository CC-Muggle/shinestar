package com.github.jms.netty.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * 简单通信
 *
 * @author yangcj
 */
public class ChatHandlerClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            // 责任链
                            ChannelPipeline pipeline = channel.pipeline();

                            pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {

                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                    // 获取可读字节数组
                                    byte[] bytes = new byte[msg.readableBytes()];
                                    msg.readBytes(bytes);
                                    String response = new String(bytes, "UTF-8");
                                    System.out.println("响应的消息：" + response);
                                }
                            });

                        }
                    });

            // 连接ip+port成功前为阻塞状态
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8081).sync();
            Channel channel = future.channel();
            while (channel.isActive()){
                Scanner scanner = new Scanner(System.in);
                String readIn = scanner.nextLine();
                ByteBuf byteBuf = Unpooled.copiedBuffer(readIn.getBytes(StandardCharsets.UTF_8));

                // channel激活时输入内容
                channel.writeAndFlush(byteBuf);
            }

            // 单纯的为挂起主线程而形成阻塞
//            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
