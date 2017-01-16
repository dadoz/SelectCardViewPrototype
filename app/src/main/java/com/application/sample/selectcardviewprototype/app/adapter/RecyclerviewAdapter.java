package com.application.sample.selectcardviewprototype.app.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.model.ContactItem;
import com.application.sample.selectcardviewprototype.app.singleton.PicassoSingleton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ShoppingItemViewHolder> {
    private final List<ContactItem> itemList;
    private final WeakReference<OnItemSelectedListenerCustom> listener;
    private final WeakReference<Context> context;
    private final WeakReference<PicassoSingleton.PicassoCallbacksInterface> picassoListener;


    public RecyclerviewAdapter(ArrayList<ContactItem> list,
                               WeakReference<OnItemSelectedListenerCustom> listener,
                               WeakReference<PicassoSingleton.PicassoCallbacksInterface> listener2,
                               WeakReference<Context> ctx) {
        this.itemList = list;
        this.listener = listener;
        this.picassoListener = listener2;
        this.context = ctx;
    }

    @Override
    public ShoppingItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_row, parent, false);
        ShoppingItemViewHolder vh = new ShoppingItemViewHolder(view, listener, picassoListener);
        view.setOnClickListener(vh);
        return vh;
    }

    @Override
    public void onBindViewHolder(ShoppingItemViewHolder vh, int position) {
        vh.bindTo(context, itemList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }

    public List<ContactItem> getAllItems() {
        return itemList;
    }

    /**
     * View Holder
     */
    public static class ShoppingItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final TextView nameTextView;
        private final ImageView thumbnailImageView;
        private TextView surnameTextView;
        private final WeakReference<OnItemSelectedListenerCustom> listener;
        private int currentPosition;
        private WeakReference<PicassoSingleton.PicassoCallbacksInterface> picassoListener;

        public ShoppingItemViewHolder(View view,
                                      WeakReference<OnItemSelectedListenerCustom> listener,
                                      WeakReference<PicassoSingleton.PicassoCallbacksInterface> listener2) {
            super(view);
            this.nameTextView = (TextView) view.findViewById(R.id.nameTextViewId);
            this.surnameTextView = (TextView) view.findViewById(R.id.surnameTextViewId);
            this.thumbnailImageView = (ImageView) view.findViewById(R.id.thumbnailImageViewId);
            this.listener = listener;
            this.picassoListener = listener2;
        }

        /**
         *
         * @param context
         * @param item
         * @param currentPosition
         */
        public void bindTo(WeakReference<Context> context, ContactItem item, int currentPosition) {
            this.currentPosition = currentPosition;
            nameTextView.setText(item.getName());
            surnameTextView.setText(item.getSurname());
            PicassoSingleton.getInstance(context, picassoListener)
                    .setProfilePictureAsync(thumbnailImageView, item.getThumbnail(),
                            ContextCompat.getDrawable(context.get(), R.drawable.ic_person_pin_circle_black_48dp));
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.get().onItemClicked(currentPosition);
            }
        }
    }

    /**
     * interface to handle onItemClicked
     */
    public interface OnItemSelectedListenerCustom {
        void onItemClicked(int selectedPosition);
    }

}