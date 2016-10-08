package _algorithms.chap04.tree;

/**
 * 平衡二叉搜索树
 * Created by autfish on 2016/9/23.
 */
public class BalancedBinaryTree {

    private Node root;
    private boolean isTaller = false;

    public void add(int key) {
        if(root == null) {
            root = new Node(key);
            return;
        }
        addNode(root, new Node(key));
    }

    private void addNode(Node parent, Node child) {
        if(child.data == parent.data) {
            isTaller = false;
            return;
        }
        if(child.data < parent.data) {
            if(parent.leftChild != null) {
                addNode(parent.leftChild, child);
            } else {
                parent.leftChild = child;
                child.parent = parent;
                isTaller = true;
            }
            if(isTaller) {
                switch(parent.balanceFactor) {
                    case 1:
                        adjustLeft(parent);
                        isTaller = false;
                        break;
                    case 0:
                        parent.balanceFactor = 1;
                        break;
                    case -1:
                        parent.balanceFactor = 0;
                        isTaller = false;
                        break;
                }
            }
        } else {
            if(parent.rightChild != null) {
                addNode(parent.rightChild, child);
            } else {
                parent.rightChild = child;
                child.parent = parent;
                isTaller = true;
            }
            if (isTaller) {
                switch (parent.balanceFactor) {
                    case 1:
                        parent.balanceFactor = 0;
                        isTaller = false;
                        break;
                    case 0:
                        parent.balanceFactor = -1;
                        break;
                    case -1: {
                        adjustRight(parent);
                        isTaller = false;
                        break;
                    }
                }
            }
        }
    }

    private void adjustLeft(Node node) {
        Node son = node.leftChild;
        if(son.balanceFactor == 1) {
            node.balanceFactor = 0;
            son.balanceFactor = 0;
            rotateRight(node);
        } else if(son.balanceFactor == -1) {
            Node grandson = son.rightChild;
            switch (grandson.balanceFactor) {
                case 1:
                    node.balanceFactor = -1;
                    son.balanceFactor = 0;
                    break;
                case 0:
                    node.balanceFactor = 0;
                    son.balanceFactor = 0;
                    break;
                case -1:
                    node.balanceFactor = 0;
                    son.balanceFactor = 1;
            }
            grandson.balanceFactor = 0;
            rotateLeft(son);
            rotateRight(node);
        }
    }

    private void adjustRight(Node node) {
        Node son = node.rightChild;
        if(son.balanceFactor == -1) {
            node.balanceFactor = 0;
            son.balanceFactor = 0;
            rotateLeft(node);
        } else if(son.balanceFactor == 1) {
            Node grandson = son.leftChild;
            switch (grandson.balanceFactor) {
                case 1:
                    node.balanceFactor = 0;
                    son.balanceFactor = -1;
                    break;
                case 0:
                    node.balanceFactor = 0;
                    son.balanceFactor = 0;
                    break;
                case -1:
                    node.balanceFactor = 1;
                    son.balanceFactor = 0;
            }
            grandson.balanceFactor = 0;
            rotateRight(son);
            rotateLeft(node);
        }
    }

    private void rotateRight(Node node) {
        System.out.println("右旋" + node.data);
        Node child = node.leftChild;
        Node parent = node.parent;

        node.leftChild = child.rightChild;
        if(child.rightChild != null) {
            child.rightChild.parent = node;
        }

        child.rightChild = node;
        node.parent = child;

        if (parent == null) {
            root = child;
            root.parent = null;
        } else if (parent.leftChild != null && parent.leftChild.data == node.data) {
            parent.leftChild = child;
            child.parent = parent;
        } else {
            parent.rightChild = child;
            child.parent = parent;
        }
    }

    private void rotateLeft(Node node) {
        System.out.println("左旋" + node.data);
        Node child = node.rightChild;
        Node parent = node.parent;

        node.rightChild = child.leftChild;
        if(child.leftChild != null) {
            child.leftChild.parent = node;
        }

        child.leftChild = node;
        node.parent = child;

        if (parent == null) {
            root = child;
            root.parent = null;
        } else if (parent.leftChild != null && parent.leftChild.data == node.data) {
            parent.leftChild = child;
            child.parent = parent;
        } else {
            parent.rightChild = child;
            child.parent = parent;
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

    public void preOrder(Node node) {
        if(node == null) {
            return;
        }
        System.out.println(node);
        preOrder(node.leftChild);
        preOrder(node.rightChild);
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

        public String toString() {
            return this.data + "[Parent=" + (this.parent == null ? "Null" : this.parent.data)
                    + ", LeftChild=" + (this.leftChild == null ? "Null" : this.leftChild.data)
                    + ", RightChild=" + (this.rightChild == null ? "Null" : this.rightChild.data) + "]";
        }
    }

    public static void main(String[] args) {
        BalancedBinaryTree avlTree = new BalancedBinaryTree();

        avlTree.add(100);
        avlTree.add(90);
        avlTree.add(110);
        avlTree.add(80);
        avlTree.add(95);
        avlTree.add(120);
        avlTree.add(75);
        avlTree.add(85);
        avlTree.add(97);
        avlTree.add(96);

        avlTree.preOrder(avlTree.root);
    }
}
