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
public class AppearOverBehavior implements CardViewBehaviorInterface {

    private final Activity mActivity;
    private final RecyclerView mRecyclerView;
    private final StatusSingleton mStatus;
    private int mPosition;

    public AppearOverBehavior(RecyclerView recyclerView, Activity activity) {
        mActivity = activity;
        mRecyclerView = recyclerView;
        mStatus = StatusSingleton.getInstance();
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


    @Override
    public void expand(int position) {
        mPosition = position;
        colorize(true, position);
    }

    @Override
    public void collapse() {
        colorize(true, mPosition);

    }
}
