package _algorithms.chap04.tree;

import java.util.Random;

/**
 * 完全二叉树
 * Created by autfish on 2016/9/8.
 */
public class BinTreeByArray {
    private int[] numbers;

    public BinTreeByArray(int[] numbers) {
        this.numbers = numbers;
    }

    /**
     * 先序遍历
     * 根节点 -> 遍历左子树 -> 遍历右子树
     * @param nodeId
     */
    public void preOrder(int nodeId) {
        if(nodeId <= numbers.length) {
            System.out.print(numbers[nodeId - 1] + "  ");
            preOrder(nodeId * 2);
            preOrder(nodeId * 2 + 1);
        }
    }

    /**
     * 中序遍历
     * 左子树 -> 父节点 -> 右子树
     * @param nodeId
     */
    public void inOrder(int nodeId) {
        if(nodeId <= numbers.length) {
            inOrder(nodeId * 2);
            System.out.print(numbers[nodeId - 1] + "  ");
            inOrder(nodeId * 2 + 1);
        }
    }

    /**
     * 后续遍历
     * 左子树 -> 右子树 -> 父节点
     * @param nodeId
     */
    public void postOrder(int nodeId) {
        if(nodeId <= numbers.length) {
            postOrder(nodeId * 2);
            inOrder(nodeId * 2 + 1);
            System.out.print(numbers[nodeId - 1] + "  ");
        }
    }

    public static void main(String[] args) {
        int[] numbers = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        for(int x = 0; x < numbers.length; x++) {
            System.out.print(numbers[x] + "  ");
        }
        System.out.println();

        BinTreeByArray tree = new BinTreeByArray(numbers);
        System.out.print("先序遍历");
        tree.preOrder(1);
        System.out.println();
        System.out.print("中序遍历");
        tree.inOrder(1);
        System.out.println();
        System.out.print("后续遍历");
        tree.postOrder(1);
    }
}
