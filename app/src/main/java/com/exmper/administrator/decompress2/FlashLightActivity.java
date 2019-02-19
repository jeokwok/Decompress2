package com.exmper.administrator.decompress2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class FlashLightActivity extends AppCompatActivity {
    private Button mMoreButton;
    private Button mBackButton;
    private ImageButton mSosButton;
    private CameraManager manager;
    private ToggleButton mToggleButton;


    private static Camera camera = null;

    /*按钮创建音效*/
    // MediaPlayer backgroundmusic = MediaPlayer.create(MainActivity.this,R.raw.gun);
    //private SoundPool soundPool;//声明一个SoundPool
    //private int soundID;//创建某个声音对应的音频ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*实现标题栏的隐藏*/
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*设置动态的layout背景图片*/
        // LinearLayout temp = (LinearLayout)findViewById(R.layout.flashlight);
        // Drawable d = Drawable.createFromPath();
        // temp.setBackgroundDrawable(d);

        setContentView(R.layout.activity_flash_light);
        //FlashLightView flashLightView =new FlashLightView(this);
        //setContentView(flashLightView);

        mMoreButton = (Button) findViewById(R.id.btn_more);
        mBackButton = (Button) findViewById(R.id.btn_more1);
        mSosButton = (ImageButton) findViewById(R.id.btn_sos);
        mToggleButton = (ToggleButton)findViewById(R.id.btn_toggle);
        //final View view = findViewById(R.id.parentView);



        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mToggleButton.setChecked(isChecked);
                //view= LayoutInflater.from(FlashLightActivity.this).inflate(R.layout.activity_flash_light,null);
                if(isChecked == true )
                {

                    //view.setBackgroundResource(R.drawable.p00);
                    //view.setBackgroundResource(R.drawable.p00);
                    try {
                        manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                        manager.setTorchMode("0", true);// "0"是主闪光灯

                    } catch (Exception e) {
                    }
                }
                else{
                    try {
                        manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                        manager.setTorchMode("0", false);// "0"是主闪光灯
                    } catch (Exception e) {
                    }

                    //view.setBackgroundResource(R.drawable.p01);
                }
            }
        });

    /*实现返回桌面点击事件*/

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                /*按钮打开音效*/

                    /*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("确认退出应用？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent home = new Intent(Intent.ACTION_MAIN);
                            home.addCategory(Intent.CATEGORY_HOME);
                            startActivity(home);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //null
                        }
                    });
                    builder.show();*/

            }
        });
        mSosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*数据对应的拨号码数组*/
                AlertDialog.Builder builder = new AlertDialog.Builder(FlashLightActivity.this);
                builder.setIcon(R.drawable.a101_meitu_4);
                builder.setTitle("紧急呼叫");
                final String[] calls = {"匪警110","急救120","火警119","自定义紧急联系人"};
                builder.setItems(calls, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*跳转到手机拨号*/
                        /*Intent intent1=new Intent();
                        intent1.setAction(Intent.ACTION_DIAL);
                        intent1.setData(Uri.parse(temp[0]));*/
                        // intent1.setAction(Intent.ACTION_PACKAGE_FIRST_LAUNCH);
                        /*实现把用户点击的数据号码输入到电话拨号上*/
                        /*if(calls[which] == calls[3] )
                        {
                            Context context = getApplicationContext();
                            CharSequence text = "此功能暂未开放！";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }else {*/
                        switch (calls[which])
                        {
                            case "匪警110"://calls[which] = temp[0];
                                //跳转到拨号界面，同时传递电话号码
                                //Intent Intent1 =  new Intent(Intent.ACTION_DIAL,Uri.parse("110" ));
                                //startActivity(Intent1);
                                String temp = "110";
                                Intent intent1=new Intent(Intent.ACTION_DIAL , Uri.parse("tel:"+temp));
                                //intent1.setAction(Intent.ACTION_DIAL , Uri.parse("tel://"+temp ));
                                    /*跳转到拨号界面OK 无法传输号码到拨号盘*/
                                //intent1.setData(Uri.parse(temp[0]));
                                startActivity(intent1);
                                break;
                            case "急救120"://calls[which] = temp[1];
                                String temp1 = "120";
                                Intent intent2=new Intent(Intent.ACTION_DIAL , Uri.parse("tel:"+temp1));
                                //intent2.setAction(Intent.ACTION_DIAL);
                                startActivity(intent2);
                                break;
                            case "火警119"://calls[which] = temp[2];
                                String temp2 = "119";
                                Intent intent3=new Intent(Intent.ACTION_DIAL , Uri.parse("tel:"+temp2));
                                //intent3.setAction(Intent.ACTION_DIAL);
                                startActivity(intent3);
                                break;
                            case "自定义紧急联系人":
                                    Context context = getApplicationContext();
                                    CharSequence text = "此功能暂未开放！";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    /*弹出一个新建自定义紧急联系人activity */
                               // Intent contactIntent = new Intent(FlashLightActivity.this,AddContactActivity.class);
                               // startActivity(contactIntent);


                                //AddContactActivity addContactActivity = new AddContactActivity();
                                //addContactActivity.getContactPhoneNum();
                                //String temp3 = addContactActivity.getContactPhoneNum();
                                //Intent intent4 = new Intent(Intent.ACTION_DIAL , Uri.parse("tel:"+temp3));
                                    /*输入一个联系人号码 、名字 保存和退出2个按钮*/
                                    /*当用户点击保存按钮后 数据保存在原紧急联系人的底行 “自定义紧急联系人”下移*/

                                    /*保存的数据储存在数据了 保存在本机的文件里*/
                                //String [] sosContact = {};

                                // contactPhoneNum[0];

                            default:
                                break;
                        }
                        // intent1.setData(Uri.parse(calls[which]));
                        //}
                        // startActivity(intent1);
                        //Toast.makeText(this,"您即将拨打"+calls[which],Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });
        mMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FlashLightActivity.this);
                final String [] menu = {"注册","登陆","更多","切换背景","返回"};
                builder.setItems(menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (menu[which])
                        {
                            case "注册":
                                displayEorro();
                                break;
                            case "登陆":
                                displayEorro();
                                break;
                            case "更多":
                                displayEorro();
                                break;
                            case"切换背景":
                                displayEorro();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();
            }
        });


    }
    /*更多菜单显示提示*/
    private void displayEorro() {
        Context context = getApplicationContext();
        CharSequence text = "此功能暂未开放！";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}

