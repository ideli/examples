package _aop.step04.introduction;

/**
 * 引介增强
 * 要给UserService增加的方法的接口定义,可控制是否打开消耗时间监控
 */
public interface Monitorable {

	void setMonitorActive(boolean active);
}
