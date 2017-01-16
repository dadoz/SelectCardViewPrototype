package com.application.sample.selectcardviewprototype.app.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.SettingsActivity;
import com.application.sample.selectcardviewprototype.app.adapter.RecyclerviewAdapter;
import com.application.sample.selectcardviewprototype.app.singleton.PicassoSingleton;
import com.application.sample.selectcardviewprototype.app.singleton.RetrieveAssetsSingleton;
import com.application.sample.selectcardviewprototype.app.cardviewAnimator.CardViewAnimator;
import com.application.sample.selectcardviewprototype.app.strategies.AppearOverAndExpandStrategy;
import com.application.sample.selectcardviewprototype.app.model.ContactItem;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactListFragment extends Fragment
        implements OnRestoreRecyclerViewInterface, RecyclerviewAdapter.OnItemSelectedListenerCustom,
        PicassoSingleton.PicassoCallbacksInterface {
    @Bind(R.id.recyclerViewId)
    RecyclerView mRecyclerView;
    @Bind(R.id.overlayViewId)
    FrameLayout mOverlayView;

    private CardViewAnimator cardBehavior;
    private StatusSingleton mStatus;
    private RetrieveAssetsSingleton assetsSingleton;


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
        setHasOptionsMenu(true);
        setActionbarTitle();
        initRecyclerView(getData());
        setCardViewAnimator();
    }

    /**
     * init actionbar
     */
    public void setActionbarTitle() {
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(R.string.app_name);
            actionbar.setDisplayHomeAsUpEnabled(false);
            actionbar.setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     *
     * @param shoppingItems
     */
    private void initRecyclerView(ArrayList<ContactItem> shoppingItems) {
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(shoppingItems,
                new WeakReference<RecyclerviewAdapter.OnItemSelectedListenerCustom>(this),
                new WeakReference<PicassoSingleton.PicassoCallbacksInterface>(this),
                new WeakReference<>(getContext()));
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
                new WeakReference<>(getContext()),
                new WeakReference<PicassoSingleton.PicassoCallbacksInterface>(this),
                mOverlayView));
    }

    /**
     *
     * @return
     */
    public ArrayList<ContactItem> getData() {
        try {
            Type listType = new TypeToken<ArrayList<ContactItem>>() {}.getType();
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
        if (!mStatus.isSelected()) {
            mStatus.setPosition(selectedPosition);
            cardBehavior.expand(selectedPosition);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                changeActivity(SettingsActivity.class);
                return true;
            case android.R.id.home:
                if (mStatus.isSelected()) {
                    cardBehavior.collapse();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param activityClass
     */
    private void changeActivity(Class activityClass) {
        startActivity(new Intent(getActivity(), activityClass));
    }

    @Override
    public void onPicassoSuccessCallback() {
    }

    @Override
    public void onPicassoErrorCallback() {
        Log.e("TAG", "error");
    }
}
