package com.application.sample.selectcardviewprototype.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.adapter.RecyclerviewAdapter;
import com.application.sample.selectcardviewprototype.app.singleton.RetrieveAssetsSingleton;
import com.application.sample.selectcardviewprototype.app.cardviewAnimator.CardViewAnimator;
import com.application.sample.selectcardviewprototype.app.strategies.AppearOverAndExpandStrategy;
import com.application.sample.selectcardviewprototype.app.model.ShoppingItem;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
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

    private CardViewAnimator cardBehavior;
    private StatusSingleton mStatus;
    private RetrieveAssetsSingleton assetsSingleton;


    public ShoppingListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mStatus = StatusSingleton.getInstance();
        assetsSingleton = RetrieveAssetsSingleton.getInstance(new WeakReference<Activity>(getActivity()));
        onInitView();
        return view;
    }

    /**
     * init cardview
     */
    public void onInitView() {
        initRecyclerView(getData());
        setCardViewAnimator();
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
    private void setCardViewAnimator() {
        cardBehavior = CardViewAnimator.getInstance();
        cardBehavior.setStrategy(new AppearOverAndExpandStrategy(mRecyclerView,
                new WeakReference<Activity>(getActivity()), mOverlayView));
    }

    /**
     *
     * @return
     */
    public ArrayList<ShoppingItem> getData() {
        try {
            Type listType = new TypeToken<ArrayList<ShoppingItem>>() {}.getType();
            return new Gson().fromJson(assetsSingleton.getJsonDataFromAssets(),
                    listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
