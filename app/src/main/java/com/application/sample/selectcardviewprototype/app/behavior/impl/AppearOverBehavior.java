package com.application.sample.selectcardviewprototype.app.behavior.impl;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.adapter.RecyclerviewAdapter;
import com.application.sample.selectcardviewprototype.app.behavior.CardViewStrategyInterface;
import com.application.sample.selectcardviewprototype.app.model.ShoppingItem;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;


import java.lang.ref.WeakReference;

import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.NOT_SET;
import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.SELECTED;

/**
 * Created by davide on 14/09/15.
 */
public class AppearOverBehavior implements CardViewStrategyInterface {
//    private static final int ANIMATION_DURATION_TIME = android.R.integer.config_mediumAnimTime;
//    private static final long ANIMATION_DELAY_TIME = 150;
    private final WeakReference<Activity> activity;
    private final RecyclerView recyclerView;
    private final StatusSingleton status;
    private final FrameLayout frameLayout;
    private int marginTop;
    private View selectedView;
//    private int viewInitialHeight;

    public AppearOverBehavior(RecyclerView recyclerView, WeakReference<Activity> activity, FrameLayout frameLayout) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.frameLayout = frameLayout;
        this.status = StatusSingleton.getInstance();
    }

    /**
     *
     * @param selecting
     */
//    private void colorize(boolean selecting) {
//        int color = activity.get().getResources().getColor(selecting ? R.color.material_blue_grey_950 :
//                R.color.cardview_light_background);
//        if (selectedView != null) {
//            selectedView.setBackgroundColor(color);
//        }
//    }

//    /**
//     *
//     * @param isDown
//     */
//    public void animate(View view, boolean isDown) {
//        view.clearAnimation();
//        ExpandCardViewAnimation animation = getAnimation(view, isDown);
//        view.setAnimation(animation);
//        view.animate();
//        view.invalidate();
//    }
//
//    /**
//     *
//     * @param isDown
//     * @return
//     */
//    public ExpandCardViewAnimation getAnimation(View view, boolean isDown) {
//        //0 - initialHeight
//        //1 - finalHeight
//        //2 - marginTop
//        int [] animParams = initAnimParams(view, isDown);
//        ExpandCardViewAnimation animation = new ExpandCardViewAnimation(view, animParams[0], animParams[1], animParams[2], isDown);
//        animation.setInterpolator(new AccelerateInterpolator());
//        animation.setDuration(activity.get().getResources().getInteger(ANIMATION_DURATION_TIME));
//        animation.setAnimationListener(new AnimationListenerCustom(this, isDown));
//        return animation;
//    }
//
//    /**
//     * params
//     *
//     * 0 - initialHeight
//     * 1 - finalHeight
//     * 2 - marginTop
//     *
//     * @param view
//     * @param isDown
//     * @return
//     */
//    private int[] initAnimParams(View view, boolean isDown) {
//        if (isDown) {
//            viewInitialHeight = view.getHeight();
//            return new int [] {
//                    view.getHeight(),
//                    recyclerView.getHeight(),
//                    getMarginTop()
//            };
//        }
//
//        //else get back
//        return new int [] {
//                recyclerView.getHeight(),
//                viewInitialHeight,
//                getMarginTop()
//        };
//    }

    /**
     * callback to be called on animation ends
     * @param isDown
     */
    public void onFinishAnimationCallback(boolean isDown) {
        if (! isDown) {
            showOverLayout(false);
//            fadeToShowRecyclerView(true);
        }
//        ((View) frameLayout.getParent()).setBackgroundColor(false ? activity.getResources().getColor(R.color.material_deep_teal_500) : Color.WHITE);
    }

    /**
     *
     */
//    private void fadeToShowRecyclerView(boolean isShowing) {
//        float initAlpha = isShowing ? 1 : 0;
//        float finalAlpha = isShowing ? 0 : 1;
//        int duration = activity.get().getResources().getInteger(ANIMATION_DURATION_TIME);
//        AlphaAnimation animation = new AlphaAnimation(initAlpha, finalAlpha);
//        animation.setDuration(duration);
//        recyclerView.setAnimation(animation);
//        recyclerView.animate();
//    }

    @Override
    public void expand(int position) {
        status.setStatus(SELECTED);
//        fadeToShowRecyclerView(false);
        selectedView = recyclerView.getLayoutManager().findViewByPosition(position);
        marginTop = getMarginTop();
        ShoppingItem selectedItem = getSelectedItem(position);
        View cardView = initOverLayout(selectedItem);
//        animatePostDelayed(cardView);
//        animate(cardView, true);
    }

//    /**
//     * @// TODO: 13/10/15 refactor do not compile with this
//     * @deprecated
//     * @param cardView
//     */
//    private void animatePostDelayed(final View cardView) {
//        Handler hd = new Handler();
//        hd.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                animate(cardView, true);
//            }
//        }, ANIMATION_DELAY_TIME);
//    }

    /**
     *
     * @param selectedItem
     */
    private View initOverLayout(ShoppingItem selectedItem) {
        frameLayout.setBackgroundColor(Color.TRANSPARENT);
        showOverLayout(true);
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
        ((TextView) view.findViewById(R.id.nameTextViewId)).setText(selectedItem.getName());
    }

    /**
     *
     * @param isShowing
     */
    private void showOverLayout(boolean isShowing) {
        frameLayout.setVisibility(isShowing ? View.VISIBLE : View.GONE);
        if (! isShowing) {
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

    @Override
    public void collapse() {
        status.setStatus(NOT_SET);
        View cardView = getInflatedCardView();
//        animate(cardView, false);
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
//        int x = positionArray[0];
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
        int rvX = positionArray[0];
        return rvY;
    }

    /**
     *
     * @return
     */
//    public int getActionBarHeight() {
//        return (int) activity.get().getTheme()
//                .obtainStyledAttributes(new int[]{android.R.attr.actionBarSize})
//                .getDimension(0, 0);
//    }

    public static class AnimationListenerCustom implements Animation.AnimationListener {
        private final AppearOverBehavior callback;
        private final boolean isDown;

        AnimationListenerCustom(AppearOverBehavior callback, boolean down) {
            this.isDown = down;
            this.callback = callback;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            callback.onFinishAnimationCallback(isDown);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }
}
