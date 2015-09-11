package com.application.sample.selectcardviewprototype.app.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.model.ShoppingItem;

import java.util.List;

/**
 * Created by davide on 04/09/15.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ShoppingItemViewHolder> {
    private List<ShoppingItem> ShoppingItemList;
    private Context mContext;
    private View.OnClickListener mListnerRef;

    public RecyclerviewAdapter(Context ctx, List<ShoppingItem> list, Fragment fragRef) {
        ShoppingItemList = list;
        mContext = ctx;
        mListnerRef = (View.OnClickListener) fragRef;
    }

    @Override
    public ShoppingItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item_row, parent, false);
        return new ShoppingItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingItemViewHolder vh, int i) {
        ShoppingItem ShoppingItem = ShoppingItemList.get(i);
        //Setting text view title
        vh.textView.setText(ShoppingItem.getName());
        vh.mainView.setOnClickListener(mListnerRef);
    }

    @Override
    public int getItemCount() {
        return (null != ShoppingItemList ? ShoppingItemList.size() : 0);
    }

    public static class ShoppingItemViewHolder extends RecyclerView.ViewHolder {
        private final CardView mainView;
        protected ImageView imageView;
        protected TextView textView;

        public ShoppingItemViewHolder(View view) {
            super(view);
            this.mainView = (CardView) view.findViewById(R.id.mainViewId);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnailImageViewId);
            this.textView = (TextView) view.findViewById(R.id.nameTextViewId);
        }
    }
}