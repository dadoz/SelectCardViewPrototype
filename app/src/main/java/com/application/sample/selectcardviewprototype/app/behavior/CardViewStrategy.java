package com.application.sample.selectcardviewprototype.app.behavior;

/**
 * Created by davide on 10/09/15.
 */
public class CardViewStrategy {
    public static CardViewStrategy cardViewStrategyRef;
    private CardViewStrategyInterface strategyRef;

    public enum CardViewBehaviorEnum { SELECT_AND_DISAPPEAR, APPEAR_OVER, EXPAND_IN_LIST }

    public static CardViewStrategy getInstance() {
        return cardViewStrategyRef == null ?
                cardViewStrategyRef = new CardViewStrategy() :
                cardViewStrategyRef;
    }

    private CardViewStrategy() {
    }

    public void setStrategy(CardViewStrategyInterface ref) {
        strategyRef = ref;
    }

    /**
     * @TODO not working at all (on restore applications)
     * @param behaviorId
     */
//    public void setBehavior(CardViewBehaviorEnum behaviorId) {
//        if (behaviorId == SELECT_AND_DISAPPEAR) {
//            strategyRef = new SelectAndDisappearBehavior(mRecyclerView, mActivity);
//        } else if (behaviorId == EXPAND_IN_LIST) {
//            strategyRef = new ExpandInListBehavior(mRecyclerView, mActivity);
//        } else if (behaviorId == APPEAR_OVER) {
//            strategyRef = new AppearOverBehavior(mRecyclerView, mActivity, frameLayout);
//        }
//    }

    /**
     *
     * @param position
     */
    public void expand(int position) {
        strategyRef.expand(position);
    }

    /**
     *
     */
    public void collapse() {
        strategyRef.collapse();
    }


}
