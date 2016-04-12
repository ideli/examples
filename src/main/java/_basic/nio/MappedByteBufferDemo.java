package _basic.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;

/*
 * 内存映射文件演示
 */
public class MappedByteBufferDemo {

	@SuppressWarnings("resource")
	public void show() {
		int length = 0x8FFFFFF;
		try {
			MappedByteBuffer out = new RandomAccessFile("d:\\data.txt", "rw").getChannel().map(MapMode.READ_WRITE, 0, length);
			for(int i = 0; i < length; i++) {
				out.put((byte)'x');
			}
			System.out.println("Finished writing");
			for(int i = length / 2; i < length / 2 + 6; i++) {
				System.out.print((char)out.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MappedByteBufferDemo().show();
	}
}
