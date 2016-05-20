package _basic.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class LambdaExamples {

	public static void main(String[] args) {
		
		Runnable r = () -> System.out.println("Hello world!");
		r.run();
		
		BiFunction<String, String, Integer> f1 = (String first, String second) -> Integer.compare(first.length(), second.length());
		int r1 = f1.apply("aa", "b");
		System.out.println(r1);

		BiFunction<String, String, Integer> f2 = (String first, String second) -> {
			if(first.length() < second.length()) return -1;
			else if(first.length() > second.length()) return 1;
			return 0;
		};
		int r2 = f2.apply("a", "bb");
		System.out.println(r2);
		
		List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
		features.forEach(n -> System.out.println(n));
		Predicate<String> startWithL = (n) -> n.startsWith("L");
		features.stream().filter(startWithL).forEach(System.out::println);
		
		String[] words = new String[] { "Lambdas", "Default Method", "Stream API", "Date and Time API" };
		Arrays.sort(words, (a, b) -> a.compareTo(b) * -1 );
		Arrays.asList(words).forEach(System.out::println);
	}

}
