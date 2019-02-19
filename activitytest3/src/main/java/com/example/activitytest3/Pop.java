package com.example.activitytest3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by 60411 on 2019/2/3.
 * 继承PopupWindow 实现弹出popup的操作
 */

public class Pop extends PopupWindow {
    private Context mContext;
    //constranct
    public Pop(Context context) {
        mContext = context;
       LayoutInflater layoutInflater =  LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dig,null,false);
        this.setContentView(view);

        this.setWidth(150);
        this.setHeight(200);
        //设置抢占焦点  点击外部退出  触发点击事件
        setFocusable(true);
        setOutsideTouchable(true);
        //设置popupwindow进出场动画
        //窗体动画

        this.setAnimationStyle(R.style.aa);

    }


}
