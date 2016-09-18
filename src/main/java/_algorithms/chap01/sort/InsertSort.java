package _algorithms.chap01.sort;

/**
 * Created by Administrator on 2016/9/18.
 */
public class InsertSort {
    private int[] numbers;

    public InsertSort(int[] numbers) {
        this.numbers = numbers;
    }

    public void sort() {
        int temp;
        for(int i = 1; i < this.numbers.length; i++) {
            temp = this.numbers[i]; //取出一个未排序的数
            for(int j = i - 1; j >= 0 && temp < this.numbers[j]; j--) {
                this.numbers[j + 1] = this.numbers[j];
                this.numbers[j] = temp;
            }
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
        InsertSort is = new InsertSort(numbers);
        is.sort();
    }
}
