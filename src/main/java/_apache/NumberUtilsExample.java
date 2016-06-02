package _apache;

import org.apache.commons.lang3.math.NumberUtils;

public class NumberUtilsExample {

	public static void main(String[] args) {
		int value = NumberUtils.toInt("100");
		System.out.println("NumberUtils.toInt(\"100\")=" + value);
	}

}
