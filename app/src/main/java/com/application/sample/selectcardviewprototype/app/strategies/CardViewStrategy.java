package com.application.sample.selectcardviewprototype.app.strategies;

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
