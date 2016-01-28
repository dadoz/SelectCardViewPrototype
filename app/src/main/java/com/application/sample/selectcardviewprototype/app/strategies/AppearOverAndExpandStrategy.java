package com.application.sample.selectcardviewprototype.app.strategies;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.adapter.RecyclerviewAdapter;
import com.application.sample.selectcardviewprototype.app.animator.AnimatorBuilder;
import com.application.sample.selectcardviewprototype.app.cardviewAnimator.CardViewAnimatorStrategyInterface;
import com.application.sample.selectcardviewprototype.app.model.ContactItem;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.NOT_SET;
import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.SELECTED;

/**
 * Created by davide on 14/09/15.
 */
public class AppearOverAndExpandStrategy implements CardViewAnimatorStrategyInterface {
    private static final long MIN_DELAY = 400;
    private final WeakReference<Activity> activity;
    private final RecyclerView recyclerView;
    private final StatusSingleton status;
    private final FrameLayout frameLayout;
    private View selectedView;
    private AnimatorBuilder animatorBuilder;
    private boolean expanding = false;
    private int initialHeight;
    private AnimatorSet animatorSet;
    private Animator translationAnimator;
    private Animator alphaAnimator;
    private Animator bottomAnimator;
    private AnimatorSet animatorSet1;
    private Animator bottomAnimatorContent;
    private AnimatorSet animatorSet2;

