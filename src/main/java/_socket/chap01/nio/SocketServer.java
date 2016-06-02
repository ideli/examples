package _socket.chap01.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import _socket.Message;

public class SocketServer implements Runnable {
	
	public static void main(String[] args) {
		new Thread(new SocketServer("127.0.0.1", 18080)).start();

	}
	
	//IP
	private String ip;
	//要监听的端口号
	private int port;
	//信号监视器
	private Selector sSelector;
	
	private volatile boolean stop = false;

	public SocketServer(String ip, int port) {
		this.ip = ip;
		this.port = port;

		try {
			sSelector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		try {
			//生成一个ServerScoket通道的实例对象，用于侦听可能发生的IO事件
			ServerSocketChannel ssc = ServerSocketChannel.open();
			//将该通道设置为异步方式
			ssc.configureBlocking(false);
			//绑定到一个指定的端口
			ssc.socket().bind(new InetSocketAddress(ip, port));
			//注册特定类型的事件到信号监视器上
			ssc.register(sSelector, SelectionKey.OP_ACCEPT);
			System.out.println("Server listen at " + ip + ":" + port);

			while (!stop) {
				//将会阻塞执行，直到有事件发生
				sSelector.select();
				Iterator<SelectionKey> it = sSelector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					//key定义了四种不同形式的操作
					switch (key.readyOps()) {
					case SelectionKey.OP_ACCEPT:
						onAccept(key);
						break;
					case SelectionKey.OP_CONNECT:
						break;
					case SelectionKey.OP_READ:
						onRead(key);
						break;
					case SelectionKey.OP_WRITE:
						break;
					}
					//处理结束后移除当前事件，以免重复处理
					it.remove();
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(sSelector != null) {
			try {
				sSelector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//处理接收连接的事件
	private void onAccept(SelectionKey key) {
		try {
			System.out.println("新的客户端请求连接...");
			ServerSocketChannel server = (ServerSocketChannel) key.channel();
			SocketChannel sc = server.accept();
			sc.configureBlocking(false);
			//注册读事件
			sc.register(sSelector, SelectionKey.OP_READ);
			System.out.println("客户端连接成功...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//处理接收连接的事件
	private void onRead(SelectionKey key) {
		SocketChannel sc = (SocketChannel) key.channel();
		ByteBuffer inBuf = ByteBuffer.allocate(1024);

		try {
			int readed = sc.read(inBuf);
			System.out.println("Read " + readed + " bytes");
			if (readed < 0) {
				MessageHandler.removeServer(sc);
				sc.close();
				return;
			} else if (readed > 0) {
				inBuf.flip();
				Message revMsg = new Message(inBuf);
				MessageHandler.handleMessage(revMsg, sc);
			}
		} catch (IOException e) {
			e.printStackTrace();
			key.cancel();
			try {
				MessageHandler.removeServer(sc);
				sc.close();
			} catch (IOException ex) {
			}
			return;
		}
	}
}
