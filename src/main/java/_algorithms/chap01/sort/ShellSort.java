package _algorithms.chap01.sort;

/**
 * 希尔排序法
 * Created by autfish on 2016/9/18.
 */
public class ShellSort {
    private int[] numbers;

    public ShellSort(int[] numbers) {
        this.numbers = numbers;
    }

    public void sort() {
        int section = this.numbers.length / 2;
        int j;
        int temp;
        while(section >= 1) {
            for(int i = section; i < this.numbers.length; i++) {
                temp = this.numbers[i];
                j = i - section;
                while(j >= 0 && this.numbers[j] > temp) {
                    this.numbers[j + section] = this.numbers[j];
                    j = j - section;
                }
                this.numbers[j + section] = temp;
            }
            section /= 2;
        }
        System.out.print("排序后: ");
        for(int x = 0; x < numbers.length; x++) {
            System.out.print(numbers[x] + "  ");
        }
    }

    public static void main(String[] args) {
        int[] numbers = new int[] { 4, 3, 6, 2, 7, 1, 5 };
        System.out.print("排序前: ");
        for(int x = 0; x < numbers.length; x++) {
            System.out.print(numbers[x] + "  ");
        }
        System.out.println();
        ShellSort ss = new ShellSort(numbers);
        ss.sort();
    }
}
