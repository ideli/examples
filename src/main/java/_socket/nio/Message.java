package _socket.nio;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import _socket.BitConverter;

public class Message {
	
	public Message(int iMessageNum) {
		this.iMessageNum = iMessageNum;
		body = new byte[1024];
	}
	
	public Message(ByteBuffer buff) {
		this.body = buff.array();
	}
	
	public void readHeader() throws ArrayIndexOutOfBoundsException {
		if(body.length >= 4) {
			iMessageNum = BitConverter.bigEndianBytesToInt(body);
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
		if(body.length >= 8) {
			iMessageLen = BitConverter.bigEndianBytesToInt(body, 4);
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public int readInt() throws ArrayIndexOutOfBoundsException {
		if(body.length >= iPosition + 4) {
			int iValue = BitConverter.bigEndianBytesToInt(body, iPosition);
			iPosition += 4;
			return iValue;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public void writeInt(int iValue)  throws ArrayIndexOutOfBoundsException {
		if(body.length >= iPosition + 4) {
			byte[] temp = BitConverter.intToBigEndianBytes(iValue);
			System.arraycopy(temp, 0, body, iPosition, 4);
			iPosition += 4;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public String readString() throws ArrayIndexOutOfBoundsException {
		if(body.length >= iPosition + 4) {
			int iLength = BitConverter.bigEndianBytesToInt(body, iPosition);
			if(body.length >= iPosition + 4 + iLength) {
				byte[] temp = new byte[iLength];
				System.arraycopy(body, iPosition + 4, temp, 0, iLength);
				String strValue = "";
				try {
					strValue = new String(temp, "UTF-8");
				} catch (UnsupportedEncodingException e) {
				}
				iPosition += (4 + iLength);
				return strValue;
			} else {
				throw new ArrayIndexOutOfBoundsException();
			}
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public void writeString(String strValue)  throws ArrayIndexOutOfBoundsException {
		try {
			byte[] buf = strValue.getBytes("UTF-8");
			int iLength = buf.length;
			if(body.length >= iPosition + 4 + iLength) {
				byte[] temp = BitConverter.intToBigEndianBytes(iLength);
				System.arraycopy(temp, 0, body, iPosition, 4);
				System.arraycopy(buf, 0, body, iPosition + 4, iLength);
				iPosition += (4 + iLength);
			} else {
				throw new ArrayIndexOutOfBoundsException();
			}
			
		} catch (UnsupportedEncodingException e) {
			
		}
	}
	
	public ByteBuffer toSend() {
		byte[] temp = BitConverter.intToBigEndianBytes(iMessageNum);
		System.arraycopy(temp, 0, body, 0, 4);
		iMessageLen = iPosition;
		temp = BitConverter.intToBigEndianBytes(iMessageLen);
		System.arraycopy(temp, 0, body, 4, 4);
		
		temp = new byte[8 + iMessageLen];
		System.arraycopy(body, 0, temp, 0, 8 + iMessageLen);
		return ByteBuffer.wrap(temp);
	}
	
	public int getMessageNum() {
		return iMessageNum;
	}
	
	public void setMessageNum(int iMessageNum) {
		this.iMessageNum = iMessageNum;
	}
	
	public int getMessageLen() {
		return iMessageLen;
	}
	
	public void setMessageLen(int iMessageLen) {
		this.iMessageLen = iMessageLen;
	}
	
	private byte[] body;
	private int iMessageNum = 0;
	private int iMessageLen = 0;
	private int iPosition = 8;
}
