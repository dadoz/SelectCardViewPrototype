package com.application.sample.selectcardviewprototype.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.adapter.RecyclerviewAdapter;
import com.application.sample.selectcardviewprototype.app.strategies.CardViewStrategy;
import com.application.sample.selectcardviewprototype.app.strategies.behaviors.AppearOverAndExpandBehavior;
import com.application.sample.selectcardviewprototype.app.model.ShoppingItem;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.SELECTED;


/**
     * A placeholder fragment containing a simple view.
     */
public class ShoppingListFragment extends Fragment
        implements OnRestoreRecyclerViewInterface, RecyclerviewAdapter.OnItemSelectedListenerCustom {
    @Bind(R.id.recyclerViewId)
    RecyclerView mRecyclerView;
    @Bind(R.id.overlayViewId)
    FrameLayout mOverlayView;

    private CardViewStrategy cardBehavior;
    private StatusSingleton mStatus;


    public ShoppingListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mStatus = StatusSingleton.getInstance();
        onInitView();
        return view;
    }

    /**
     *
     */
    public void onInitView() {
        initRecyclerView(getData());
        setCardviewBehavior();
    }

    /**
     *
     * @param shoppingItems
     */
    private void initRecyclerView(ArrayList<ShoppingItem> shoppingItems) {
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(shoppingItems,
                new WeakReference<RecyclerviewAdapter.OnItemSelectedListenerCustom>(this));
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(adapter);
    }

    /**
     *
     */
    private void setCardviewBehavior() {
        setCardviewBehavior(false);
    }

    /**
     *
     * @param isToggling
     */
    private void setCardviewBehavior(boolean isToggling) {
        cardBehavior = CardViewStrategy.getInstance();
        cardBehavior.setStrategy(new AppearOverAndExpandBehavior(mRecyclerView,
                new WeakReference<Activity>(getActivity()), mOverlayView));
    }

    /**
     *
     * @return
     */
    public ArrayList<ShoppingItem> getData() {
        ArrayList<ShoppingItem> list = new ArrayList<ShoppingItem>();
        list.add(new ShoppingItem("id0", null, "Flour", "sample description"));
        list.add(new ShoppingItem("id1", null, "Soap", "sample description 2"));
        list.add(new ShoppingItem("id2", null, "Eggs", "sample description 3"));
        list.add(new ShoppingItem("id3", null, "Onions", "sample description 4"));
        list.add(new ShoppingItem("id3", null, "Potatoes", "sample description 5"));
        list.add(new ShoppingItem("id3", null, "Bacon", "sample description 6"));
        list.add(new ShoppingItem("id3", null, "Tomato Sauce", "sample description 7"));
        return list;
    }

    @Override
    public void onRestoreRecyclerView() {
        cardBehavior.collapse();
    }

    @Override
    public void destroyBehavior() {
    }

    @Override
    public void onItemClicked(int selectedPosition) {
        if (mStatus.getStatus() != SELECTED) {
            cardBehavior.expand(selectedPosition);
        }
    }
}
