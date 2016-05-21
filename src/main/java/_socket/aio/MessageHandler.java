package _socket.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import _socket.Message;

public class MessageHandler {
	
	private static HashMap<String, String> hmClientList = new HashMap<String, String>();
	
	public static void handleMessage(Message revMsg, AsynchronousSocketChannel attachment) {
		try {
			revMsg.readHeader();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		switch(revMsg.getMessageNum())	{
		case 1:
			connect(revMsg, attachment);
			break;
		case 2:
			sayHello(revMsg, attachment);
			break;
		case 99:
			disconnect(revMsg, attachment);
			break;
		default:
			System.out.println("未定义的消息号: " + revMsg.getMessageNum());
			break;
		}
	}
	
	private static void connect(Message revMsg, AsynchronousSocketChannel attachment) {
		try {
			int serverId = revMsg.readInt();
			String strServerInfo = attachment.getRemoteAddress().toString();
			
			if(serverId > 0 && !hmClientList.containsKey(serverId + "")) {
				System.out.println("服务器" + serverId + "注册成功");
				hmClientList.put(serverId + "", strServerInfo);
			}
		} catch (IOException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		
	}
	
	private static void disconnect(Message revMsg, AsynchronousSocketChannel attachment) {
		try {
			int serverId = revMsg.readInt();
			if(serverId > 0 && hmClientList.containsKey(serverId + "")) {
				System.out.println("服务器" + serverId + "断开连接");
				hmClientList.remove(serverId + "");
				attachment.close();
			}
		} catch (IOException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		
	}
	
	private static void sayHello(Message revMsg, AsynchronousSocketChannel attachment) {
		try {
			String name = revMsg.readString();
			System.out.println("Receive sayHello: " + name);
			Message msgOut = new Message(2);
			msgOut.writeString("Hello, " + name);
			ByteBuffer buf = msgOut.toSend();
			attachment.write(buf, attachment, new WriteHandler(buf));
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeServer(SocketChannel sc) {
		try {
			String serverInfo = sc.getRemoteAddress().toString();
			Iterator<Entry<String, String>> iter = hmClientList.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				if(val.equals(serverInfo)) {
					hmClientList.remove(key);
					System.out.println("服务器" + key + "断开连接");
					break;
				}
			}
		} catch (IOException e) {
		}
	}
}

/**
 * Write响应完请求的回调
 */
class WriteHandler implements
		CompletionHandler<Integer, AsynchronousSocketChannel> {
	private ByteBuffer buffer;

	public WriteHandler(ByteBuffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void completed(Integer result,
			AsynchronousSocketChannel attachment) {
		if(buffer.hasRemaining()) {
			attachment.write(buffer, attachment, this);
		}
	}

	@Override
	public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
		exc.printStackTrace();
		try {
			attachment.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
