package com.application.sample.selectcardviewprototype.app.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by davide on 15/10/15.
 */
public class AnimatorBuilder {

    private static final String TOP = "top";
    private final int duration;
    private String TRANSLATION_Y ="translationY";
    private String ALPHA = "alpha";
    private String BOTTOM = "bottom";
    private int oldViewHeight = 0;
    private int oldViewTop = 0;

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
    public Animator getShowHideAnimator(View view, boolean expanding) {
        int start = expanding ? 1 : 0;
        int end = expanding ? 0 : 1;
        return buildAlphaAnimator(view, start, end);
    }

    /**
     *
     * @param view
     * @param expanding
     * @param marginTop
     * @return
     */
    public Animator getTranslationAnimator(View view, int marginTop, boolean expanding) {
        int start = expanding ? 0 : -marginTop;
        int end = expanding ? -marginTop : 0;
        return buildTranslationAnimator(view, start, end);
    }

    /**
     *
     * @param view
     * @param expanding
     * @param containerHeight
     * @return
     */
    public Animator getResizeBottomAnimator(View view, int containerHeight, boolean expanding) {
        oldViewHeight = expanding ? view.getHeight() : oldViewHeight;
        oldViewTop = expanding ? view.getTop() : oldViewTop;
        int start = expanding ? containerHeight + oldViewTop : oldViewHeight + oldViewTop;
        return buildResizeBottomAnimator(view, start);
    }
}
