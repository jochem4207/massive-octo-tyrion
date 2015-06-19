package com.jdkmedia.vh8.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdkmedia.vh8.domain.Player;
import com.jdkmedia.vh8.R;

import java.util.List;

/**
 * Created by jochem on 09-05-15.
 */

//TODO: IMPLEMENT SOMETHING to refresh list
public class PlayerListAdapter extends BaseAdapter {

    private List<Player> mList;
    private Activity mContext;
    private LayoutInflater mLayoutInflater = null;


    public PlayerListAdapter(Activity context, List<Player> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    public List<Player> getData() {
        return mList;
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    @Override
    public Object getItem(int pos) {
        return mList.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Viewholder for smoothher scroll
        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.fragment_player_search_listview, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.tvBigTitle = (TextView) convertView.findViewById(R.id.firstLine);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Player item = (Player) getItem(position);

        //viewHolder.mTVItem.setText(mList.get(position));
        viewHolder.tvBigTitle.setText(item.getNickName());
        return convertView;
    }

    private static class ViewHolder implements View.OnClickListener {
        TextView tvBigTitle;

        @Override
        public void onClick(View v) {
            Log.i("TAG", "HOI");

        }
    }
}