package _socket.chap09.netty.privateProtocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import _socket.Message;

public class NettyServer {

	public static void main(String[] args) throws Exception {
		new NettyServer().bind("127.0.0.1", 18080);
	}

	public void bind(String host, int port) throws InterruptedException {
		// 配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 100)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch)
							throws IOException {
						ch.pipeline().addLast(new MessageDecoder(1024 * 1024, 4, 4));
						ch.pipeline().addLast(new MessageEncoder());
						ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
						ch.pipeline().addLast("LoginAuthHandler", new LoginAuthRespHandler());
						ch.pipeline().addLast("HeartBeatHandler", new HeartBeatRespHandler());
						ch.pipeline().addLast(new MessageHandler());
					}
				});

		// 绑定端口，同步等待成功
		ChannelFuture future = b.bind(port).sync();
		System.out.println("Netty server start ok : " + (host + " : " + port));
		future.channel().closeFuture().sync();
	}
	
	private class MessageHandler extends ChannelHandlerAdapter {
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object in) throws UnsupportedEncodingException {
			Message msg = (Message)in;
			System.out.println("receive message: " + msg.readString());
			Message resp = new Message(msg.getMessageNum() + 1);
			resp.writeString("got it");
			ctx.write(resp);
		}
		
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
		}
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			cause.printStackTrace();
			ctx.close();
		}
	}
}
