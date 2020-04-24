package com.company.model;

import java.util.Objects;

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

    // region 4. Equal and Hash code

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return fromPoint == that.fromPoint &&
                toPoint == that.toPoint &&
                Double.compare(that.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromPoint, toPoint, weight);
    }

    // endregion

}
