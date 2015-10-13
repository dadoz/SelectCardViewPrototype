package com.application.sample.selectcardviewprototype.app.behavior;

/**
 * Created by davide on 10/09/15.
 */
public class CardViewStrategy {
    public static CardViewStrategy mCardViewBehavior;
    private CardViewStrategyInterface mBehavior;

    public enum CardViewBehaviorEnum { SELECT_AND_DISAPPEAR, APPEAR_OVER, EXPAND_IN_LIST }

    public static CardViewStrategy getInstance() {
        return mCardViewBehavior == null ?
s                mCardViewBehavior = new CardViewStrategy() :
                mCardViewBehavior;
    }

    private CardViewStrategy() {
    }

    public void setBehavior(CardViewStrategyInterface cardViewStrategy) {
        mBehavior = cardViewStrategy;
    }

    /**
     * @TODO not working at all (on restore applications)
     * @param behaviorId
     */
//    public void setBehavior(CardViewBehaviorEnum behaviorId) {
//        if (behaviorId == SELECT_AND_DISAPPEAR) {
//            mBehavior = new SelectAndDisappearBehavior(mRecyclerView, mActivity);
//        } else if (behaviorId == EXPAND_IN_LIST) {
//            mBehavior = new ExpandInListBehavior(mRecyclerView, mActivity);
//        } else if (behaviorId == APPEAR_OVER) {
//            mBehavior = new AppearOverBehavior(mRecyclerView, mActivity, frameLayout);
//        }
//    }

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
