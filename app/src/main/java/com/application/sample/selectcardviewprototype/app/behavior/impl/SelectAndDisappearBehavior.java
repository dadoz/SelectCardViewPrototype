package com.application.sample.selectcardviewprototype.app.behavior.impl;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.adapter.RecyclerviewAdapter;
import com.application.sample.selectcardviewprototype.app.behavior.CardViewBehaviorInterface;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;

import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.SELECTED;

/**
 * Created by davide on 14/09/15.
 */
public class SelectAndDisappearBehavior  implements CardViewBehaviorInterface {

    private static final String TAG = "SelectAndDisappearBehav";
    private final Activity mActivity;
    private final RecyclerView mRecyclerView;
    private final StatusSingleton mStatus;

    public SelectAndDisappearBehavior(RecyclerView recyclerView, Activity activity) {
        mActivity = activity;
        mRecyclerView = recyclerView;
        mStatus = StatusSingleton.getInstance();
    }

    /**
     *
     * @param selecting
     */
    private void colorize(boolean selecting) {
        int color = mActivity.getResources().getColor(selecting ? R.color.material_blue_grey_950 :
                R.color.cardview_light_background);
        View selectedView = mRecyclerView.getChildAt(0);
        selectedView.setBackgroundColor(color);

    }

    /**
     *
     * @param position
     */
    private void select(int position) {
        Log.e(TAG, "" + position);
        ((RecyclerviewAdapter) mRecyclerView.getAdapter()).leaveSelectedItem(position);
        mStatus.setStatus(SELECTED);
    }

    @Override
    public void expand(int position) {
        select(position);
        colorize(true);
    }

    @Override
    public void collapse() {
        colorize(false);
    }
}
