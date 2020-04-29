package com.company.model;

public class Children {

    // region 0. Constants

    public static final int NOT_DEFINED = -1;

    // endregion

    // region 1. Decl and Init

    private int left;
    private int right;

    // endregion

    // region 2. Constructor

    public Children(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public Children(){
        this.left = NOT_DEFINED;
        this.right = NOT_DEFINED;

    }

    // endregion

    // region 3. Getters and Setters

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }


    // endregion
}
