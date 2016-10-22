package io.samlewis.neuralview;

import android.graphics.Point;

public class DirectedPoint extends Point {

    public boolean yDirection;
    public boolean xDirection;

    public DirectedPoint(boolean xDirection, boolean yDirection) {
        this.xDirection = xDirection;
        this.yDirection = yDirection;
    }

}
