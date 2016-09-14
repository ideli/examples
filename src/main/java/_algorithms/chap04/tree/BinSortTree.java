package _algorithms.chap04.tree;

/**
 * 二叉排序树
 * 左子节点的数据小于父节点, 右子节点的数据大于父节点
 * 中序遍历结果是有序数据
 * Created by autfish on 2016/9/14.
 */
public class BinSortTree {

    private Node root;

    public void add(Node child) {
        if(child == null) {
            return;
        }
        if(root == null) {
            root = child;
            return;
        }
        addNode(root, child);
    }

    private void addNode(Node parent, Node child) {
        if(child.data < parent.data) {
            if(parent.leftChild != null) {
                addNode(parent.leftChild, child);
            } else {
                parent.leftChild = child;
            }
        } else {
            if(parent.rightChild != null) {
                addNode(parent.rightChild, child);
            } else {
                parent.rightChild = child;
            }
        }
    }

    public void search(int key) {
        Node target = searchNode(root, key);
        if(target == null) {
            System.out.println("未找到关键字" + key);
        } else {
            System.out.println("查找到关键字" + key);
        }
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

    public void delete(Node from, Node child) {
        if(from == null) {
            return;
        }
        if(from.data == child.data) {
            deleteNode(from);
        } else if(child.data < from.data) {
            delete(from.leftChild, child);
        } else {
            delete(from.rightChild, child);
        }
    }

    private void deleteNode(Node node) {
        if(node.rightChild == null) {
            node = node.leftChild;
        } else if(node.leftChild == null) {
            node = node.rightChild;
        } else {
            Node parent = node;
            Node leaf = node.rightChild;
            while(leaf.leftChild != null) {
                parent = leaf;
                leaf = leaf.leftChild;
            }
            node.data = leaf.data;
            if(parent == node) {
                parent.rightChild = null;
            } else {
                parent.leftChild = null;
            }
        }
    }

    public void inOrder(Node node) {
        if(node == null) {
            return;
        }
        inOrder(node.leftChild);
        System.out.print(node.data + " ");
        inOrder(node.rightChild);
    }

    private static class Node {
        Node leftChild;
        Node rightChild;
        int data;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        int[] datas = new int[] { 54, 90, 6, 69, 12, 37, 92, 28, 65, 83 };
        BinSortTree bsTree = new BinSortTree();
        for(int i = 0; i < datas.length; i++) {
            bsTree.add(new Node(datas[i]));
        }
        System.out.print("中序遍历");
        bsTree.inOrder(bsTree.root);
        System.out.println();

        bsTree.search(37);
        bsTree.search(38);
    }
}
