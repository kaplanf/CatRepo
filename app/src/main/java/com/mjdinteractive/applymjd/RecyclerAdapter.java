package com.mjdinteractive.applymjd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjdinteractive.applymjd.api.Cat;
import com.mjdinteractive.applymjd.api.CatList;
import com.squareup.picasso.Picasso;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Cat[] mCats;
    private Context mContext;

    public RecyclerAdapter(Context context, CatList cats) {
        mCats = cats.getCats();
        mContext = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView catName;


        ViewHolder(View itemView) {
            super(itemView);
            catName = (TextView) itemView.findViewById(R.id.cat_name);
            //attach captions and photos here

        }
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View contactView = inflater.inflate(R.layout.view_cat, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int position) {
        Cat kitten = mCats[position];

        viewHolder.catName.setText(kitten.getName());

        //bind captions and photos here

    }

    @Override
    public int getItemCount() {
        return mCats.length;
    }

}
