package _socket.chap06.netty.messagePack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class UserTransferClient {

	public static void main(String[] args) throws Exception {
		int port = 18080;
		new UserTransferClient().connect(port, "127.0.0.1");
	}

	public void connect(int port, String host) throws Exception {
		//配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>(){
					@Override
					protected void initChannel(SocketChannel channel)
							throws Exception {
						channel.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
						channel.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
						channel.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
						channel.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
						channel.pipeline().addLast(new UserTransferClientHandler());
					}
				});
			//发起异步连接操作
			ChannelFuture f = b.connect(host, port).sync();
			//等待客户端链路关闭
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}

class UserTransferClientHandler extends ChannelHandlerAdapter {
	private int counter;
	
	public UserTransferClientHandler() {
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		for(int i = 0; i < 100; i++) {
			User user = new User();
			user.setName("User" + i);
			user.setAge(i);
			ctx.writeAndFlush(user);
		}
		ctx.flush();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("body: " + msg + "; counter: " + ++counter);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
