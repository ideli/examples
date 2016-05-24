package _socket.chap09.netty.privateProtocol;

import _socket.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class HeartBeatRespHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Message message = (Message) msg;
		// 返回心跳应答消息
		if (message.getMessageNum() == 3) {
			System.out.println("Receive client heart beat message : ---> " + message);
			Message heartBeat = new Message(4);
			System.out.println("Send heart beat response message to client : ---> " + heartBeat);
			ctx.writeAndFlush(heartBeat);
		} else
			ctx.fireChannelRead(msg);
	}
}
