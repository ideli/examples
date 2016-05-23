package _socket.chap06.netty.messagePack;

import java.util.List;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf msg,
			List<Object> objects) throws Exception {
		final int length = msg.readableBytes();
		final byte[] array = new byte[length];
		msg.getBytes(msg.readerIndex(), array, 0, length);
		MessagePack msgpack = new MessagePack();
		objects.add(msgpack.read(array, User.class));
	}

}
