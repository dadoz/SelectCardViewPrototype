package com.application.sample.selectcardviewprototype.app.animator;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.ref.WeakReference;

public class AnimatorBuilder {

    private static final String TOP = "top";
    private final int duration;
    private static final String TRANSLATION_Y ="translationY";
    private String ALPHA = "alpha";
    private String BOTTOM = "bottom";

    /**
     * TODO follow up builder design pattern
     * @param context
     * @return
     */
    public static AnimatorBuilder getInstance(WeakReference<Context> context) {
        return new AnimatorBuilder(context);
    }


    public AnimatorBuilder(WeakReference<Context> context) {
        this.duration = context.get().getResources().
                getInteger(android.R.integer.config_mediumAnimTime);
    }

    /**
     *
     * @param view
     * @param endHeight
     * @return
     */
    public Animator buildResizeBottomAnimator(@NonNull View view, @NonNull int endHeight) {
        Animator animator = ObjectAnimator.ofInt(view, BOTTOM, endHeight);
        animator.setDuration(duration);
        return animator;
    }
    /**
     *
     * @param view
     * @param endHeight
     * @return
     */
    public Animator buildResizeTopAnimator(@NonNull View view, @NonNull int endHeight) {
        Animator animator = ObjectAnimator.ofInt(view, TOP, endHeight);
        animator.setDuration(duration);
        return animator;
    }

    /**
     *
     * @param view
     * @param startY
     * @param endY
     * @return
     */
    public Animator buildTranslationAnimator(@NonNull View view, @NonNull float startY,
                                             @NonNull float endY) {
        Animator animator = ObjectAnimator.ofFloat(view, TRANSLATION_Y, startY, endY);
        animator.setDuration(duration);
        return animator;
    }

    /**
     *
     * @param colorFrom
     * @param colorTo
     * @return
     */
    private ValueAnimator buildColorTransitionAnimator(@NonNull int colorFrom, @NonNull int colorTo) {
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        animator.setDuration(duration);
        return animator;
    }

    /**
     *
     * @param view
     * @param endAlpha
     * @param startAlpha
     * @return
     */
    public Animator buildAlphaAnimator(View view, float startAlpha, float endAlpha) {
        Animator animator = ObjectAnimator.ofFloat(view, ALPHA, startAlpha, endAlpha);
        animator.setDuration(duration);
        return animator;
    }

    /**
     *
     * @param view
     * @param expanding
     * @return
     */
    public Animator getHideAnimator( View view, boolean expanding) {
        int start = expanding ? 1 : 0;
        int end = expanding ? 0 : 1;
        return buildAlphaAnimator(view, start, end);
    }
    /**
     *
     * @param colorFrom
     * @param colorTo
     * @param expanding
     * @return
     */
    public ValueAnimator getColorTransitionAnimator(int colorFrom, int colorTo, boolean expanding) {
        return expanding ?
                buildColorTransitionAnimator(colorFrom, colorTo) :
                buildColorTransitionAnimator(colorTo, colorFrom);
    }

    /**
     *
     * @param view
     * @param marginTop
     * @param expanding
     * @return
     */
    public Animator getTranslationAnimator( View view, int marginTop, boolean expanding) {
        int start = expanding ? 0 : -marginTop;
        int end = expanding ? -marginTop : 0;
        return  buildTranslationAnimator(view, start, end);

    }

    /**
     *
     * @param view
     * @param initialViewHeight
     * @param marginTop
     * @param containerHeight
     * @param expanding
     * @return
     */
    public Animator getResizeBottomAnimator(View view, int initialViewHeight, int marginTop,
                                            int containerHeight, boolean expanding) {
        int start = expanding ?
                containerHeight + marginTop :
                initialViewHeight + marginTop;
        return buildResizeBottomAnimator(view, start);
    }


}