    public AppearOverAndExpandStrategy(RecyclerView recyclerView,
                                       WeakReference<Activity> activity,
                                       FrameLayout frameLayout) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.frameLayout = frameLayout;
        this.status = StatusSingleton.getInstance();
        this.animatorBuilder = new AnimatorBuilder(new WeakReference<Context>(activity.get()
                .getApplicationContext()));
    }

    @Override
    public void expand(int position) {
        expanding = true;
        status.setStatus(SELECTED);
        selectedView = recyclerView.getLayoutManager().findViewByPosition(position);
        initialHeight = selectedView.getHeight();
        ContactItem selectedItem = getSelectedItem(position);
        View cardView = initOverLayout(selectedItem);
        initAnimator(cardView, expanding);
    }

    @Override
    public void collapse() {
        expanding = false;
        status.setStatus(NOT_SET);
        View cardView = getInflatedCardView();
        initAnimator(cardView, expanding);
    }

    /**
     * @param view
     * @param expanding
     */
    public void initAnimator(final View view, final boolean expanding) {
        showOverLayout(true);

        animatorSet = new AnimatorSet();
        animatorSet1 = new AnimatorSet();
        animatorSet2 = new AnimatorSet();
        translationAnimator = animatorBuilder.getTranslationAnimator(view,
                getMarginTop(), expanding);
        bottomAnimator = animatorBuilder.getResizeBottomAnimator(view, initialHeight,
                getMarginTop(), recyclerView.getHeight(), expanding);
        bottomAnimatorContent = animatorBuilder
                .getResizeBottomAnimator(view.findViewById(R.id.mainContentViewId), initialHeight,
                        getMarginTop(), recyclerView.getHeight(), expanding);
        alphaAnimator = animatorBuilder.getHideAnimator(recyclerView, expanding);

        buildAnimatorSet(expanding);
        animatorSet1.start();
    }

    /**
     *
     * @param expanding
     */
    private boolean buildAnimatorSet(boolean expanding) {
        return expanding ? onExpanding() : onCollapsing();
    }

    /**
     *
     */
    public boolean onExpanding() {
        animatorSet.playTogether(alphaAnimator, translationAnimator);
        animatorSet2.playTogether(bottomAnimator, bottomAnimatorContent);
        animatorSet1.play(animatorSet).before(animatorSet2);
        animatorSet1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setActionBar(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initCardviewContentAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return true;
    }

    /**
     * @return
     */
    private void initCardviewContentAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(getCardviewContentAnimatorArray());
        animatorSet.start();
    }


    /**
     *
     * @return
     */
    public boolean onCollapsing() {
        alphaAnimator.setStartDelay(MIN_DELAY);
        animatorSet.play(translationAnimator);
        animatorSet1.play(animatorSet).before(bottomAnimator).before(alphaAnimator);
        animatorSet1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setActionBar(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showOverLayout(false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        return true;
    }
    /**
     *
     * @param selectedItem
     */
    private View initOverLayout(ContactItem selectedItem) {
        frameLayout.setBackgroundColor(Color.TRANSPARENT);
        View cardView = inflateCardView();
        initCardView(cardView, selectedItem, getMarginTop());
        return cardView;
    }

    /**
     *
     * @return
     */
    private View inflateCardView() {
        View view = activity.get().getLayoutInflater().inflate(R.layout.shopping_item_row,
                frameLayout);
        updateContentDescription(view);
        return view.findViewById(R.id.mainViewId);
    }

    /**
     *
     * @return
     * @param selectedItem
     */
    private void initCardView(View view, ContactItem selectedItem, int oldMarginTop) {
        CardView.LayoutParams lp = (CardView.LayoutParams) view.getLayoutParams();
        lp.setMargins(0, oldMarginTop, 0, 0);
        view.setLayoutParams(lp);
        ((TextView) view.findViewById(R.id.nameTextViewId))
                .setText(selectedItem.getName());
        ((TextView) view.findViewById(R.id.descriptionTextViewId))
                .setText(selectedItem.getDescription());
        //update description view
        initDescriptionView(view, selectedItem);
    }

    /**
     *
     * @param view
     * @param selectedItem
     */
    private void initDescriptionView(View view, ContactItem selectedItem) {
        setPhoneView((TextView) view.findViewById(R.id.phoneTextId),
                selectedItem.getPhone());
        setEmailView((TextView) view.findViewById(R.id.emailTextId),
                selectedItem.getEmail());
        setPositionView((TextView) view.findViewById(R.id.positionTextId),
                selectedItem.getPosition());
    }
    /**
     *
     * @param view
     * @param phone
     */
    private void setPhoneView(TextView view, String phone) {
        if (phone == null) {
            ((View) view.getParent().getParent()).setVisibility(View.GONE);
            return;
        }

        view.setText(phone);
    }

    /**
     *
     * @param view
     * @param email
     */
    private void setEmailView(TextView view, String email) {
        if (email == null) {
            ((View) view.getParent().getParent()).setVisibility(View.GONE);
            return;
        }

        view.setText(email);
    }

    /**
     *
     * @param view
     * @param position
     */
    private void setPositionView(TextView view, String position) {
        if (position == null) {
            ((View) view.getParent().getParent()).setVisibility(View.GONE);
            return;
        }

        view.setText(position);
    }

    /**
     *
     * @return
     */
    private void updateContentDescription(final View view) {
        setColorFilterToDrawable(((ImageView) view.findViewById(R.id.phoneImageId)),
                R.color.material_green400);
        setColorFilterToDrawable(((ImageView) view.findViewById(R.id.emailImageId)),
                R.color.material_pink400);
        setColorFilterToDrawable(((ImageView) view.findViewById(R.id.positionImageId)),
                R.color.material_bluegrey600);
    }

    /**
     *
     * @param view
     * @param colorId
     */
    private void setColorFilterToDrawable(ImageView view, int colorId) {
        int color = activity.get().getResources().getColor(colorId);
        view.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * get selected item by pos
     * @param position
     * @return
     */
    private ContactItem getSelectedItem(int position) {
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

    /**
     *
     * @return
     */
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
     * set actionBar
     * @param visible
     */
    private void setActionBar(boolean visible) {
        try {
            ((AppCompatActivity) activity.get())
                    .getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public View[] getCardviewDescriptionViewArray() {
        return new View[] {
                getInflatedCardView().findViewById(R.id.phoneLayoutId),
                getInflatedCardView().findViewById(R.id.emailLayoutId),
                getInflatedCardView().findViewById(R.id.positionLayoutId),
        };
    }

    /**
     *
     * @return
     */
    public Animator[] getCardviewContentAnimatorArray() {
        View[] viewArray = getCardviewDescriptionViewArray();
        ArrayList<Animator> alphaAnimatorArrayList = new ArrayList<Animator>();
        for (int i = 0; i < viewArray.length; i++) {
            if (viewArray[i].getVisibility() == View.VISIBLE) {
                alphaAnimatorArrayList.add(animatorBuilder.getHideAnimator(viewArray[i], !expanding));
            }
        }

        Animator[] array = new Animator[alphaAnimatorArrayList.size()];
        alphaAnimatorArrayList.toArray(array);
        return array;
    }
}
