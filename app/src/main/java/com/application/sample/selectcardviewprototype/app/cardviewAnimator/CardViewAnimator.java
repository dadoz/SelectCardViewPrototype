package com.application.sample.selectcardviewprototype.app.cardviewAnimator;

/**
 * Created by davide on 10/09/15.
 */
public class CardViewAnimator {
    public static CardViewAnimator cardViewStrategyRef;
    private CardViewAnimatorStrategyInterface strategyRef;

    public enum CardviewAnimatorStrategyEnum { SELECT_AND_DISAPPEAR, APPEAR_OVER, EXPAND_IN_LIST }

    public static CardViewAnimator getInstance() {
        return cardViewStrategyRef == null ?
                cardViewStrategyRef = new CardViewAnimator() :
                cardViewStrategyRef;
    }

    private CardViewAnimator() {
    }

    public void setStrategy(CardViewAnimatorStrategyInterface ref) {
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
