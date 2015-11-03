package com.application.sample.selectcardviewprototype.app.strategies.behaviors;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.adapter.RecyclerviewAdapter;
import com.application.sample.selectcardviewprototype.app.animation.AnimatorBuilder;
import com.application.sample.selectcardviewprototype.app.strategies.CardViewStrategyInterface;
import com.application.sample.selectcardviewprototype.app.model.ShoppingItem;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;

import java.lang.ref.WeakReference;

import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.NOT_SET;
import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.SELECTED;

/**
 * Created by davide on 14/09/15.
 */
public class AppearOverAndExpandBehavior implements CardViewStrategyInterface {
    private final WeakReference<Activity> activity;
    private final RecyclerView recyclerView;
    private final StatusSingleton status;
    private final FrameLayout frameLayout;
    private int marginTop;
    private View selectedView;
    private AnimatorBuilder animatorBuilder;

    public AppearOverAndExpandBehavior(RecyclerView recyclerView,
                                       WeakReference<Activity> activity,
                                       FrameLayout frameLayout) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.frameLayout = frameLayout;
        this.status = StatusSingleton.getInstance();
        this.animatorBuilder = new AnimatorBuilder(new WeakReference<Context>(activity.
                get().getApplicationContext()));
    }

    @Override
    public void expand(int position) {
        status.setStatus(SELECTED);
        selectedView = recyclerView.getLayoutManager().findViewByPosition(position);
        marginTop = getMarginTop();
        ShoppingItem selectedItem = getSelectedItem(position);
        View cardView = initOverLayout(selectedItem);
        initAnimator(cardView, true);
    }

    @Override
    public void collapse() {
        status.setStatus(NOT_SET);
        View cardView = getInflatedCardView();
        initAnimator(cardView, false);
    }

    /**
     * TODO refactor ??
     * @param view
     * @param expanding
     */
    public void initAnimator(final View view, final boolean expanding) {
        AnimatorSet animatorSet = new AnimatorSet();
        Animator alphaAnimator = animatorBuilder.getShowHideAnimator(recyclerView, expanding);
        Animator translationAnimator = animatorBuilder.getTranslationAnimator(view,
                getMarginTop(), expanding);
        animatorSet.playTogether(alphaAnimator, translationAnimator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                showOverLayout(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Animator bottomAnimator = animatorBuilder
                        .getResizeBottomAnimator(view, recyclerView.getHeight(), expanding);
                bottomAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!expanding) {
                            showOverLayout(false);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                bottomAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.start();
    }

    /**
     *
     * @param selectedItem
     */
    private View initOverLayout(ShoppingItem selectedItem) {
        frameLayout.setBackgroundColor(Color.TRANSPARENT);
        View cardView = inflateCardView();
        initCardView(cardView, selectedItem, marginTop);
        return cardView;
    }

    /**
     *
     * @return
     */
    private View inflateCardView() {
        View view = activity.get().getLayoutInflater().inflate(R.layout.shopping_item_row, frameLayout);
        return view.findViewById(R.id.mainViewId);
    }

    /**
     *
     * @return
     * @param selectedItem
     */
    private void initCardView(View view, ShoppingItem selectedItem, int oldMarginTop) {
        CardView.LayoutParams lp = (CardView.LayoutParams) view.getLayoutParams();
        lp.setMargins(0, oldMarginTop, 0, 0);
        view.setLayoutParams(lp);
        ((TextView) view.findViewById(R.id.nameTextViewId))
                .setText(selectedItem.getName());
        ((TextView) view.findViewById(R.id.descriptionTextViewId))
                .setText(selectedItem.getDescription());
    }

    /**
     *
     * @param isShowing
     */
    private void showOverLayout(boolean isShowing) {
        frameLayout.setVisibility(isShowing ? View.VISIBLE : View.GONE);
        if (!isShowing) {
            frameLayout.removeAllViews();
        }
    }

    /**
     * get selected item by pos
     * @param position
     * @return
     */
    private ShoppingItem getSelectedItem(int position) {
        return ((RecyclerviewAdapter) recyclerView.getAdapter())
                .getAllItems().get(position);
    }

    /**
     * get selected item margin top on recycler view
     * @return
     */
    public int getMarginTop() {
        int offset = 0;
        return getSelectedViewPosition() - getRecyclerViewPosition() + offset;
    }

    public View getInflatedCardView() {
        return frameLayout.getChildAt(0);
    }

    /**
     *
     * @return
     */
    public int getSelectedViewPosition() {
        if (selectedView == null) {
            return 0;
        }

        int[] positionArray = new int[2];
        selectedView.getLocationInWindow(positionArray);
        int y = positionArray[1];
        return y;
    }

    /**
     *
     * @return
     */
    public int getRecyclerViewPosition() {
        int[] positionArray = new int[2];
        recyclerView.getLocationInWindow(positionArray);
        int rvY = positionArray[1];
        return rvY;
    }

}
