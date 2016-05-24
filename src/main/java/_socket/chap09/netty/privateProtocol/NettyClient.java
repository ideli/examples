package _socket.chap09.netty.privateProtocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import _socket.Message;

public class NettyClient implements Runnable {

	public static void main(String[] args) throws Exception {

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		NettyClient client = new NettyClient("127.0.0.1", 18080);
		executor.execute(client);

		BufferedReader console = new BufferedReader(new InputStreamReader(
				System.in));
		while (true) {
			String cmd;
			try {
				cmd = console.readLine();
				if (cmd.equalsIgnoreCase("exit")) {
					client.shutdown();
					System.exit(0);
				}
				Message msg = new Message(5);
				msg.writeString(cmd);
				client.send(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String host;
	private int port;
	private Channel channel;
	private EventLoopGroup group = new NioEventLoopGroup();

	public NettyClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void connect() throws Exception {
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(new MessageDecoder(1024 * 1024, 4, 4));
							ch.pipeline().addLast("MessageEncoder", new MessageEncoder());
							ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
							ch.pipeline().addLast("LoginAuthHandler", new LoginAuthReqHandler());
							ch.pipeline().addLast("HeartBeatHandler", new HeartBeatReqHandler());
							ch.pipeline().addLast(new MessageClientHandler());
						}
					});
			// 发起异步连接操作
			ChannelFuture future = b.connect(new InetSocketAddress(host, port))
					.sync();
			channel = future.channel();
			channel.closeFuture().sync();
		} finally {
			System.out.println("disconnected");
			// 所有资源释放完成之后，清空资源，再次发起重连操作
			Executors.newScheduledThreadPool(1).execute(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(1);
						try {
							connect();// 发起重连操作
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public void send(Message msg) {
		channel.writeAndFlush(msg);
	}

	public void shutdown() {
		group.shutdownGracefully();
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

class MessageClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object in)
			throws Exception {
		Message msg = (Message) in;
		System.out.println("msg: " + msg.getMessageNum());
		System.out.println("message: " + msg.readString());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
