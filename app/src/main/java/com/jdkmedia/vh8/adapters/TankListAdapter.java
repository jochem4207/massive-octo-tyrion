package com.jdkmedia.vh8.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdkmedia.vh8.domain.Player;
import com.jdkmedia.vh8.R;
import com.jdkmedia.vh8.domain.Tank;

import java.util.List;

/**
 * Created by jochem on 09-05-15.
 */

public class TankListAdapter extends BaseAdapter {

    private List<Tank> mList;
    private Activity mContext;
    private LayoutInflater mLayoutInflater = null;


    public TankListAdapter(Activity context, List<Tank> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public List<Tank> getData() {
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
            convertView = li.inflate(R.layout.fragment_tank_list, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.tvTankName = (TextView) convertView.findViewById(R.id.tankFirstLine);
            viewHolder.tvTankImage = (ImageView) convertView.findViewById(R.id.tankIcon);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Tank item = (Tank) getItem(position);

        viewHolder.tvTankName.setText(item.getName());
        //viewHolder.tvTankImage.setImageDrawable(item.getImage());
        return convertView;
    }

    private static class ViewHolder implements View.OnClickListener {
        TextView tvTankName;
        ImageView tvTankImage;

        @Override
        public void onClick(View v) {

        }
    }
}