package com.exmper.administrator.decompress2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class MyTicketDataAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TicketInfo> mMyData;

    public MyTicketDataAdapter(Context context, List<TicketInfo> myData) {
        mContext = context;
        mMyData = myData;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override

    public int getCount() {
        return mMyData.size();
    }

    @Override
    public TicketInfo getItem(int position) {
        return mMyData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.custom_item_4, parent, false);
           viewHolder = new ViewHolder();
            viewHolder.mFromStation = (TextView) convertView.findViewById(R.id.fromstation);
            viewHolder.mToStation  = (TextView) convertView.findViewById(R.id.tostation);
            viewHolder.mNum = (TextView) convertView.findViewById(R.id.num);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
           // Log.i("xxx",""+position);  //有缓存就 直接不渲染
           // view = convertView;
        }
        //往构造器里填充数据
        TicketInfo ticketInfo = getItem(position);
        viewHolder.mFromStation.setText((CharSequence) ticketInfo.getFromStation());
        viewHolder.mToStation.setText((CharSequence) ticketInfo.getToStation());
        viewHolder.mNum.setText((CharSequence) ticketInfo.getNum());


        return  convertView;
    }

    class ViewHolder{
        private TextView mFromStation;
        private TextView mToStation;
        private TextView mNum;
        private TextView mFromDate;
        private TextView mToDate;
        private TextView mSoftSet;
        private TextView mOneSet;
        private TextView mTwoSet;
        private TextView price;
    }
}
