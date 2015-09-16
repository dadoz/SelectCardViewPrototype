package com.application.sample.selectcardviewprototype.app.behavior;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import com.application.sample.selectcardviewprototype.app.behavior.impl.AppearOverBehavior;
import com.application.sample.selectcardviewprototype.app.behavior.impl.SelectAndDisappearBehavior;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;

import static com.application.sample.selectcardviewprototype.app.behavior.CardViewBehavior.CardViewBehaviorEnum.SELECT_AND_DISAPPEAR;


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
    public enum CardViewBehaviorEnum { SELECT_AND_DISAPPEAR, APPEAR_OVER }

    public static CardViewBehavior getInstance(CardViewBehaviorEnum indexBeahvior, RecyclerView recyclerView, Activity activity) {
        return mCardViewBehavior == null ?
                mCardViewBehavior = new CardViewBehavior(indexBeahvior, recyclerView, activity) :
                mCardViewBehavior;
    }

    private CardViewBehavior(CardViewBehaviorEnum indexBeahvior, RecyclerView recyclerView, Activity activity) {
        mRecyclerView = recyclerView;
        mStatus = StatusSingleton.getInstance();
        mActivity = activity;
        setBehavior(indexBeahvior);
    }

    /**
     *
     * @param indexBeahavior
     */
    public void setBehavior(CardViewBehaviorEnum indexBeahavior) {
        mBehavior = indexBeahavior == SELECT_AND_DISAPPEAR ?
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
