package com.exmper.administrator.decompress2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class PlaneGameActivity extends AppCompatActivity {

    public static  PlaneGameActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置全屏
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
              // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //显示自定义的SurfaceView视图
        setContentView(new MySurfaceView(PlaneGameActivity.this));

    }
}
