package com.mycompany.issue1;

class Node {

    String name;
    int width;
    int height;
    Node left;
    Node right;

    public Node() {
        this.left = null;
        this.right = null;
    }

    public Node(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.left = null;
        this.right = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return '[' + "Name: " + name + ", " + width + height +']';
    }

}
