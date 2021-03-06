package com.exmper.administrator.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2019/1/23.
 */
public class MyAdapter extends BaseAdapter{
    private Context mContext;
    private List<User> mDatas;
    private LayoutInflater layoutInflater;
    
    private ViewHolder mViewHolder;

    public MyAdapter(Context mContext, List<User> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public User  getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        ViewHolder holder = null;
        
        if(converView == null){
           View view = layoutInflater.inflate(R.layout.custom_item1,parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView)view .findViewById(R.id.tv1)
            convertView.setTag(holder);//将Holder存储到convertView中
        }else{
            
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(mData.get(position).getmName())
        //TextView textView = (TextView) view.findViewById(R.id.tv1);
        //User user =getItem(position);
       // textView.setText(user.getmName());
        //mViewHolder.textView.setText(user.getmName());

        return convertView;
    }
}

static class ViewHolder{
    TextView textView;
    
}
