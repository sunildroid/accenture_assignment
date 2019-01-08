package com.example.sunilkuntal.myalbum.ui.feature.home;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sunilkuntal.myalbum.R;
import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class MyAlbumAdapter extends RecyclerView.Adapter<MyAlbumAdapter.AlbumViewHolder> {

    List<AlbumEntity> mItems = new ArrayList<>();
    public final String STR_TEMPLATE_ID = "ID:\t %s";
    public final String STR_TEMPLATE_USER_ID = "User ID:\t\t%s";
    public final String STR_TEMPLATE_TITLE = "Title:\t\t%s";
    private ListItemClickable mListItemClickable;

    public MyAlbumAdapter(ListItemClickable mListItemClickable) {
        this.mListItemClickable = mListItemClickable;
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        final AlbumEntity model = mItems.get(position);
        holder.tvID.setText(String.format(STR_TEMPLATE_ID, model.getId()));
        holder.tvUserID.setText(String.format(STR_TEMPLATE_USER_ID, model.getUserId()));
        holder.tvTitle.setText(String.format(STR_TEMPLATE_TITLE, model.getTitle()));
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new AlbumViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album, parent, false));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<AlbumEntity> items) {
        this.mItems.clear();
        Collections.sort(items);
        this.mItems.addAll(items);
        notifyDataSetChanged();

    }


    class AlbumViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView tvUserID;
        TextView tvID;
        TextView tvTitle;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            tvUserID = itemView.findViewById(R.id.tvUserID);
            tvID = itemView.findViewById(R.id.tvID);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          mListItemClickable.handleListItemClick(mItems.get(getAdapterPosition()).getId());
        }
    }
}


