package _socket.chap01.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import _socket.Message;

public class SocketClient implements Runnable {

	public static void main(String[] args) {
		SocketClient client = new SocketClient("127.0.0.1", 18080);
		Thread thread = new Thread(client);
		thread.start();
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String cmd;
			try {
				cmd = console.readLine();
				if(cmd.equalsIgnoreCase("exit")) {
					client.stop();
					System.exit(0);
				}
				client.send(cmd);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	private SocketChannel socketChannel;
	private boolean connected = false;
	private String ip;
	private int port;
	private volatile boolean stop = false;

	public SocketClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public void stop() {
		this.stop = true;
	}

	public void send(String strMsg) {
		if(!connected) {
			System.out.println("Socket not ready");
			return;
		}
		Message msg = new Message(2);
		msg.writeString(strMsg);
		ByteBuffer bytes = msg.toSend();
		try {
			socketChannel.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		InetSocketAddress inetSocketAddress = new InetSocketAddress(ip,
				port);
		Selector sSelector = null;
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(inetSocketAddress);
			sSelector = Selector.open();
			socketChannel.register(sSelector, SelectionKey.OP_CONNECT);

			while (!stop) {
				//将会阻塞执行，直到有事件发生
				sSelector.select();
				Iterator<SelectionKey> it = sSelector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					//处理结束后移除当前事件，以免重复处理
					it.remove();
					//key定义了四种不同形式的操作
					if (key.isConnectable()) {
						if (socketChannel.isConnectionPending()) {
							if (socketChannel.finishConnect()) {
								//只有当连接成功后才能注册OP_READ事件
								key.interestOps(SelectionKey.OP_READ);
								connected = true;
							} else {
								key.cancel();
							}
						}
					} else if (key.isReadable()) {
						onRead(key);
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if(sSelector != null) {
			try {
				sSelector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//处理客户端发来的消息，处理读事件
	private void onRead(SelectionKey key) {
		SocketChannel sc = (SocketChannel) key.channel();
		ByteBuffer inBuf = ByteBuffer.allocate(1024);
		inBuf.clear();

		try {
			int iReaded = sc.read(inBuf);
			System.out.println("Read " + iReaded + " bytes");
			if (iReaded < 0) {
				MessageHandler.removeServer(sc);
				sc.close();
				return;
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
		inBuf.flip();
		Message revMsg = new Message(inBuf);
		revMsg.readHeader();
		String strName = revMsg.readString();
		System.out.println("Receive: " + strName);
	}
}
