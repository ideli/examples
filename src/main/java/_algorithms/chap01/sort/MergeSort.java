package _algorithms.chap01.sort;

import java.util.Arrays;

/**
 * 合并排序法
 * Created by autfish on 2016/9/20.
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] numbers = new int[] {4, 3, 6, 2, 7, 1, 5};
        System.out.println("排序前: " + Arrays.toString(numbers));

        MergeSort ms = new MergeSort();
        ms.sort(numbers, 0, numbers.length - 1);

        System.out.println("排序后: " + Arrays.toString(numbers));
    }

    public void sort(int[] numbers, int from, int to) {
        int middle = (from + to) / 2;
        if (from < to) {
            sort(numbers, from, middle);
            sort(numbers, middle + 1, to);
            //左侧数列最大值小于右侧数列最小值, 不需要通过合并来调整顺序
            if(numbers[middle] < numbers[middle + 1])
                return;
            merge(numbers, from, middle, to);
        }
    }

    private void merge(int[] numbers, int from, int middle, int to) {
        int[] temp = new int[to - from + 1];
        int left = from;
        int right = middle + 1;
        int i = 0;

        //从拆分到两边数列各剩一个数字开始合并; 当数列中有多个数字时, 一定是已经排好序的
        //从两边数列左侧开始依次取数对比, 挑选小的一个放入临时数组
        while (left <= middle && right <= to) {
            if (numbers[left] < numbers[right]) {
                temp[i++] = numbers[left++];
            } else {
                temp[i++] = numbers[right++];
            }
        }

        //把左边数列剩余的数移入数组
        while (left <= middle) {
            temp[i++] = numbers[left++];
        }

        //把右边数列剩余的数移入数组
        while (right <= to) {
            temp[i++] = numbers[right++];
        }

        System.arraycopy(temp, 0, numbers, from, temp.length);
    }
}
