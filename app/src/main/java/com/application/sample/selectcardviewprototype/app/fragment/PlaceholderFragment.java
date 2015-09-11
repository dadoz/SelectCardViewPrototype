package com.application.sample.selectcardviewprototype.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class PlaceholderFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    @Bind(R.id.recyclerViewId)
    RecyclerView mRecyclerView;
    private String TAG = "PlaceholderFragment";

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
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(adapter);
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
                int position = mRecyclerView.getAdapter();
                CardViewBehavior behavior = new CardViewBehavior(0, mRecyclerView, position);
                behavior.prepareToShow();
                behavior.show();
                Log.e(TAG, "hey clicking");
                break;
        }
    }


}
