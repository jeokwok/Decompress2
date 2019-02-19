package com.exmper.administrator.test2;

/*   下载进度条
    带数据显示 和
    下载完提示*/

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Test2Activity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView mTv1;
    private Button mButton;

    private static  final  int TYPE_PROGRESS = 110;
    private static final int TYPE_OVER = 120;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case TYPE_PROGRESS:
                    int progress = msg.arg2;
                    progressBar.setProgress(progress);
                    mTv1.setText(msg.arg2+"/100");
                    break;
                case  TYPE_OVER:
                    Toast.makeText(Test2Activity.this, "download is success", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);


        progressBar = (ProgressBar) findViewById(R.id.pb_progress);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mButton = (Button) findViewById(R.id.btn_start);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyThread().start();
            }
        });
    }

    class MyThread extends  Thread{
        int count = 0;
        @Override
        public void run() {
            super.run();
            while(count<progressBar.getMax()) {
                SystemClock.sleep(100);
                count++;
                Message message =Message.obtain();
                message.what = TYPE_PROGRESS;
                message.arg2 = count;
                handler.sendMessage(message);
            }
            Message message =Message.obtain();
            message.what = TYPE_OVER;
            handler.sendMessage(message);
        }
    }
}
