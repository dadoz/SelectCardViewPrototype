package com.application.sample.selectcardviewprototype.app.behavior.impl;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.animation.ExpandCardViewAnimation;
import com.application.sample.selectcardviewprototype.app.behavior.CardViewStrategyInterface;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;

import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.SELECTED;

/**
 * Created by davide on 14/09/15.
 */
public class ExpandInListBehavior implements CardViewStrategyInterface {

    private final Activity activity;
    private final RecyclerView recyclerView;
    private final StatusSingleton status;
    private int oldHeight;
    private View selectedView;

    public ExpandInListBehavior(RecyclerView recyclerView, Activity activity) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        status = StatusSingleton.getInstance();
    }

    /**
     *
     * @param selecting
     */
    private void colorize(boolean selecting, int position) {
        int color = activity.getResources().getColor(selecting ? R.color.material_blue_grey_950 :
                R.color.cardview_light_background);
        View selectedView = recyclerView.getChildAt(position);
        selectedView.setBackgroundColor(color);
    }

    /**
     *
     * @param view
     */
    public void animate(View view,int initialHeight, int finalHeight, boolean down) {
        ExpandCardViewAnimation animation = new ExpandCardViewAnimation(view, initialHeight, finalHeight, 0, down);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(500);
        view.setAnimation(animation);
        view.animate().start();
        view.invalidate();
    }

    /**
     *
     * @param position
     */
    private View initSelectedView(int position) {
        View view = recyclerView.getChildAt(position);
        oldHeight = view.getHeight();
        return view;
    }

    /**
     *
     * @return int
     */
    private int getParentHeight() {
        int height = 1000; //TODO must be changed
        return height;
    }

    @Override
    public void expand(int position) {
        status.setStatus(SELECTED);
        selectedView = initSelectedView(position);
        colorize(true, position);
        animate(selectedView, selectedView.getHeight(), getParentHeight(), true);
        recyclerView.getAdapter().notifyItemChanged(position);
    }

    @Override
    public void collapse() {
        int position = recyclerView.getChildPosition(selectedView);
        colorize(false, position);
        animate(selectedView, selectedView.getHeight(), oldHeight, false);
        recyclerView.getAdapter().notifyItemChanged(position);
    }

}
