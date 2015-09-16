package com.application.sample.selectcardviewprototype.app.behavior.impl;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.adapter.RecyclerviewAdapter;
import com.application.sample.selectcardviewprototype.app.animation.ExpandCardViewAnimation;
import com.application.sample.selectcardviewprototype.app.behavior.CardViewBehaviorInterface;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;

import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.IDLE;
import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.SELECTED;

/**
 * Created by davide on 14/09/15.
 */
public class AppearOverBehavior implements CardViewBehaviorInterface {

    private final Activity mActivity;
    private final RecyclerView mRecyclerView;
    private final StatusSingleton mStatus;
    private final View mOverlayView;
    private int mOldHeight;
    private View mSelectedview;

    public AppearOverBehavior(RecyclerView recyclerView, Activity activity) {
        mActivity = activity;
        mRecyclerView = recyclerView;
        mStatus = StatusSingleton.getInstance();
        mOverlayView = activity.getWindow().getDecorView().findViewById(R.id.overalayViewId);
    }

    /**
     *
     * @param selecting
     */
    private void colorize(boolean selecting, int position) {
        int color = mActivity.getResources().getColor(selecting ? R.color.material_blue_grey_950 :
                R.color.cardview_light_background);
        View selectedView = mRecyclerView.getChildAt(position);
        selectedView.setBackgroundColor(color);
    }

    /**
     *
     * @param view
     */
    public void animate(View view,int initialHeight, int finalHeight, boolean down) {
        ExpandCardViewAnimation animation = new ExpandCardViewAnimation(view, initialHeight, finalHeight, down);
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
        View view = mRecyclerView.getChildAt(position);
        mOldHeight = view.getHeight();
        return view;
    }

    /**
     *
     * @return int
     */
    private int getParentHeight() {
        int height = 1000;
        return height;
    }

    @Override
    public void expand(int position) {
        mStatus.setStatus(SELECTED);
        mSelectedview = initSelectedView(position);
        colorize(true, position);
        animate(mSelectedview, mSelectedview.getHeight(), getParentHeight(), true);
        mRecyclerView.getAdapter().notifyItemChanged(position);
    }

    @Override
    public void collapse() {
        int position = mRecyclerView.getChildPosition(mSelectedview);
        colorize(false, position);
//        mOverlayView.setVisibility(View.GONE);
        animate(mSelectedview, mSelectedview.getHeight(), mOldHeight, false);
        mRecyclerView.getAdapter().notifyItemChanged(position);
    }

}
