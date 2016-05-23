package _socket.chap06.netty.messagePack;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgpackEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext context, Object obj, ByteBuf msg)
			throws Exception {
		MessagePack msgpack = new MessagePack();
		byte[] raw = msgpack.write(obj);
		msg.writeBytes(raw);
	}

}
