package _algorithms.chap04.tree;

/**
 * Created by Administrator on 2016/9/23.
 */
public class BalancedBinaryTree {

    private Node root;

    public void add(int key) {
        if(root == null) {
            root = new Node(key);
            return;
        }
        if(searchNode(root, key) == null)
            addNode(root, new Node(key));
    }

    private void addNode(Node parent, Node child) {
        if(child.data < parent.data) {
            if(parent.leftChild != null) {
                addNode(parent.leftChild, child);
            } else {
                parent.leftChild = child;
                child.parent = parent;
            }
        } else {
            if(parent.rightChild != null) {
                addNode(parent.rightChild, child);
            } else {
                parent.rightChild = child;
            }
        }
    }

    public Node search(int key) {
        return searchNode(root, key);
    }

    private Node searchNode(Node from, int key) {
        if(from == null || key == from.data) {
            return from;
        } else if(key > from.data) {
            return searchNode(from.rightChild, key);
        } else {
            return searchNode(from.leftChild, key);
        }
    }

    private static class Node {
        Node parent;
        Node leftChild;
        Node rightChild;
        int data;
        int balanceFactor = 0;

        public Node(int data) {
            this.data = data;
        }
    }
}
