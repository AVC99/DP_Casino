package model;

import view.views.Roulette;

public class RouletteCell {
    private int value;
    private double angle;
    private String color;
    public static final String RED="RED";
    public static final String BLACK="BLACK";
    public static final String GREEN="GREEN";

    public RouletteCell(int value, double angle, String color ){
        this.angle=angle;
        this.value=value;
        this.color= color;
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public double getAngle() {
        return angle;
    }
}
