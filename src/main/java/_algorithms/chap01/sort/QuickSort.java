package _algorithms.chap01.sort;

import java.util.Random;

/**
 * 快速排序法
 * 平均时间复杂度O(NlogN),即O(N*log2N)
 * (对数的定义: 如果a^x=N(a>0且a≠1),那么数x叫做以a为底N的对数,记作x=logaN,读作以a为底N的对数)
 * @author autfish
 *
 */
public class QuickSort {

    public static void main(String[] args) {
        int length = 20;
        Random r = new Random();
        int[] numbers = new int[length];
        for(int i = 0; i < length; i++) {
            numbers[i] = r.nextInt(1000);
        }

        System.out.print("before: ");
        for(int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + "  ");
        }
        System.out.println();
        new QuickSort().sort(false, 0, numbers.length - 1, numbers);

        System.out.print("after: ");
        for(int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + "  ");
        }
        System.out.println();
    }

    public void sort(boolean descend, int left, int right, int... numbers) {
        if(left >= right) {
            return;
        }
        int temp = numbers[left];
        int t = 0;
        int i = left;
        int j = right;
        while(i != j) {
            if(descend) {
                //先从右往左找
                while (numbers[j] <= temp && i < j)
                    j--;
                //再从左往右找
                while (numbers[i] >= temp && i < j)
                    i++;
            } else {
                //先从右往左找
                while (numbers[j] >= temp && i < j)
                    j--;
                //再从左往右找
                while (numbers[i] <= temp && i < j)
                    i++;
            }
            //交换两个数在数组中的位置
            if(i < j) {
                t = numbers[i];
                numbers[i] = numbers[j];
                numbers[j] = t;
            }
        }
        //将基准数归位
        numbers[left] = numbers[i];
        numbers[i] = temp;

        sort(descend, left, i - 1, numbers);
        sort(descend, i + 1, right, numbers);
    }
}
