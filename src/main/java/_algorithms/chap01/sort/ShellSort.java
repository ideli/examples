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

    /**
     * 对数组分组并对每个组做直接插入排序, 完成后缩小组的数量, 重复插入排序, 直到缩小到一个组
     * 第一次分组数: section = n/2 == 0 ? n/2 : n/2+1, 分组规则: 每隔n/2挑一个数, 即[x]和[x+n/2]为一组
     */
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
        int[] numbers = new int[] { 4, 3, 6, 2, 7, 1, 5, 8 };
        System.out.print("排序前: ");
        for(int x = 0; x < numbers.length; x++) {
            System.out.print(numbers[x] + "  ");
        }
        System.out.println();
        ShellSort ss = new ShellSort(numbers);
        ss.sort();
    }
}
