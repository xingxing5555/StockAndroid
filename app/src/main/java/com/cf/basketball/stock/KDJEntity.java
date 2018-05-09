package com.cf.basketball.stock;

/**
 * Created by GongWen on 17/1/4.
 */

public class KDJEntity {

    /**
     * K : 100
     * d : 100
     * j : 100
     */

    private float K;
    private float d;
    private float j;

    public float getK() {
        return K;
    }

    public void setK(float K) {
        this.K = K;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }

    public float getJ() {
        return j;
    }

    public void setJ(float j) {
        this.j = j;
    }

    public float getMax() {
        float max = 100;
        if (K > max) {
            max = K;
        }
        if (d > max) {
            max = d;
        }
        if (j > max) {
            max = j;
        }
        return max;
    }

    public float getMin() {
        float min = 0;
        if (K < min) {
            min = K;
        }
        if (d < min) {
            min = d;
        }
        if (j < min) {
            min = j;
        }
        return min;
    }
}
