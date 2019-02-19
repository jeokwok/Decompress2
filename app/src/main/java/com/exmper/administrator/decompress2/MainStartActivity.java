package com.exmper.administrator.decompress2;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainStartActivity extends AppCompatActivity {
    private ImageView iv_start;
    private TextView mVersionName;
    private ProgressBar mProgressBar;

    String a;
    private VideoView videoview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_start);
        /*开场图片*/
        initImage();
        /*显示进度条的数值 */
        initProgressValue();
    }

    private void initProgressValue() {
        new Thread(){
            @Override
            public void run() {
                int i=0;
                while(i<100){
                    i++;
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int j=i;
                    mProgressBar.setProgress(i);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            a =(j+"%").toString();
                        }
                    });
                }
            }
        }.start();
    }

    /*开场图片实现*/
    private void initImage() {
        iv_start = (ImageView) findViewById(R.id.iv_begin);
        mVersionName = (TextView)findViewById(R.id.tv_version);
        mProgressBar = (ProgressBar)findViewById(R.id.pb_check_version);

        mVersionName.setTextColor(Color.BLACK);
        mVersionName.setTextSize(15);
        mVersionName.setText("Version:" + getVersionName() + a);
        iv_start.setImageResource(R.drawable.image02);
        //进行缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        //动画播放完成后保持形状
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {

                startActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_start.startAnimation(scaleAnimation);

    }

    private void startActivity() {
        Intent intent = new Intent(MainStartActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
    /*开场动画实现*/
    /*private void initView() {
        videoview = (VideoView) findViewById(R.id.videoview);
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.beginflash));
        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });
    }*/

    /**
     * 返回版本号。
     *
     * @return
     */
    private int getVersionCode() {
        // 1.获取包管理者packageManager
        PackageManager pm = getPackageManager();
        // 2.从包管理者对象中，获取指定包名的基本信息（版本名称，版本号），传0表示基本信息。
        try {
            PackageInfo version = pm.getPackageInfo(getPackageName(), 0);
            return version.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取应用的版本名称。
     */
    private String getVersionName() {
        // 1.获取包管理者packageManager
        PackageManager pm = getPackageManager();
        // 2.从包管理者对象中，获取指定包名的基本信息（版本名称，版本号），传0表示基本信息。
        try {
            PackageInfo version = pm.getPackageInfo(getPackageName(), 0);
            return version.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*联网检查版本信息*/
    public void checkVersion(){
        new Thread(){
            public void run() {
                HttpURLConnection connection;
                try {
                    /*创建一个表示资源网络地址的URL对象*/
                    URL url = new URL("file:///J:/test/test/index.html");
                    /*创建一个HttpURLConnection的对象，它能够加载URL并连接到相应的站点*/
                    connection = (HttpURLConnection) url.openConnection();
                    connection.getContent();
                    /*使用HttpURLConnection对象的getContent()方法来创建一个InputStreamReader,用于读取来自URL的数据流*/
                    InputStreamReader inputStreamReader = new InputStreamReader((InputStream)connection.getContent());
                    /*使用输入流阅读器来创建一个BufferedReader对象 高效的从输入流中读取字符*/
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String line;
                    line = bufferedReader.readLine();
                    mVersionName.setText(line.toString());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();



        new Thread(){
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection connection;
                try{
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(1000);
                    connection.setConnectTimeout(1000);
                    connection.setRequestMethod("GET");} catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e) {

                }
                finally {

                }
                super.run();
            }
        };
    }

}
