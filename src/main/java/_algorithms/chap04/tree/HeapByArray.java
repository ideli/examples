package _algorithms.chap04.tree;

import java.util.Random;

/**
 * 堆: 特殊的完全二叉树, 分为最大堆和最小堆
 * 最大堆: 所有父节点都比子节点要大
 * 最小堆: 所有父节点都比子节点要小
 * Created by autfish on 2016/9/13.
 */
public class HeapByArray {

    private int[] numbers;
    private int length;

    public HeapByArray(int[] numbers) {
        this.numbers = numbers;
        this.length = numbers.length;
    }

    /**
     * 调整二叉树
     * 如果父节点编号为x, 那么左子节点的编号是2x, 右子节点的编号是2x+1
     * 节点编号从1开始, 对应数组中的索引是编号-1
     * @param nodeId 节点编号, 从1开始
     */
    public void adjust(int nodeId) {
        int swapId;
        int flag = 0; //是否需要继续向下调整
        while(nodeId * 2 <= this.length && flag == 0) {
            //首先判断它和左子节点的关系, 并用swapId记录值较小的节点编号(最大堆是记录较大的)
            int index = nodeId - 1; //节点对应数组中数字的索引
            int leftChild = nodeId * 2 - 1; //左子节点对应数组中数字的索引
            int rightChild = nodeId * 2; //右子节点对应数组中数字的索引
            if(numbers[index] < numbers[leftChild])  {
                swapId = nodeId * 2;
            } else {
                swapId = nodeId;
            }
            //如果有右子节点, 再与右子节点比较
            if(nodeId * 2 + 1 <= this.length) {
                if(numbers[swapId - 1] < numbers[rightChild])
                    swapId = nodeId * 2 + 1;
            }
            //如果最小的节点编号不是自己, 说明子节点中有比父节点更小的
            if(swapId != nodeId) {
                swap(swapId, nodeId);
                nodeId = swapId;
            } else {
                flag = 1;
            }
        }
    }

    public void sort() {
        while(this.length > 1) {
            swap(1, this.length);
            this.length--;
            adjust(1);
        }
        for(int x = 0; x < numbers.length; x++) {
            System.out.print(numbers[x] + "  ");
        }
    }

    public void swap(int nodeId1, int nodeId2) {
        int t = numbers[nodeId1 - 1];
        numbers[nodeId1 - 1] = numbers[nodeId2 - 1];
        numbers[nodeId2 - 1] = t;
    }

    /**
     * 创建最大堆
     */
    public void createMaxHeap() {
        //从最后一个非叶节点到第一个节点依次向上调整
        for(int i = this.length / 2; i >= 1; i--) {
            adjust(i);
        }
    }

    public static void main(String[] args) {
        int length = 20;
        Random r = new Random();
        int[] numbers = new int[length];
        for(int i = 0; i < length; i++) {
            numbers[i] = r.nextInt(1000);
        }
        //numbers = new int[] { 7, 6, 5, 4, 3, 2, 1 };
        for(int x = 0; x < numbers.length; x++) {
            System.out.print(numbers[x] + "  ");
        }
        System.out.println();

        HeapByArray heap = new HeapByArray(numbers);
        heap.createMaxHeap();
        heap.sort();
    }
}
