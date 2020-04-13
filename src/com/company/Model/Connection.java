package com.company.Model;

public class Connection {

    // region 1. Init widgets

    private int fromPoint;
    private int toPoint;

    private double weight;

    // endregion

    // region 2. Constructor

    public Connection(int from, int to, double weight){
        this.fromPoint = from;
        this.toPoint = to;

        this.weight = weight;
    }

    // endregion

    // region 3. Getters and Setters

    public int getFromPoint() {
        return fromPoint;
    }

    public int getToPoint() {
        return toPoint;
    }

    public double getWeight() {
        return weight;
    }

    public void setFromPoint(int fromPoint) {
        this.fromPoint = fromPoint;
    }

    public void setToPoint(int toPoint) {
        this.toPoint = toPoint;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    // endregion

}
