package _basic.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class ChannelDemo {

	@SuppressWarnings("resource")
	public void showFileChannel() {
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < 300; i++) {
			builder.append("test ");
		}
		String test = builder.toString();
		
		FileChannel fc = null;
		try {
			fc = new FileOutputStream("d:\\data.txt").getChannel();
			fc.write(ByteBuffer.wrap(test.getBytes(Charset.forName("UTF-8"))));
			fc.close();
			fc = new RandomAccessFile("d:\\data.txt", "rw").getChannel();
			//移动到最后
			fc.position(fc.size());
			fc.write(ByteBuffer.wrap(test.getBytes(Charset.forName("UTF-8"))));
			fc.close();
			
			fc = new FileInputStream("d:\\data.txt").getChannel();
			ByteBuffer buff = ByteBuffer.allocate(1024);
			fc.read(buff);
			//读取ByteBuffer内容前必须执行
			buff.flip();
			while(buff.hasRemaining()) {
				System.out.println((char)buff.get());
			}
			
			//回复初始状态
			buff.clear();
			while(fc.read(buff) != -1) {
				buff.flip();
				System.out.println(new String(buff.array()));
				buff.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fc != null) {
				try {
					fc.close();
				} catch (IOException e) {}
			}
		}
	}
	
	public static void main(String[] args) {
		new ChannelDemo().showFileChannel();
	}
}
