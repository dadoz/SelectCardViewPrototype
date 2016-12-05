package com.application.sample.selectcardviewprototype.app.cardviewAnimator;

public class CardViewAnimator {
    public static CardViewAnimator cardViewStrategyRef;
    private CardViewAnimatorStrategyInterface strategyRef;

    public static CardViewAnimator getInstance() {
        return cardViewStrategyRef == null ?
                cardViewStrategyRef = new CardViewAnimator() :
                cardViewStrategyRef;
    }

    private CardViewAnimator() {
    }

    /**
     *
     * @param ref
     */
    public void setStrategy(CardViewAnimatorStrategyInterface ref) {
        strategyRef = ref;
    }

    /**
     * expand animation
     * @param position
     */
    public void expand(int position) {
        strategyRef.expand(position);
    }

    /**
     * collapse animation
     */
    public void collapse() {
        strategyRef.collapse();
    }


}
