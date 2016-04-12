package _thread.step11._volatile;

public class VariableTest {

	public static void main(String[] args) {
		Thread t1 = new Thread() {
			public void run() {
				while(true) {
					Variable2.one();
				}
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				while(true) {
					Variable2.two();
				}
			}
		};
		t1.start();
		t2.start();
	}
}

class Variable1 {
	static int i = 0, j = 0;
	static void one() { i++; j++; }
	static void two() {
		if(i != j)
			System.out.printf("i-j=%d%n", i - j);
	}
}

/*
 * volatile 对于定义为volatile的变量,线程不会将其从main memory复制到work memory,用于控制线程中对象的可见性
 */
class Variable2 {
	volatile static int i = 0, j = 0;
	static void one() { i++; j++; }
	static void two() {
		if(i != j)
			System.out.printf("i-j=%d%n", i - j);
	}
}