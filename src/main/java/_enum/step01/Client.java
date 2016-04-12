package _enum.step01;

public class Client {

	public void test() {
		for(Shrubbery s : Shrubbery.values()) {
			System.out.println(s + ".ordinal(): " + s.ordinal());
			System.out.println(s + ".compareTo(Shrubbery.CRAWLING): " + s.compareTo(Shrubbery.CRAWLING));
			System.out.println(s + ".equals(Shrubbery.CRAWLING): " + s.equals(Shrubbery.CRAWLING));
			System.out.println(s + " == Shrubbery.CRAWLING: " + (s == Shrubbery.CRAWLING));
			System.out.println(s + ".getDeclaringClass(): " + s.getDeclaringClass());
			System.out.println(s + ".name(): " + s.name());
			System.out.println("----------------------------------------------");
		}
		for(String s : "HANGING CRAWLING GROUND".split(" ")) {
			System.out.println(Enum.valueOf(Shrubbery.class, s));
		}
	}
}
