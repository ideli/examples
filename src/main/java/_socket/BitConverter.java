package _socket;

/*
 * 数据类型和byte数组的转换
 * 
 * 注意:
 * 网络字节序 Big Endian 例如int=2存储为 0 0 0 2
 * 主机字节序 大部分为Littile Endian 例如int=2存储为2 0 0 0
 * JAVA默认Big Endian
 */
public class BitConverter {
	
	public static byte[] intToBytes(final int integer) {
		return intToBigEndianBytes(integer);
	}
	
	public static byte[] shortToBytes(final short s) {
		return shortToBigEndianBytes(s);
	}
	
	public static short bytesToShort(final byte[] b) {
		return bigEndianBytesToShort(b);
	}
	
	public static int bytesToInt(final byte[] b) {
		return bigEndianBytesToInt(b);
	}
	
	/*
	 * 把int转换为4字节的Big Endian数组
	 */
	public static byte[] intToBigEndianBytes(final int integer) {
		byte[] targets = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((integer >>> offset) & 0xff);
		}
		return targets;
	}
	
	/*
	 * 把int转换为4字节的Little Endian数组
	 */
	public static byte[] intToLittleEndianBytes(final int integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer
				: integer)) / 8;
		byte[] targets = new byte[4];

		for (int n = 0; n < byteNum; n++)
			targets[n] = (byte) (integer >>> (n * 8));

		return targets;
	}
	
	/*
	 * 把short转换为2字节的Big Endian数组
	 */
	public static byte[] shortToBigEndianBytes(final short s) {
		byte[] targets = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}
	
	/*
	 * 把short转换为2字节的Little Endian数组
	 */
	public static byte[] shortToLittleEndianBytes(final short integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer
				: integer)) / 8;
		byte[] targets = new byte[2];

		for (int n = 0; n < byteNum; n++)
			targets[n] = (byte) (integer >>> (n * 8));

		return targets;
	}

	/*
	 * 把2字节Little Endian数组转换为short
	 */
	public static short littleEndianBytesToShort(final byte[] b) {
		int targets = (b[0] & 0xff) | ((b[1] << 8) & 0xff00);
		return (short) targets;
	}

	/*
	 * 从Little Endian数组的offset开始读两字节转换为short
	 */
	public static short littleEndianBytesToShort(final byte[] b, int offset) {
		int targets = (b[offset] & 0xff) | ((b[offset + 1] << 8) & 0xff00);
		return (short) targets;
	}

	/*
	 * 把4字节Little Endian数组转换为int
	 */
	public static int littleEndianBytesToInt(final byte[] b) {
		int targets = 0;
		for (int i = 0; i < 4; i++) {
			targets += (b[i] & 0xFF) << (8 * i);
		}
		return targets;
	}
	
	/*
	 * 从Little Endian数组的offset开始读4字节转换为int
	 */
	public static int littleEndianBytesToInt(final byte[] b, int offset) {
		int targets = 0;
		for (int i = 0; i < 4; i++) {
			targets += (b[i + offset] & 0xFF) << (8 * i);
		}
		return targets;
	}

	/*
	 * 把2字节Big Endian数组转换为short
	 */
	public static short bigEndianBytesToShort(final byte[] b) {
		int s = 0;
		if (b[0] >= 0) {
			s = s + b[0];
		} else {
			s = s + 256 + b[0];
		}
		s = s * 256;
		if (b[1] >= 0) {
			s = s + b[1];
		} else {
			s = s + 256 + b[1];
		}
		return (short) s;
	}
	
	/*
	 * 从Big Endian数组的offset开始读两字节转换为short
	 */
	public static short bigEndianBytesToShort(final byte[] b, int offset) {
		int s = 0;
		if (b[offset] >= 0) {
			s = s + b[offset];
		} else {
			s = s + 256 + b[offset];
		}
		s = s * 256;
		if (b[offset + 1] >= 0) {
			s = s + b[offset + 1];
		} else {
			s = s + 256 + b[offset + 1];
		}
		return (short) s;
	}

	/*
	 * 把4字节Big Endian数组转换为int
	 */
	public static int bigEndianBytesToInt(final byte[] b) {
		int s = 0;
		for (int i = 0; i < 3; i++) {
			if (b[i] >= 0) {
				s = s + b[i];
			} else {
				s = s + 256 + b[i];
			}
			s = s * 256;
		}
		if (b[3] >= 0) {
			s = s + b[3];
		} else {
			s = s + 256 + b[3];
		}
		return s;
	}

	/*
	 * 从Big Endian数组的offset开始读4字节转换为int
	 */
	public static int bigEndianBytesToInt(final byte[] b, int offset) {
		int s = 0;
		for (int i = 0; i < 3; i++) {
			if (b[i + offset] >= 0) {
				s = s + b[i + offset];
			} else {
				s = s + 256 + b[i + offset];
			}
			s = s * 256;
		}
		if (b[3 + offset] >= 0) {
			s = s + b[3 + offset];
		} else {
			s = s + 256 + b[3 + offset];
		}
		return s;
	}
}
