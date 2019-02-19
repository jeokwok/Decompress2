package com.exmper.administrator.decompress2;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NetworkLoginActivity extends AppCompatActivity
{
    EditText etUsername,etUserpass;
    Button login,cancel,logout,forceout,help;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {getSupportActionBar().hide();}  /*实现标题栏的隐藏*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_login);
        //绑定组件
        bindCompoent();
        //程序初始化
        initialize();
    }

    //绑定各种主键并设置好监听器
    private void bindCompoent()
    {
        //绑定各类主键
        etUsername = (EditText) findViewById(R.id.userEditText);
        etUserpass = (EditText) findViewById(R.id.pwdEditText);
        login = (Button) findViewById(R.id.bnLogin);
        cancel = (Button) findViewById(R.id.bnCancel);
        logout = (Button) findViewById(R.id.bnLogout);
        forceout = (Button) findViewById(R.id.bnForceLogout);
        help = (Button) findViewById(R.id.bnHelp);

        //给取消按钮绑定监听器
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*点击取消的同时清空2个输入栏*/
                etUsername.setText(null);
                etUserpass.setText(null);
            }
        });

        //给登录按钮监听器
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证是否连接网络和输入是否合理
                if(validate())
                {
                    //发送登录请求
                    String username = etUsername.getText().toString();
                    String userpasswd = etUserpass.getText().toString();
                    try {
                        if(loginValidate(username,userpasswd))
                        {
                            Toast.makeText(getApplicationContext(),
                                    "登录成功",Toast.LENGTH_LONG).
                                    show();
                            login.setEnabled(false);
                            logout.setEnabled(true);
                            //将正确的账号和密码存储到本地中去
                            //save();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),
                                    "登录失败,请检查你的用户名和密码是否正确",
                                    Toast.LENGTH_SHORT).
                                    show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //给登出按钮设置监听器
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证是否连接网络和输入是否合理
                if(validate())
                {
                    //发送登录请求
                    String username = etUsername.getText().toString();
                    String userpasswd = etUserpass.getText().toString();
                    try {
                        if(logoutValidate())
                        {
                            Toast.makeText(getApplicationContext(),
                                    "登出成功",
                                    Toast.LENGTH_SHORT).
                                    show();
                            logout.setEnabled(false);
                            login.setEnabled(true);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),
                                    "登出失败,请检查你的用户名和密码是否正确",
                                    Toast.LENGTH_SHORT).
                                    show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //给强制登出按钮设置监听器
        forceout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证是否连接网络和输入是否合理
                if(validate())
                {
                    //发送登录请求
                    String username = etUsername.getText().toString();
                    String userpasswd = etUserpass.getText().toString();
                    try {
                        if(forceLogoutValidate(username,userpasswd))
                        {
                            Toast.makeText(getApplicationContext(),
                                    "下线成功",
                                    Toast.LENGTH_SHORT).
                                    show();
                            logout.setEnabled(false);
                            login.setEnabled(true);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),
                                    "下线失败,请检查你的用户名和密码是否正确",
                                    Toast.LENGTH_SHORT).
                                    show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(NetworkLoginActivity.this).
                        setTitle("帮助界面").
                        setMessage("本软件适合在西南大学一键登录外网 \n--------by cjyong\n" +
                                "强制退出按钮会强制退出所有当前账号登录的设备,请谨慎使用\n"+
                                "如果软件有问题,请联系QQ2686600303\n").
                        setNegativeButton("取消",null).
                        show();
            }
        });
    }

    private void initialize()
    {
        //判断以前是否登录过,通过存储在本地的记录进行判断
        isOldUser();

        //判断网络是否连接正确,是否可以联网
        wifiIsGood();

    }

    //检查网络是否可以正确连接
    private void wifiIsGood()
    {
        //判断wifi是否连接
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo.State wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(wifi== NetworkInfo.State.DISCONNECTED)
        {
            Toast.makeText(this,"请连接好WiFi再进行登录",Toast.LENGTH_SHORT).show();
            return ;
        }

        //判断是否可以上网
        try
        {
            //前往bibili网站
            String html = HttpUtil.sendGetRequest("http://www.bilibili.com", false, null, "utf-8");
            if(html.contains("http://222.198.127.170/"))
            {
                return;
            }
            else
            {
                Toast.makeText(this,"你的WiFi已经可以上网,不用登陆",Toast.LENGTH_LONG).show();
                return;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //通过遍历本地记录,进行判断是否之前登录过,如果登录自动填充两个对话框
    private void isOldUser()
    {
        //这里通过preference进行储存相应数据
        preferences = getSharedPreferences("userpass",0);
        editor = preferences.edit();
        String username = preferences.getString("username", null);
        String userpass = preferences.getString("userpass",null);

        if(username != null && userpass!= null)
        {
            etUsername.setText(username);
            etUserpass.setText(userpass);
        }
        return;
    }

    //检查wifi状况和输入情况
    private boolean validate()
    {
        String username = etUsername.getText().toString();
        String userpass = etUserpass.getText().toString();
        if(username.equals("") || userpass.equals(""))
        {
            Toast.makeText(this,"用户名或者密码不可以为空,请重新输入",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //将正确的账号和密码存储到本地中去
    private void save()
    {
        String username = etUsername.getText().toString();
        String userpass = etUserpass.getText().toString();
        editor.putString("username",username);
        editor.putString("userpass",userpass);
        editor.commit();
    }


    //进行登录操作
    private boolean loginValidate(String username,String passwd) throws Exception
    {
        final String html = HttpUtil.sendGetRequest("http://222.198.127.170/", false, null, "gbk");
        //使用正则表达式获取对应的填充数据
        String p = "jsp\\?(.+?)'</script>";
        Pattern reg = Pattern.compile(p);
        Matcher m= reg.matcher(html);
        String FillingStr = "";
        if(m.find())
        {
            FillingStr = m.group(1);
        }
        //这里需要注意,需要使用utf-8格式进行编码
        FillingStr = URLEncoder.encode(FillingStr,"utf-8");
        final String url = "http://222.198.127.170/eportal/InterFace.do?method=login";
        final String data="userId="+username+"&password="+passwd+"&service=%25E9%25BB%2598%25E8%25AE%25A4&queryString="+FillingStr+"&operatorPwd=&operatorUserId=&validcode=";
        //发送登录请求
        String html2=HttpUtil.sendPostRequest(url, data, false, null, "gbk");
        if(html2.contains("success"))
            return true;
        return false;
    }

    //进行登出操作
    private boolean logoutValidate() throws Exception
    {
        String html = HttpUtil.sendGetRequest("http://222.198.127.170/eportal/InterFace.do?method=logout",false,null,"utf-8");
        if(html.contains("success"))
            return true;
        return false;
    }

    //进行强制下线操作
    private boolean forceLogoutValidate(String username,String passwd) throws Exception
    {
        //构造填充参数
        String data ="name="+username+"&password="+passwd;
        String url= "http://service2.swu.edu.cn/selfservice/module/scgroup/web/login_judge.jsf";
        //构造cookie
        String Cookie=HttpUtil.getCookie(url,data);
        Cookie=String.format(Cookie+" rmbUser=true; userName=%s; passWord=%s; oldpassWord=%s;", username,passwd,passwd);
        String listurl= "http://service2.swu.edu.cn/selfservice/module/webcontent/web/onlinedevice_list.jsf";
        String html= HttpUtil.sendGetRequest(listurl, true, Cookie, "gbk");
        //账号密码错误
        if(html.contains("您还未登录或会话过期"))
            return false;
        //获取设备的IP地址构造填充数据
        String p = "<span id=\"a1\">IP : (.+?)</span >";
        Pattern reg = Pattern.compile(p);
        Matcher m=reg.matcher(html);
        //将所有的设备进行下线
        while(m.find())
        {
            //执行下线操作
            String myurl = "http://service2.swu.edu.cn/selfservice/module/userself/web/userself_ajax.jsf?methodName=indexBean.kickUserBySelfForAjax";
            String mydata = "key= "+username+":" +m.group(1);
            HttpUtil.sendPostRequest(myurl, mydata, true, Cookie, "utf-8");
        }
        return true;
    }


}
