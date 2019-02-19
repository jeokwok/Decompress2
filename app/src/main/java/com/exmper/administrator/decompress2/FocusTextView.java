package com.exmper.administrator.decompress2;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * Created by Administrator on 2018/12/12.
 */
public class FocusTextView extends TextView {
    //使用在Java代码中调用的构造函数创建控件  context 上下文环境
    public FocusTextView(Context context) {
        super(context);
    }
    //系统调用（上下文环境+带属性）的构造函数  AttributeSet = 属性集合
    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //系统调用（上下文环境+属性+布局文件中定义的样式）的构造函数
    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /*override superclass instance method  让textview 控件一直能获取焦点*/
    @Override
    public boolean isFocused() {
        return true;
    }
}
