package com.company.model;

public class TreeNode {

    // region 1. Init and Decl

    private int val;
    private TreeNode left;
    private TreeNode right;

    // endregion

    // region 2. Constructor

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public TreeNode(int val){
        this.val = val;
        this.left = null;
        this.right = null;
    }

    // endregion

    // region 3. Getters and Setters

    public int getVal() {
        return val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    // endregion

}
