package _algorithms.chap04.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by autfish on 2016/9/13.
 */
public class BinTreeByList {

    List<Node> nodes = null;
    private int[] datas = null;
    private int number;

    public BinTreeByList(int[] datas) {
        this.datas = datas;
        this.number = this.datas.length;
    }

    public void create() {
        nodes = new LinkedList<>();
        for(int i = 0; i < this.number; i++) {
            nodes.add(new Node(datas[i]));
        }
        //如果父节点编号为x, 那么左子节点的编号是2x, 右子节点的编号是2x+1
        for(int noteId = 1; noteId <= this.number / 2; noteId++) {
            //索引从0开始, 需要在节点编号上减1
            nodes.get(noteId - 1).leftChild = nodes.get(noteId * 2 - 1);
            if(noteId * 2 < this.number)
                nodes.get(noteId - 1).rightChild = nodes.get(noteId * 2);
        }
    }

    public void preOrder(Node node) {
        if(node == null) {
            return;
        }
        System.out.print(node.data + " ");
        preOrder(node.leftChild);
        preOrder(node.rightChild);
    }

    public void inOrder(Node node) {
        if(node == null) {
            return;
        }
        inOrder(node.leftChild);
        System.out.print(node.data + " ");
        inOrder(node.rightChild);
    }

    public void postOrder(Node node) {
        if(node == null) {
            return;
        }
        postOrder(node.leftChild);
        inOrder(node.rightChild);
        System.out.print(node.data + " ");
    }

    public static void main(String[] args) {
        int[] numbers = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        for(int x = 0; x < numbers.length; x++) {
            System.out.print(numbers[x] + "  ");
        }
        System.out.println();

        BinTreeByList tree = new BinTreeByList(numbers);
        tree.create();
        System.out.print("先序遍历");
        tree.preOrder(tree.nodes.get(0));
        System.out.println();
        System.out.print("中序遍历");
        tree.inOrder(tree.nodes.get(0));
        System.out.println();
        System.out.print("后续遍历");
        tree.postOrder(tree.nodes.get(0));
    }

    private static class Node {
        Node leftChild;
        Node rightChild;
        int data;

        public Node(int data) {
            this.data = data;
        }
    }
}
