package com.application.sample.selectcardviewprototype.app.fragment;

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
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.adapter.RecyclerviewAdapter;
import com.application.sample.selectcardviewprototype.app.behavior.CardViewStrategy;
import com.application.sample.selectcardviewprototype.app.behavior.impl.AppearOverBehavior;
import com.application.sample.selectcardviewprototype.app.model.ShoppingItem;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.application.sample.selectcardviewprototype.app.behavior.CardViewStrategy.CardViewBehaviorEnum.APPEAR_OVER;
import static com.application.sample.selectcardviewprototype.app.behavior.CardViewStrategy.CardViewBehaviorEnum.SELECT_AND_DISAPPEAR;
import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.SELECTED;


/**
     * A placeholder fragment containing a simple view.
     */
public class PlaceholderFragment extends Fragment
        implements OnRestoreRecyclerViewInterface,
        CompoundButton.OnCheckedChangeListener, RecyclerviewAdapter.OnLmnItemSelectedListener {
    private View mRootView;
    @Bind(R.id.recyclerViewId)
    RecyclerView mRecyclerView;
    @Bind(R.id.behaviorSwitchCompatId)
    SwitchCompat mBehaviorSwitch;
    @Bind(R.id.titleBehaviorId)
    TextView titleBehaviorTextview;
    @Bind(R.id.overlayViewId)
    FrameLayout mOverlayView;

    private String TAG = "PlaceholderFragment";
    private CardViewStrategy cardBehavior;
    private StatusSingleton mStatus;


    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, mRootView);
        mStatus = StatusSingleton.getInstance();
        onInitView();
        return mRootView;
    }

    /**
     *
     */
    public void onInitView() {
        initRecyclerView(getData());
        setCardviewBehavior();
        mBehaviorSwitch.setOnCheckedChangeListener(this);
    }

    /**
     *
     * @param shoppingItems
     */
    private void initRecyclerView(ArrayList<ShoppingItem> shoppingItems) {
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(shoppingItems,
                new WeakReference<RecyclerviewAdapter.OnLmnItemSelectedListener>(this));
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
        cardBehavior.setBehavior(new AppearOverBehavior(mRecyclerView, getActivity(), mOverlayView));
//        cardBehavior.setBehavior(isToggling ? SELECT_AND_DISAPPEAR : APPEAR_OVER);
        titleBehaviorTextview.setText(isToggling ? "SelectAndDisappear" : "AppearOver");
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
        list.add(new ShoppingItem("id3", null, "sample5"));
        list.add(new ShoppingItem("id3", null, "sample6"));
        list.add(new ShoppingItem("id3", null, "sample7"));
        return list;
    }

    @Override
    public void onRestoreRecyclerView() {
        cardBehavior.collapse();
    }

    @Override
    public void destroyBehavior() {
//        cardBehavior = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean toggling) {
        if (mStatus.getStatus() == SELECTED) {
            cardBehavior.collapse();
        }
        setCardviewBehavior(toggling);
    }


    @Override
    public void onItemClicked(int selectedPosition) {
        if (mStatus.getStatus() != SELECTED) {
            cardBehavior.expand(selectedPosition);
        }
    }
}
