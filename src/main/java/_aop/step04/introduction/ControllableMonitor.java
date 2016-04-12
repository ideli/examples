package _aop.step04.introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * 引介增强 - 增加方法后的代理类
 */
public class ControllableMonitor extends DelegatingIntroductionInterceptor
	implements Monitorable {

	private ThreadLocal<Boolean> MonitorStatusMap = new ThreadLocal<>();
	
	@Override
	public void setMonitorActive(boolean active) {
		MonitorStatusMap.set(active);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object obj = null;
		if(MonitorStatusMap.get() != null && MonitorStatusMap.get()) {
			long start = System.currentTimeMillis();
			obj = super.invoke(invocation);
			long end = System.currentTimeMillis();
			System.out.println(invocation.getClass().getName() + "." + invocation.getMethod().getName() + "消耗 " + (end - start));
		} else {
			obj = super.invoke(invocation);
		}
		return obj;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6720872762725475674L;
}
