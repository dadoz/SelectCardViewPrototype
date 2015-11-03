package com.application.sample.selectcardviewprototype.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.model.ShoppingItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davide on 04/09/15.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ShoppingItemViewHolder> {
    private final List<ShoppingItem> shoppingItemList;
    private final WeakReference<OnItemSelectedListenerCustom> listener;


    public RecyclerviewAdapter(ArrayList<ShoppingItem> list,
                               WeakReference<OnItemSelectedListenerCustom> listener) {

        shoppingItemList = list;
        this.listener = listener;
    }

    @Override
    public ShoppingItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item_row, parent, false);
        ShoppingItemViewHolder vh = new ShoppingItemViewHolder(view, listener);
        view.setOnClickListener(vh);
        return vh;
    }

    @Override
    public void onBindViewHolder(ShoppingItemViewHolder vh, int position) {
        vh.bindTo(shoppingItemList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return (null != shoppingItemList ? shoppingItemList.size() : 0);
    }

    public List<ShoppingItem> getAllItems() {
        return shoppingItemList;
    }

    /**
     *
     */
    public static class ShoppingItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final TextView descriptionTextView;
        private TextView textView;
        private final WeakReference<OnItemSelectedListenerCustom> listener;
        private int currentPosition;

        public ShoppingItemViewHolder(View view, WeakReference<OnItemSelectedListenerCustom> listener) {
            super(view);
//            this.imageView = (ImageView) view.findViewById(R.id.thumbnailImageViewId);
            this.textView = (TextView) view.findViewById(R.id.nameTextViewId);
            this.descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextViewId);

            this.listener = listener;
        }

        public void bindTo(ShoppingItem item, int currentPosition) {
            this.currentPosition = currentPosition;
            textView.setText(item.getName());
            descriptionTextView.setText(item.getDescription());
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.get().onItemClicked(currentPosition);
            }
        }
    }

    /**
     *
     * @param pos
     */
    public void leaveSelectedItem(int pos) {
        ShoppingItem itemTemp = shoppingItemList.get(pos);
        shoppingItemList.clear();
        shoppingItemList.add(itemTemp);
        notifyDataSetChanged();
    }

    /**
     *
     * @param items
     */
    public void addAllItem(ArrayList<ShoppingItem> items) {
        shoppingItemList.clear();
        notifyDataSetChanged();
        shoppingItemList.addAll(items);
        notifyItemRangeInserted(0, items.size());
    }

    public interface OnItemSelectedListenerCustom {
        void onItemClicked(int selectedPosition);
    }

}