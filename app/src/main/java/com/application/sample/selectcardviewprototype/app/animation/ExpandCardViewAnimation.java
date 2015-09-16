package com.application.sample.selectcardviewprototype.app.animation;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by davide on 15/09/15.
 */
public class ExpandCardViewAnimation extends Animation {
    private final int targetHeight;
    private final View view;
    private final boolean down;
    private final int initialHeight;

    public ExpandCardViewAnimation(View view, int initialHeight, int targetHeight, boolean down) {
        this.view = view;
        this.targetHeight = targetHeight;
        this.down = down;
        this.initialHeight = initialHeight;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
//            interpolatedTime = down ? interpolatedTime : (1 - interpolatedTime);
        view.getLayoutParams().height = getScaledHeight(down, interpolatedTime);
        view.requestLayout();
    }

    /**
     *
     * @param down
     * @param interpolatedTime
     * @return
     */
    private int getScaledHeight(boolean down, float interpolatedTime) {
        int deltaHeight = (initialHeight - targetHeight);
        return down ?
                initialHeight + (int) (targetHeight * interpolatedTime) :
                initialHeight - (int) (deltaHeight * interpolatedTime);
    }

    @Override
    public void initialize(int width, int height, int parentWidth,
                           int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}