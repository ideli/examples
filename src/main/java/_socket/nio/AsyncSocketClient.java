package _socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class AsyncSocketClient implements Runnable {

	private SocketChannel socketChannel;
	private boolean bConnected = false;
	private String strIp;
	private int iPort;

	public AsyncSocketClient(String strIp, int iPort) {
		this.strIp = strIp;
		this.iPort = iPort;
	}

	public void Send(String strMsg) {
		if(!bConnected) {
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
		InetSocketAddress inetSocketAddress = new InetSocketAddress(strIp,
				iPort);
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(inetSocketAddress);
			Selector sSelector = Selector.open();
			socketChannel.register(sSelector, SelectionKey.OP_CONNECT);

			while (true) {
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
								bConnected = true;
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
