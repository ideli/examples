package _socket.chap09.netty.privateProtocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import _socket.Message;

public class HeartBeatReqHandler extends ChannelHandlerAdapter {

	private volatile ScheduledFuture<?> heartBeat;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message message = (Message) msg;
		// 握手成功，主动发送心跳消息
		if (message.getMessageNum() == 2) {
			System.out.println("Client begin to send heart beat message : ---> " + message);
			heartBeat = ctx.executor().scheduleAtFixedRate(
					new HeartBeatReqHandler.HeartBeatTask(ctx), 0, 5000,
					TimeUnit.MILLISECONDS);
		} else if (message.getMessageNum() == 4) {
			System.out.println("Client receive server heart beat message : ---> " + message);
		} else
			ctx.fireChannelRead(msg);
	}

	private class HeartBeatTask implements Runnable {
		private final ChannelHandlerContext ctx;

		public HeartBeatTask(final ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void run() {
			Message heatBeat = new Message(3);
			System.out.println("Client send heart beat messsage to server : ---> " + heatBeat);
			ctx.writeAndFlush(heatBeat);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		if (heartBeat != null) {
			heartBeat.cancel(true);
			heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
	}
}
