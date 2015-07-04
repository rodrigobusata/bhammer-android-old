package com.busata.bhammer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.busata.bhammer.views.BViewHolder;
import com.busata.bhammer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BSimpleAdapter extends RecyclerView.Adapter<BSimpleAdapter.BSimpleViewHolder> {

    private List<String> mItems;
    private LayoutInflater mLayoutInflater;
    private int mResource;

    private boolean mWithDivider = true;

    public BSimpleAdapter(Context context, List<String> items) {
        mItems = items;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = R.layout.simple_list_item;
    }

    public BSimpleAdapter(Context context, String[] items) {
        mItems = new ArrayList<>(Arrays.asList(items));
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = R.layout.simple_list_item;
    }

    public BSimpleAdapter(Context context, int resource, List<String> items) {
        mItems = items;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = resource;
    }

    public BSimpleAdapter(Context context, int resource, String[] items) {
        mItems = new ArrayList<>(Arrays.asList(items));
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = resource;
    }

    @Override
    public BSimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new BSimpleViewHolder(mLayoutInflater.inflate(mResource, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(BSimpleViewHolder holder, int position) {
        if (mItems.get(position) != null) {
            holder.mText.setText(mItems.get(position));
        }

        if (mWithDivider) holder.mDivider.setVisibility(View.VISIBLE);
        else holder.mDivider.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addListItem(String item, int position) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public boolean isWithDivider() {
        return mWithDivider;
    }

    public void setWithDivider(boolean withDivider) {
        this.mWithDivider = withDivider;
    }

    public class BSimpleViewHolder extends BViewHolder {
        public TextView mText;
        public View mDivider;

        public BSimpleViewHolder(View itemView) {
            super(itemView);

            mText = (TextView) itemView.findViewById(R.id.text1);
            mDivider = itemView.findViewById(R.id.divider);

            itemView.setOnClickListener(this);
        }
    }
}