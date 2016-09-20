package _algorithms.chap01.sort;

import java.util.Arrays;

/**
 * 合并排序法
 * Created by autfish on 2016/9/20.
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] numbers = new int[]{4, 3, 6, 2, 7, 1, 5};
        System.out.println("排序前: " + Arrays.toString(numbers));

        MergeSort ms = new MergeSort();
        ms.sort(numbers, 0, numbers.length - 1);

        System.out.println("排序后: " + Arrays.toString(numbers));
    }

    public void sort(int[] numbers, int left, int right) {
        int middle = (left + right) / 2;
        if (left < right) {
            sort(numbers, left, middle);
            sort(numbers, middle + 1, right);
            merge(numbers, left, middle, right);
        }
    }

    private void merge(int[] numbers, int left, int middle, int right) {
        int[] temp = new int[right - left + 1];
        int lpointer = left;
        int rpointer = middle + 1;
        int i = 0;

        //从拆分到两边数列各剩一个数字开始合并; 当数列中有多个数字时, 一定是已经排好序的
        //从两边数列左侧开始依次取数对比, 挑选小的一个放入临时数组
        while (lpointer <= middle && rpointer <= right) {
            if (numbers[lpointer] < numbers[rpointer]) {
                temp[i++] = numbers[lpointer++];
            } else {
                temp[i++] = numbers[rpointer++];
            }
        }

        //把左边数列剩余的数移入数组
        while (lpointer <= middle) {
            temp[i++] = numbers[lpointer++];
        }

        //把右边数列剩余的数移入数组
        while (rpointer <= right) {
            temp[i++] = numbers[rpointer++];
        }

        System.arraycopy(temp, 0, numbers, left, temp.length);
    }
}
