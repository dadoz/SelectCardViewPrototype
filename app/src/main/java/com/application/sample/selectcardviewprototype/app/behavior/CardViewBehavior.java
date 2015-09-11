package com.application.sample.selectcardviewprototype.app.behavior;

import android.support.v7.widget.RecyclerView;

/**
 * Created by davide on 10/09/15.
 */
public class CardViewBehavior {
    private final int mBehavior;
    private final RecyclerView mRecyclerView;

    public CardViewBehavior(int i, RecyclerView recyclerView, int position) {
        mBehavior = i;
        mRecyclerView = recyclerView;
    }

    //move in behavior class
    public void prepareToShow() {
        mRecyclerView.getAdapter().notifyDataSetChanged();

    }

    public void show() {

    }

}
