package com.mycompany.issue1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
    Node root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(String name, int width, int height) {
        root = new Node(name, width, height);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void addLeaf(String name, int width, int height) {
        if (root == null) {
            root = new Node(name, width, height);
        } else {
            Node newNode = new Node(name, width, height);
            addLeafToTree(root, newNode);
        }
    }

    private void addLeafToTree(Node root, Node newNode) {
        if (root.left == null) {
            root.left = newNode;
        } else if (root.right == null) {
            root.right = newNode;
        } else {
            // Add the node to the side with fewer nodes for simplicity
            int leftCount = countNodes(root.left);
            int rightCount = countNodes(root.right);
            if (leftCount <= rightCount) {
                addLeafToTree(root.left, newNode);
            } else {
                addLeafToTree(root.right, newNode);
            }
        }
    }

    public void preorder(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.getName() + "[" + node.getWidth() + "," + node.getHeight() + "]");
        preorder(node.left);
        preorder(node.right);
    }

    public String printTree(Node root) {
        String treeRepresentation = "(" + printFormattedTree(root) + ")";
        return treeRepresentation;
    }

    String printFormattedTree(Node node) {
        if (node == null) {
            return "";
        }
        String result = node.getName() + "[" + node.getWidth() + "," + node.getHeight() + "]";
        String left = printFormattedTree(node.getLeft());
        String right = printFormattedTree(node.getRight());

        if (!left.isEmpty() && !right.isEmpty()) {
            result += " | (" + left + " - " + right + ")";
        } else if (!left.isEmpty()) {
            result += " | " + left;
        } else if (!right.isEmpty()) {
            result += " - " + right;
        }
        return result;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Method to delete a node with the given name from the binary tree
    public Node removeLeaf(Node root, String name) {
        if (root == null) {
            return null;
        }

        if (name.compareTo(root.name) < 0) {
            root.left = removeLeaf(root.left, name);
        } else if (name.compareTo(root.name) > 0) {
            root.right = removeLeaf(root.right, name);
        } else {
            // Node to be deleted found
            if (root.left == null && root.right == null) {
                return null; // Case 1: No children
            } else if (root.left == null) {
                return root.right; // Case 2: One child (right)
            } else if (root.right == null) {
                return root.left; // Case 2: One child (left)
            } else {
                // Case 3: Two children
                Node minRightSubtree = findMin(root.right);
                root.name = minRightSubtree.name;
                root.width = minRightSubtree.width;
                root.height = minRightSubtree.height;
                root.right = removeLeaf(root.right, minRightSubtree.name);
            }
        }
        return root;
    }

    private int countNodes(Node node) {
        if (node == null)
            return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    public void getPreOrder(Node node, ArrayList<String> result) {
        if (node != null) {
            result.add(String.valueOf(node.getName()));
            getPreOrder(node.getRight(), result);
            getPreOrder(node.getLeft(), result);
        }
    }

    public void getInOrder(Node node, ArrayList<String> result) {
        if (node != null) {
            getInOrder(node.getRight(), result);
            result.add(String.valueOf(node.getName()));
            getInOrder(node.getLeft(), result);
        }
    }

    public void getPostOrder(Node node, ArrayList<String> result) {
        if (node != null) {
            getPostOrder(node.getRight(), result);
            getPostOrder(node.getLeft(), result);
            result.add(String.valueOf(node.getName()));
        }
    }

    public void getLevelOrder(Node node, ArrayList<String> result) {
        if (node != null) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(node);
            while (!queue.isEmpty()) {
                Node current = queue.poll();
                result.add(String.valueOf(current.getName()));
                if (current.getRight() != null) {
                    queue.add(current.getRight());
                }
                if (current.getLeft() != null) {
                    queue.add(current.getLeft());
                }
            }
        }
    }
}
