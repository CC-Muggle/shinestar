package com.github.jms.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * 简单通信
 *
 * @author yangcj
 */
public class ChatHandlerServer {

    public static void main(String[] args) {

        ServerBootstrap bootstrap = new ServerBootstrap();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {

                        // 为通道添加责任链
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();

                            //处理读取到的消息
                            pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                    // 获取可读字节数组
                                    byte[] bytes = new byte[msg.readableBytes()];
                                    msg.readBytes(bytes);
                                    String request = new String(bytes, "UTF-8");
                                    System.out.println("接收到的消息：" + request);

                                    // 保存至缓存中
                                    ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);

                                    // 责任链模式，传递给下一个责任链
                                    ctx.fireChannelRead(byteBuf);
                                }
                            });

                            pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                    // 获取可读字节数组
                                    byte[] bytes = new byte[msg.readableBytes()];
                                    msg.readBytes(bytes);
                                    String request = new String(bytes, "UTF-8");
                                    System.out.println("要发送的消息：" + request);

                                    // 8是很懂每次都要cp到缓存里
                                    ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);

                                    // 回写信息
                                    ctx.writeAndFlush(byteBuf);
                                }
                            });
                        }
                    });

            // 端口绑定成功后
            ChannelFuture future = bootstrap.bind(8081).sync();

            // 当端口关闭会被唤醒
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
