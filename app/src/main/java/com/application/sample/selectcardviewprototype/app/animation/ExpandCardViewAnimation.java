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
    private final boolean isDown;
    private final int initialHeight;
    private final int marginTop;

    public ExpandCardViewAnimation(View view, int initialHeight, int targetHeight, int marginTop, boolean isDown) {
        this.view = view;
        this.isDown = isDown;
        this.targetHeight = targetHeight;
        this.initialHeight = initialHeight;
        this.marginTop = marginTop;         //TODO calculate this offset
//        Log.e("TAG", "" + marginTop);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        view.getLayoutParams().height = getScaledHeight(interpolatedTime);
        //over sampling to smooth transition
        float y = getDeltaTranslation(interpolatedTime);
//        Log.e("TAG", "translation" + y);
        for (int i = 0; i < 2; i ++) {
            float yTranslation = (i == 0) ? y / 2 : y;
            if (isDown) {
                view.setTranslationY(yTranslation);
            }
            view.requestLayout();
        }
    }

    /**
     * 
     * @param interpolatedTime
     * @return
     */
    public float getDeltaTranslation(float interpolatedTime) {
        return isDown ?
                - marginTop * interpolatedTime :
                marginTop * interpolatedTime;
    }

    /**
     *
     * @param interpolatedTime
     * @return
     */
    private int getScaledHeight(float interpolatedTime) {
        int deltaHeight = (initialHeight - targetHeight);
        return isDown ?
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