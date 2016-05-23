package _socket.chap03.netty.lineBased;

import java.util.concurrent.Executors;

import io.netty.bootstrap.Bootstrap;
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
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient implements Runnable {

	public static void main(String[] args) throws Exception {
		int port = 18080;
		TimeClient client = new TimeClient("127.0.0.1", port);
		Executors.newSingleThreadExecutor().execute(client);
		Thread.sleep(1000);
		client.stop();
	}
	
	private EventLoopGroup group;
	private String host;
	private int port;
	
	public TimeClient(String host, int port) {
		group = new NioEventLoopGroup();
		this.host = host;
		this.port = port;
	}

	public void connect() throws Exception {
		//配置客户端NIO线程组
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>(){
					@Override
					protected void initChannel(SocketChannel channel)
							throws Exception {
						channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
						channel.pipeline().addLast(new StringDecoder());
						channel.pipeline().addLast(new TimeClientHandler());
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
	
	public void stop() {
		if(group != null) {
			group.shutdownGracefully();
		}
	}

	@Override
	public void run() {
		try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class TimeClientHandler extends ChannelHandlerAdapter {
	private int counter;
	
	private byte[] req;
	
	public TimeClientHandler() {
		req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ByteBuf message = null;
		for(int i = 0; i < 100; i++) {
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.write(message);
		}
		ctx.flush();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String)msg;
		System.out.println("body: " + body + "; counter: " + ++counter);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
