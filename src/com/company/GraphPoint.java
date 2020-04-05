package com.company;

import java.awt.*;

public class GraphPoint {
    // region 0. Constants

    // endregion

    // region 1. Init widgets

    private Dimension position;

    private int name;

    // endregion

    // region 2. Constructor

    public GraphPoint(int  name, Dimension position){
        this.name = name;
        this.position = position;
    }

    // endregion

    // region 3. Getters and Setters

    public Dimension getPosition() {
        return position;
    }

    public int getName() {
        return name;
    }

    public void setPosition(Dimension position) {
        this.position = position;
    }

    public void setName(int name) {
        this.name = name;
    }

    // endregion
}
