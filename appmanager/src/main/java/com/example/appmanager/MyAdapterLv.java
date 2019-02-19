package com.example.appmanager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class MyAdapterLv extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<APP> mMyData;

    public MyAdapterLv(Context context, List<APP> myData) {
        mContext = context;
        mMyData = myData;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override

    public int getCount() {
        return mMyData.size();
    }

    @Override
    public APP getItem(int position) {
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
            convertView = mLayoutInflater.inflate(R.layout.custom_item_lv, parent, false);
           viewHolder = new ViewHolder();
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.iv_imageview);
            viewHolder.mTextView1  = (TextView) convertView.findViewById(R.id.tv_textview_1);
            viewHolder.mTextView2 = (TextView) convertView.findViewById(R.id.tv_textview_2);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
           // Log.i("xxx",""+position);  //有缓存就 直接不渲染
           // view = convertView;
        }
        APP app = getItem(position);
        viewHolder.mImageView.setImageDrawable(app.getIcon());
        viewHolder.mTextView1.setText(app.getAppName());
        viewHolder.mTextView2.setText(app.getPackageName());



        return  convertView;
    }

    class ViewHolder{
        private ImageView mImageView;
        private TextView mTextView1;
        private TextView mTextView2;


    }
}
