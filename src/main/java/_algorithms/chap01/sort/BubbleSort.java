package _algorithms.chap01.sort;

import java.util.Random;

/**
 * 冒泡排序法
 * 时间复杂度O(N^2)
 * @author autfish
 *
 */
public class BubbleSort {
	public static void main(String[] args) {
		int length = 20;
		Random r = new Random();
		int[] numbers = new int[length];
		for(int i = 0; i < length; i++) {
			numbers[i] = r.nextInt(1000);
		}
		new BubbleSort().sort(true, numbers);
		new BubbleSort().sort(false, numbers);
	}
	
	public void sort(boolean descend, int... numbers) {
		System.out.print("before: ");
		for(int i = 0; i < numbers.length; i++) {
			System.out.print(numbers[i] + "  ");
		}
		System.out.println();
		
		//n个数执行n-1轮
		//每一轮后完成一个数字归位, 下一轮要比较的数字个数减1(所以内层循环是j < n - i)
		int n = numbers.length - 1;
		int t;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n - i; j++) {
				if(descend) { //从大到小
					if(numbers[j] < numbers[j + 1]) {
						t = numbers[j];
						numbers[j] = numbers[j + 1];
						numbers[j + 1] = t;
					}
				} else {
					if(numbers[j] > numbers[j + 1]) {
						t = numbers[j];
						numbers[j] = numbers[j + 1];
						numbers[j + 1] = t;
					}
				}
			}
		}
		System.out.print("after: ");
		for(int i = 0; i < numbers.length; i++) {
			System.out.print(numbers[i] + "  ");
		}
		System.out.println();
	}
}
