package com.example.activitytest3;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OneActivity extends AppCompatActivity {
    private PackageManager mPackageManager;
    private TextView mTextView;
    Vibrator mVibrator;
    NotificationManager mManager;
    ImageView  imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        mTextView = (TextView) findViewById(R.id.tv1);
        mVibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        //获取通知信息栏的管理器 获取对象
         mManager = (NotificationManager) this.getSystemService(Service.NOTIFICATION_SERVICE);


         imageView = (ImageView) findViewById(R.id.iv_imageview);
        //自动补齐输入  传递String数据 自动补齐
        List <String> data1 = new ArrayList<>();
        data1.add("a");
        data1.add("b");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data1);
        //设置适配器



    }
    void oneclick(View view){
        Intent intent = new Intent(OneActivity.this,TwoActivity.class);
        startActivity(intent);
        //实现activity之间的跳转动画 
        this.overridePendingTransition(R.anim.two_in,R.anim.main_out);
    }

    void twoclick(View view){

        Dialog dig = new Dialog(this);
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dig.setContentView(R.layout.dig);
        //获取对话窗体对象然后设置窗体动画
        dig.getWindow().setWindowAnimations(R.style.isa);
        dig.show();

    }

    public void go3(View view){
        Pop po = new Pop(this);
        po.showAsDropDown(mTextView,50,100);

    }
    public void go4(View view){
        Toast.makeText(this, "震动 ", Toast.LENGTH_SHORT).show();
        //设置震动器 参数1 long数组  参数2 -1代表不重复  次数代表震动次数
        mVibrator.vibrate(new long[]{2000,3000,3000,5000},-1);

    }
    //弹出状态栏提示
    public void go5(View view){
        /*实现点击事件 下拉通知栏弹出通知*/
        Toast.makeText(this, "弹出下拉通知 （下拉通知栏）", Toast.LENGTH_SHORT).show();
        /*构建通知栏主体通知显示类型 */
        String  str = " 0 ";
        //设置点击下拉通知 跳转页面
        Intent in = new Intent(OneActivity.this,TwoActivity.class);
        PendingIntent intent = PendingIntent.getActivity(this,50,in,0);
        Notification.Builder  no = new Notification.Builder(this);
        no.setTicker("消息提示 您的手机已经欠费   大部分机型").setContentText("AAAAA")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentInfo("bbbbbb")
                //设置所有通知声音、震动、灯光全部默认
                .setDefaults(Notification.DEFAULT_ALL)
                //参数传入 URI  设置音乐铃声 SD卡中加载音乐文件
        .setSound(Uri.parse(str))
        .setContentIntent(intent);
       // no.build();
        Notification noti = no.getNotification();
        mManager.notify(998,noti);
    }
    /*提示栏 */
    /*自定义toast*/

    public void go7(View view){
        Toast.makeText(this, "button seven on click ", Toast.LENGTH_SHORT).show();
       // TranslateAnimation translateAnimation = new TranslateAnimation(0,600,0,0);
       // translateAnimation.setDuration(2000);
       // imageView.startAnimation(translateAnimation);

        //属性动画  Animator
        Animator animator = ObjectAnimator.ofFloat(imageView,"rotationX",0,360);
       // Animator animator = ObjectAnimator.ofFloat(imageView,"translationY",0,600);
       // Animator animator = ObjectAnimator.ofFloat(imageView,"translation",0,600); //表示Z轴
        animator.setDuration(3000);
        animator.start();

        //设置轴
        imageView.setPivotX(imageView.getWidth());
        imageView.setPivotY(0);

        Animator animator2 = ObjectAnimator.ofFloat(imageView,"rotationY",0,360);
        // Animator animator = ObjectAnimator.ofFloat(imageView,"translationX",0,600);
        animator2.setDuration(3000);
        animator2.start();
        //透明度
        Animator animator3 = ObjectAnimator.ofFloat(imageView,"alpha",1,0);
        animator3.setDuration(3000);
        animator3.start();
        //缩放
        Animator animator4 = ObjectAnimator.ofFloat(imageView,"scaleX",0,3f);
        //Animator animator4 = ObjectAnimator.ofFloat(imageView,"scaleY",0,3f);
        animator4.setDuration(3000);
        animator4.start();
    }
    public void go8(View view){
        //获取包名并打印到log日志
           List<PackageInfo> pi = mPackageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
          for(PackageInfo ps:pi ){
               System.out.println("包名： "+ps.packageName + "版本号： "+ ps.versionName + "版本名称： "+ ps.versionCode);
              CharSequence label = ps.applicationInfo.loadLabel(mPackageManager);
             Drawable icon =  ps.applicationInfo.loadIcon(mPackageManager);
              //判断包名是否一致
              if("".equals(ps.packageName)){
                  ActivityInfo[] activityInfos = ps.activities;
                  if(activityInfos!= null && activityInfos.length != 0){
                      ActivityInfo sactivity = activityInfos[0];

                      Intent intent = new Intent();
                      intent.setClassName(ps.packageName,sactivity.name);
                      this.startActivity(intent);
                  }
              }
          }
        //获取图标
        //获取指定包名的应用 打开指定应用   获取是Activity组合
        List<PackageInfo> pi1= mPackageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES|PackageManager.GET_ACTIVITIES);
        for(PackageInfo ps1:pi1){
            if("".equals(ps1.packageName))
            {
                //获取activity列表信息
                ActivityInfo[] activityInfos1 = ps1.activities;
                if(activityInfos1 != null && activityInfos1.length != 0)
                {
                    //用来打开指定包名的应用
                    ActivityInfo aactivity = activityInfos1[0];
                    Intent intent = new Intent();
                    this.startActivity(intent);
                }
            }
        }

    }


}
