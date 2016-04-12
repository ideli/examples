package _basic.grammar;

public class TryWithResourcesDemo {

	public void show() {
		try(AutoCloseable wrapper = new AutoCloseable() {

			@Override
			public void close() throws Exception {
				System.out.println(this + " closed");
			}
		}) {
			System.out.println(wrapper.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new TryWithResourcesDemo().show();
	}
}
