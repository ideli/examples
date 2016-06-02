package _apache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class ArrayUtilsExample {

	public static void main(String[] args) {
		List<Integer> intList = new ArrayList<>();
		intList.add(1);
		intList.add(2);
		int[] values = ArrayUtils.toPrimitive(intList.toArray(new Integer[0]));
		System.out.println(Arrays.toString(values));
	}
}
