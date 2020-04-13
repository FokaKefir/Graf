package com.company.Model;

import java.awt.*;

public class PointPosition {

    // region 0. Constants

    // endregion

    // region 1. Init widgets

    private int x;
    private int y;

    private int name;

    // endregion

    // region 2. Constructor

    public PointPosition(int  name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    // endregion

    // region 3. Getters and Setters


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setName(int name) {
        this.name = name;
    }

    // endregion
}
