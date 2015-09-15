package com.application.sample.selectcardviewprototype.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.adapter.RecyclerviewAdapter;
import com.application.sample.selectcardviewprototype.app.behavior.CardViewBehavior;
import com.application.sample.selectcardviewprototype.app.model.ShoppingItem;

import java.util.ArrayList;



/**
     * A placeholder fragment containing a simple view.
     */
public class PlaceholderFragment extends Fragment
        implements View.OnClickListener, OnRestoreRecyclerViewInterface, CompoundButton.OnCheckedChangeListener {
    private View mRootView;
    @Bind(R.id.recyclerViewId)
    RecyclerView mRecyclerView;
    @Bind(R.id.behaviorSwitchCompatId)
    SwitchCompat mBehaviorSwitch;
    @Bind(R.id.titleBehaviorId)
    TextView titleBehaviorTextview;
    private String TAG = "PlaceholderFragment";
    private CardViewBehavior mCardBehavior;
    private View mV;

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, mRootView);
        onInitView();
        return mRootView;
    }

    /**
     *
     */
    public void onInitView() {
        ArrayList<ShoppingItem> shoppingItems = getData();

        RecyclerviewAdapter adapter = new RecyclerviewAdapter(getActivity(), shoppingItems, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        DefaultItemAnimator animator = new DefaultItemAnimator();
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setAdapter(adapter);

        mCardBehavior = CardViewBehavior.getInstance(0, mRecyclerView, getActivity());
        mBehaviorSwitch.setOnCheckedChangeListener(this);
    }

    /**
     *
     * @return
     */
    public ArrayList<ShoppingItem> getData() {
        ArrayList<ShoppingItem> list = new ArrayList<ShoppingItem>();
        list.add(new ShoppingItem("id0", null, "sample1"));
        list.add(new ShoppingItem("id1", null, "sample2"));
        list.add(new ShoppingItem("id2", null, "sample3"));
        list.add(new ShoppingItem("id3", null, "sample4"));
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainViewId:
                int position = mRecyclerView.getLayoutManager().getPosition(v);
                mCardBehavior.expand(position);
                Log.e(TAG, "hey clicking");
                break;
        }
    }


    @Override
    public void onRestoreRecyclerView() {
        ArrayList<ShoppingItem> shoppingItems = getData();
        ((RecyclerviewAdapter) mRecyclerView.getAdapter()).addAllItem(shoppingItems);
        mCardBehavior.collapse();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean toggling) {
        mCardBehavior.collapse();
        titleBehaviorTextview.setText(toggling ? "SelectAndDisappear" : "AppearOver");
        mCardBehavior.setBehavior(toggling ? 0 : 1);
    }
}
