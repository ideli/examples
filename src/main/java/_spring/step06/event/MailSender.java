package _spring.step06.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MailSender implements ApplicationContextAware {

	private ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}

	public void sendMail(String to) {
		System.out.println("MailSender:模拟发送邮件...");
		MailSendEvent event = new MailSendEvent(this.ctx, to);
		ctx.publishEvent(event);
	}
}
