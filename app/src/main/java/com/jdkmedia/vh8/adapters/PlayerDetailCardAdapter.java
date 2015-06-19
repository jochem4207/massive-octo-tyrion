package com.jdkmedia.vh8.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdkmedia.vh8.MainActivity;
import com.jdkmedia.vh8.R;
import com.jdkmedia.vh8.domain.PlayerDetailCard;

import java.util.ArrayList;

/**
 * Created by jochem on 31-05-15.
 */

//Use this as a adapter for the info blocks
//Block contains image, 2 textfields
public class PlayerDetailCardAdapter extends RecyclerView.Adapter<PlayerDetailCardAdapter.ViewHolder> {
    private static TankItemClickListener itemClickListener;
    private ArrayList<PlayerDetailCard> mDataset;


    public PlayerDetailCardAdapter(ArrayList<PlayerDetailCard> myDataset) {
        mDataset = myDataset;
    }

    public void setOnItemClickListener(TankItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void addItem(PlayerDetailCard data, int index) {
        mDataset.add(index, data);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_player_detail_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mainTitle.setText(mDataset.get(position).getMainTitle());
        holder.subTitle.setText(mDataset.get(position).getSubTitle());
        holder.image.setImageResource(mDataset.get(position).getImageLocation());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface TankItemClickListener {
        void onItemClick(int position, View v);
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView mainTitle;
        TextView subTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mainTitle = (TextView) itemView.findViewById(R.id.detailCardTextViewMain);
            subTitle = (TextView) itemView.findViewById(R.id.detailCardTextViewSub);
            image = (ImageView) itemView.findViewById(R.id.detailCardImageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}