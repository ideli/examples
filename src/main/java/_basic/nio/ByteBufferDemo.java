package _basic.nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/*
 * ByteBuffer
 * position - 当前读取的位置
 * mark - 标记,可以通过reset()设置position到mark
 * limit - 界限,当前数据的最大索引
 * capacity - 容量
 */
public class ByteBufferDemo {

	public void showMethod() {
		ByteBuffer buff = ByteBuffer.allocate(1024);
		
		//capacity() 返回缓冲区容量
		System.out.println("capacity()=" + buff.capacity());
		
		//clear() 清空缓冲区,position设置为0,limit设置为容量
		buff.clear();
		
		//flip() limit设置为position,position设置为0,准备读取已经写入的数据
		buff.flip();
		
		//limit() 返回limit值
		System.out.println("limit()=" + buff.limit());
		
		//limit(int lim) 设置limit值
		System.out.println("buff.limit(100)=" + buff.limit(100));
		
		//mark() mark设置为position
		buff.mark();
		
		//reset() 设置position为上次mark的索引
		buff.reset();
		
		//rewind() position设置为0,删除mark
		buff.rewind();
		
		//position() 返回position值
		System.out.println("position()=" + buff.position());
		
		//position(int pos) 设置position值
		buff.position(100);
		
		//remaining() 返回(limit - position)
		System.out.println("remaining()=" + buff.remaining());
		
		//hasRemaining() 若有介于position和limit之间的数据返回true
		System.out.println("hasRemaining()=" + buff.hasRemaining());
	}
	
	public void showEndian() {
		ByteBuffer buff = ByteBuffer.allocate(4);
		buff.asIntBuffer().put(2);
		System.out.println(Arrays.toString(buff.array()));
		buff.clear();
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.asIntBuffer().put(2);
		System.out.println(Arrays.toString(buff.array()));
		
		buff = ByteBuffer.allocate(8);
		buff.asLongBuffer().put(9999999999L);
		System.out.println(buff.asLongBuffer().get());
		System.out.println(Arrays.toString(buff.array()));
		buff.clear();
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.asLongBuffer().put(9999999999L);
		System.out.println(buff.asLongBuffer().get());
		System.out.println(Arrays.toString(buff.array()));
	}
	
	public static void main(String[] args) {
		new ByteBufferDemo().showMethod();
		System.out.println("----------------------------");
		new ByteBufferDemo().showEndian();
	}
}
