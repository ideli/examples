package _algorithms.chap04.tree;

/**
 * 二叉排序树
 * 左子节点的数据小于父节点, 右子节点的数据大于父节点
 * 中序遍历结果是有序数据
 * Created by autfish on 2016/9/14.
 */
public class BinSortTree {

    private Node root;

    public void add(int key) {
        if(root == null) {
            root = new Node(key);
            return;
        }
        if(searchNode(root, key) == null)
            addNode(root, new Node(key));
    }

    public Node search(int key) {
        return searchNode(root, key);
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

    private Node searchNode(Node from, int key) {
        if(from == null || key == from.data) {
            return from;
        } else if(key > from.data) {
            return searchNode(from.rightChild, key);
        } else {
            return searchNode(from.leftChild, key);
        }
    }

    public void delete(int key) {
        Node child = root;
        Node parent = child;
        boolean isLefChild = true;
        while(child != null) {
            if(child.data == key) {
                deleteNode(parent, child, isLefChild);
                child = null;
            } else if(key < child.data) {
                isLefChild = true;
                parent = child;
                child = child.leftChild;
            } else {
                isLefChild = false;
                parent = child;
                child = child.rightChild;
            }
        }
    }

    private void deleteNode(Node parent, Node child, boolean isLefChild) {
        if(child.leftChild == null && child.rightChild == null) {
            if(isLefChild) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
        } else if(child.leftChild == null) {
            if(isLefChild) {
                parent.leftChild = child.rightChild;
            } else {
                parent.rightChild = child.rightChild;
            }
        } else if(child.rightChild == null) {
            if(isLefChild) {
                parent.leftChild = child.leftChild;
            } else {
                parent.rightChild = child.leftChild;
            }
        } else {
            Node leaf = child.rightChild;
            parent = child;
            while(leaf.leftChild != null) {
                parent = leaf;
                leaf = leaf.leftChild;
            }
            child.data = leaf.data;
            if(parent != child)
                parent.leftChild = null;
            else
                parent.rightChild = null;
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
            bsTree.add(datas[i]);
        }
        System.out.print("中序遍历");
        bsTree.inOrder(bsTree.root);
        System.out.println();

        bsTree.delete(83);
        System.out.print("中序遍历");
        bsTree.inOrder(bsTree.root);
        System.out.println();
    }
}
