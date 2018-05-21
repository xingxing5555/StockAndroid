package com.example.admin.basic.stock;

/**
 * Created by GongWen on 17/1/4.
 */

public class MACDEntity  {

    /**
     * dif : 0
     * dea : 0
     * macd : 0
     */

    private float dif;
    private float dea;
    private float macd;

    public float getDif() {
        return dif;
    }

    public void setDif(float dif) {
        this.dif = dif;
    }

    public float getDea() {
        return dea;
    }

    public void setDea(float dea) {
        this.dea = dea;
    }

    public float getMacd() {
        return macd;
    }

    public void setMacd(float macd) {
        this.macd = macd;
    }

    public float getMax() {
        float max = Integer.MIN_VALUE;
        if (dif > max) {
            max = dif;
        }
        if (dea > max) {
            max = dea;
        }
        if (macd > max) {
            max = macd;
        }
        return max;
    }

    public float getMin() {
        float min = Integer.MAX_VALUE;
        if (dif < min) {
            min = dif;
        }
        if (dea < min) {
            min = dea;
        }
        if (macd < min) {
            min = macd;
        }
        return min;
    }
}
