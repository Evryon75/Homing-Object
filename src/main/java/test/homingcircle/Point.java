package test.homingcircle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point extends Circle {

    private double hsp;
    private double vsp;
    private final int maxSpeed;

    Point(double radius, double x, double y, Color color, int maxSpeed){

        super(radius);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setFill(color);

        this.hsp = 0;
        this.vsp = 0;
        this.maxSpeed = maxSpeed;

    }

    void update(){
        this.setCenterX(this.getCenterX() + this.hsp);
        this.setCenterY(this.getCenterY() + this.vsp);
    }

    public double getHsp() {
        return hsp;
    }
    public void setHsp(double hsp) {
        this.hsp = hsp;
    }
    public double getVsp() {
        return vsp;
    }
    public void setVsp(double vsp) {
        this.vsp = vsp;
    }
    public int getMaxSpeed() {
        return maxSpeed;
    }
}
