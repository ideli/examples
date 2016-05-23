package _socket.chap07.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {

	public static void main(String[] args) throws Exception {
		int port = 18080;
		String url = DEFAULT_URL;
		if (args.length > 1)
			url = args[1];
		new HttpFileServer().run("127.0.0.1", port, url);
	}
	
	private static final String DEFAULT_URL = "/src/";

	public void run(final String host, final int port, final String url) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast("http-decoder",
									new HttpRequestDecoder());
							ch.pipeline().addLast("http-aggregator",
									new HttpObjectAggregator(65536));
							ch.pipeline().addLast("http-encoder",
									new HttpResponseEncoder());
							ch.pipeline().addLast("http-chunked",
									new ChunkedWriteHandler());
							ch.pipeline().addLast("fileServerHandler",
									new HttpFileServerHandler(url));
						}
					});
			ChannelFuture future = b.bind(host, port).sync();
			System.out.println("HttpServer Started, visit: " + "http://" + host + ":" + port + url);
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
