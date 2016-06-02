package _socket;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Message {
	
	public Message(int messageNum) {
		this.messageNum = messageNum;
		body = new byte[1024];
	}
	
	public Message(ByteBuffer buff) {
		this.body = buff.array();
	}
	
	public Message(byte[] body) {
		this.body = body;
	}
	
	public void readHeader() throws ArrayIndexOutOfBoundsException {
		if(body.length >= 4) {
			messageNum = BitConverter.bigEndianBytesToInt(body);
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
		if(body.length >= 8) {
			messageLen = BitConverter.bigEndianBytesToInt(body, 4);
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public int readInt() throws ArrayIndexOutOfBoundsException {
		if(body.length >= position + 4) {
			int iValue = BitConverter.bigEndianBytesToInt(body, position);
			position += 4;
			return iValue;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public void writeInt(int iValue)  throws ArrayIndexOutOfBoundsException {
		if(body.length >= position + 4) {
			byte[] temp = BitConverter.intToBigEndianBytes(iValue);
			System.arraycopy(temp, 0, body, position, 4);
			position += 4;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public String readString() throws ArrayIndexOutOfBoundsException {
		if(body.length >= position + 4) {
			int iLength = BitConverter.bigEndianBytesToInt(body, position);
			if(body.length >= position + 4 + iLength) {
				byte[] temp = new byte[iLength];
				System.arraycopy(body, position + 4, temp, 0, iLength);
				String strValue = "";
				try {
					strValue = new String(temp, "UTF-8");
				} catch (UnsupportedEncodingException e) {
				}
				position += (4 + iLength);
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
			if(body.length >= position + 4 + iLength) {
				byte[] temp = BitConverter.intToBigEndianBytes(iLength);
				System.arraycopy(temp, 0, body, position, 4);
				System.arraycopy(buf, 0, body, position + 4, iLength);
				position += (4 + iLength);
			} else {
				throw new ArrayIndexOutOfBoundsException();
			}
			
		} catch (UnsupportedEncodingException e) {
			
		}
	}
	
	public ByteBuffer toSend() {
		byte[] temp = BitConverter.intToBigEndianBytes(messageNum);
		System.arraycopy(temp, 0, body, 0, 4);
		messageLen = position;
		temp = BitConverter.intToBigEndianBytes(messageLen);
		System.arraycopy(temp, 0, body, 4, 4);
		
		temp = new byte[8 + messageLen];
		System.arraycopy(body, 0, temp, 0, 8 + messageLen);
		return ByteBuffer.wrap(temp);
	}
	
	public int getMessageNum() {
		return messageNum;
	}
	
	public void setMessageNum(int messageNum) {
		this.messageNum = messageNum;
	}
	
	public int getMessageLen() {
		return messageLen;
	}
	
	public void setMessageLen(int messageLen) {
		this.messageLen = messageLen;
	}
	
	private byte[] body;
	private int messageNum = 0;
	private int messageLen = 0;
	private int position = 8;
}
