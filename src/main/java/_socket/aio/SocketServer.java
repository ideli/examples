package _socket.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import _socket.Message;


public class SocketServer {

	static int PORT = 18080;
	static int BUFFER_SIZE = 1024;
	static String CHARSET = "utf-8"; // 默认编码
	static CharsetDecoder decoder = Charset.forName(CHARSET).newDecoder(); // 解码

	int port;
	// ByteBuffer buffer;
	AsynchronousServerSocketChannel serverChannel;

	public SocketServer(int port) throws IOException {
		this.port = port;
		// this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
		this.decoder = Charset.forName(CHARSET).newDecoder();
	}

	private void listen() throws Exception {

		// 打开一个服务通道
		// 绑定服务端口
		this.serverChannel = AsynchronousServerSocketChannel.open().bind(
				new InetSocketAddress(port), 100);
		this.serverChannel.accept(this, new AcceptHandler());
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
		System.out.println("运行中...");
	}

	/**
	 * accept到一个请求时的回调
	 */
	private class AcceptHandler implements
			CompletionHandler<AsynchronousSocketChannel, SocketServer> {
		@Override
		public void completed(final AsynchronousSocketChannel client,
				SocketServer attachment) {
			try {
				// tcp各项参数
				client.setOption(StandardSocketOptions.TCP_NODELAY, true);
				client.setOption(StandardSocketOptions.SO_SNDBUF, 1024);
				client.setOption(StandardSocketOptions.SO_RCVBUF, 1024);

				if (client.isOpen()) {
					System.out.println(client.getRemoteAddress() + " connected");
					final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
					buffer.clear();
					client.read(buffer, client, new ReadHandler(buffer));
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				attachment.serverChannel.accept(attachment, this);// 监听新的请求，递归调用。
			}
		}

		@Override
		public void failed(Throwable exc, SocketServer attachment) {
			try {
				exc.printStackTrace();
			} finally {
				attachment.serverChannel.accept(attachment, this);// 监听新的请求，递归调用。
			}
		}
	}

	/**
	 * Read到请求数据的回调
	 */
	private class ReadHandler implements
			CompletionHandler<Integer, AsynchronousSocketChannel> {

		private ByteBuffer inBuf;

		public ReadHandler(ByteBuffer buffer) {
			this.inBuf = buffer;
		}

		@Override
		public void completed(Integer result,
				AsynchronousSocketChannel attachment) {
			try {
				if (result < 0) {// 客户端关闭了连接
					SocketServer.close(attachment);
				} else if (result == 0) {
					System.out.println("空数据"); // 处理空数据
				} else {
					// 读取请求，处理客户端发送的数据
					inBuf.flip();
					Message revMsg = new Message(inBuf);
					MessageHandler.handleMessage(revMsg, attachment);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}  finally {
				inBuf.clear();
				attachment.read(inBuf, attachment, this);
			}
		}

		@Override
		public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
			exc.printStackTrace();
			SocketServer.close(attachment);
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println("正在启动服务...");
			SocketServer server = new SocketServer(PORT);
			server.listen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void close(AsynchronousSocketChannel client) {
		try {
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
