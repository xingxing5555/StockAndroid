package com.example.admin.basic.stock;

import android.graphics.DashPathEffect;

public interface ILineScatter {
    /**
     * Returns true if vertical highlight indicator lines are enabled (drawn)
     *
     * @return
     */
    boolean isVerticalHighlightIndicatorEnabled();

    /**
     * Returns true if vertical highlight indicator lines are enabled (drawn)
     *
     * @return
     */
    boolean isHorizontalHighlightIndicatorEnabled();

    /**
     * Returns the line-width in which highlight lines are to be drawn.
     *
     * @return
     */
    float getHighlightLineWidth();

    /**
     * Returns the DashPathEffect that is used for highlighting.
     *
     * @return
     */
    DashPathEffect getDashPathEffectHighlight();

    /**
     * Returns the color that is used for drawing the highlight indicators.
     *
     * @return
     */
    int getHighLightColor();
}