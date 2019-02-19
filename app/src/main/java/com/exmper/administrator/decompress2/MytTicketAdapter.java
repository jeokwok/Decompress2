package com.exmper.administrator.decompress2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2019/1/20.
 */

public class MytTicketAdapter extends BaseAdapter {


        private Context mContext;
        private List<String> mDatas;
        private LayoutInflater layoutInflater;

    public MytTicketAdapter(Context mContext, List<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
         View view = layoutInflater.inflate(R.layout.custom_item_4,parent,false);

         TextView textView = (TextView)view.findViewById(R.id.tv_griyview_name);
        textView.setText(mDatas.get(position));

        return view ;
    }
}
