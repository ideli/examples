package _socket.chap05.netty.fixedLength;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 固定长度的消息
 * @author Administrator
 *
 */
public class EchoServer {

	public static void main(String[] args) throws Exception {
		int port = 18080;
		new EchoServer().bind(port);
	}

	public void bind(int port) throws InterruptedException {
		//配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childHandler(new ChildChannelHandler());
			//绑定端口, 同步等待成功
			ChannelFuture f = b.bind(port).sync();
			//等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel channel) throws Exception {
			channel.pipeline().addLast(new FixedLengthFrameDecoder(16));
			channel.pipeline().addLast(new StringDecoder());
			channel.pipeline().addLast(new EchoServerHandler());
		}
	}
	
	private class EchoServerHandler extends ChannelHandlerAdapter {
		
		private int counter;
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
			String body = (String)msg;
			System.out.println("body: " + body + "; counter: " + ++counter);
			String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) : "BAD ORDER";
			currentTime = String.format("%-20s", currentTime);
			ByteBuf resp = Unpooled.copiedBuffer((currentTime).getBytes());
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
