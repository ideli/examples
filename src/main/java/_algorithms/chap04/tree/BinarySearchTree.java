package _algorithms.chap04.tree;

/**
 * 二叉搜索树
 * 左子节点的数据小于父节点, 右子节点的数据大于父节点
 * 中序遍历结果是有序数据
 * Created by autfish on 2016/9/14.
 */
public class BinarySearchTree {

    private Node root;
    private int steps;

    /**
     * 插入节点
     * @param key
     */
    public void add(int key) {
        if(root == null) {
            root = new Node(key);
            return;
        }
        addNode(root, new Node(key));
    }

    private void addNode(Node parent, Node child) {
        if(child.data == parent.data) {
            return;
        }
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

    /**
     * 查找节点
     * @param key
     */
    public void search(int key) {
        this.steps = 0;
        Node node = searchNode(root, key);
        if(node == null) {
            System.out.println("共查找" + this.steps + "次, 未找到" + key);
        } else {
            System.out.println("共查找" + this.steps + "次, 搜索到" + key);
        }
    }

    private Node searchNode(Node from, int key) {
        this.steps++;
        if(from == null || key == from.data) {
            return from;
        } else if(key > from.data) {
            return searchNode(from.rightChild, key);
        } else {
            return searchNode(from.leftChild, key);
        }
    }

    /**
     * 删除节点
     * @param key
     */
    public void delete(int key) {
        Node child = root;
        Node parent = child;
        boolean isLeftChild = true;
        while(child != null) {
            if(child.data == key) {
                deleteNode(parent, child, isLeftChild);
                child = null;
            } else if(key < child.data) {
                isLeftChild = true;
                parent = child;
                child = child.leftChild;
            } else {
                isLeftChild = false;
                parent = child;
                child = child.rightChild;
            }
        }
    }

    private void deleteNode(Node parent, Node child, boolean isLeftChild) {
        if(child.leftChild == null && child.rightChild == null) {
            if(isLeftChild) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
        } else if(child.leftChild == null) {
            if(isLeftChild) {
                parent.leftChild = child.rightChild;
            } else {
                parent.rightChild = child.rightChild;
            }
        } else if(child.rightChild == null) {
            if(isLeftChild) {
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
                parent.leftChild = leaf.leftChild;
            else
                parent.rightChild = leaf.rightChild;
        }
    }

    /**
     * 中序遍历二叉搜索树, 结果是从小到大排列的
     * @param node
     */
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
        int[] datas = new int[] { 8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15 };
        BinarySearchTree bsTree = new BinarySearchTree();
        for(int i = 0; i < datas.length; i++) {
            bsTree.add(datas[i]);
        }
        System.out.print("中序遍历");
        bsTree.inOrder(bsTree.root);
        System.out.println();

        bsTree.search(8);
        bsTree.search(12);
        bsTree.search(15);

        System.out.println("删除节点8");
        bsTree.delete(8);

        System.out.print("中序遍历");
        bsTree.inOrder(bsTree.root);
        System.out.println();

        bsTree.search(8);
    }
}
