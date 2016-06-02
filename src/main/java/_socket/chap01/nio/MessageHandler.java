package _socket.chap01.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import _socket.Message;

public class MessageHandler {
	
	private static HashMap<String, String> hmClientList = new HashMap<String, String>();
	
	public static void handleMessage(Message revMsg, SocketChannel sc) {
		try {
			revMsg.readHeader();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		switch(revMsg.getMessageNum())	{
		case 1:
			connect(revMsg, sc);
			break;
		case 2:
			sayHello(revMsg, sc);
			break;
		case 99:
			disconnect(revMsg, sc);
			break;
		default:
			System.out.println("未定义的消息号: " + revMsg.getMessageNum());
			break;
		}
	}
	
	private static void connect(Message revMsg, SocketChannel sc) {
		try {
			int serverId = revMsg.readInt();
			String strServerInfo = sc.getRemoteAddress().toString();
			
			if(serverId > 0 && !hmClientList.containsKey(serverId + "")) {
				System.out.println("服务器" + serverId + "注册成功");
				hmClientList.put(serverId + "", strServerInfo);
			}
		} catch (IOException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		
	}
	
	private static void disconnect(Message revMsg, SocketChannel sc) {
		try {
			int serverId = revMsg.readInt();
			if(serverId > 0 && hmClientList.containsKey(serverId + "")) {
				System.out.println("服务器" + serverId + "断开连接");
				hmClientList.remove(serverId + "");
				sc.close();
			}
		} catch (IOException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		
	}
	
	private static void sayHello(Message revMsg, SocketChannel sc) {
		try {
			String name = revMsg.readString();
			System.out.println("Receive sayHello: " + name);
			Message msgOut = new Message(2);
			msgOut.writeString("Hello, " + name);
			ByteBuffer buf = msgOut.toSend();
			sc.write(buf);
		} catch (IOException e) {
			e.printStackTrace();
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
