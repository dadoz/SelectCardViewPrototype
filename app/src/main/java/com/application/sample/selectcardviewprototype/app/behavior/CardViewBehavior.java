package com.application.sample.selectcardviewprototype.app.behavior;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import com.application.sample.selectcardviewprototype.app.behavior.impl.AppearOverBehavior;
import com.application.sample.selectcardviewprototype.app.behavior.impl.SelectAndDisappearBehavior;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;


/**
 * Created by davide on 10/09/15.
 */
public class CardViewBehavior {
    public static CardViewBehavior mCardViewBehavior;
    private CardViewBehaviorInterface mBehavior;
    private final RecyclerView mRecyclerView;
    private final StatusSingleton mStatus;
    private final Activity mActivity;
    private String TAG = "CardViewBehavior";

    public static CardViewBehavior getInstance(int indexBeahvior, RecyclerView recyclerView, Activity activity) {
        return mCardViewBehavior == null ?
                mCardViewBehavior = new CardViewBehavior(indexBeahvior, recyclerView, activity) :
                mCardViewBehavior;
    }

    private CardViewBehavior(int indexBeahvior, RecyclerView recyclerView, Activity activity) {
        mRecyclerView = recyclerView;
        mStatus = StatusSingleton.getInstance();
        mActivity = activity;
        setBehavior(indexBeahvior);
    }

    /**
     *
     * @param indexBeahvior
     */
    public void setBehavior(int indexBeahvior) {
        mBehavior = indexBeahvior == 0 ?
                new SelectAndDisappearBehavior(mRecyclerView, mActivity) : new AppearOverBehavior(mRecyclerView, mActivity);
    }

    /**
     *
     * @param position
     */
    public void expand(int position) {
        mBehavior.expand(position);
    }

    /**
     *
     */
    public void collapse() {
        mBehavior.collapse();
    }


}
