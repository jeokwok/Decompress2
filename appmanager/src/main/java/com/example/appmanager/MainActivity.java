package com.example.appmanager;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView mGridView;
    private ListView mListView;
    private ImageButton imageButton1;

    private final static int GV = 0;
    private final static int LV = 1;

    //view视图切换结果
    private int changeStatus = 0;

    List<APP> myDate = new ArrayList<>();
    Util mUtil;
   private  PackageManager mPackageManager;
   /*声明GridView和listview 数据构造器*/
    private MyAdapter myAdapter;
    private MyAdapterLv myAdapterLv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        initializeDatas();
         myAdapter = new MyAdapter(this,myDate);
        mGridView.setAdapter(myAdapter);

        myAdapterLv = new MyAdapterLv(this,myDate);
        mListView.setAdapter(myAdapterLv);
        //使用工具类 获取数据
        //Util util = new Util(this);
        //util.versionCheck();
    }

    private void initializeDatas() {
        mPackageManager = this.getPackageManager();
        mUtil = new Util(mPackageManager);
        myDate = mUtil.getAllApps();

    }

    private void initializeView() {
        imageButton1 = (ImageButton) findViewById(R.id.btn_button1);
        mGridView = (GridView) findViewById(R.id.gv_gridview);
        mListView = (ListView) findViewById(R.id.lv_listview);
    }

    public void button1click(View view){
        //点击切换GridView和listview
        Toast.makeText(this, "button 1 点击事件", Toast.LENGTH_SHORT).show();
        if(changeStatus == GV){
            changeStatus = LV;
            mGridView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);

            //点击切换背景图片
        }else if(changeStatus ==  LV)
        {
            changeStatus = GV;
            mListView.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);
        }
    }
}
   /*创建工具类 获取去数据 填充适配器 */
class Util
{
    private PackageManager mPackageManager;

    public Util(PackageManager packageManager) {
        mPackageManager = packageManager;
    }

    public  List<APP> getAllApps( ){
        List<APP> apps = new ArrayList<>();
        List<PackageInfo> pm= mPackageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES|PackageManager.GET_ACTIVITIES);
        for(PackageInfo pi:pm){
            APP app = new APP();
            CharSequence label = pi.applicationInfo.loadLabel(mPackageManager);
            Drawable icon =  pi.applicationInfo.loadIcon(mPackageManager);

            app.setIcon(icon);
            app.setAppName(label.toString());
            app.setPackageName(pi.packageName);
           // app.setVersionCode(pi.versionCode+"");
           // app.setVersionName(pi.versionName);

            //ActivityInfo[] activityInfos = pi.activities;
            //if(activityInfos != null && activityInfos.length != 0)
           // {
              //  ActivityInfo fristactivity = activityInfos[0];
              //  app.setAppName(fristactivity.name);
           // }

            apps.add(app);
        }
        return apps;

    }
}
class MyAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<APP> mMyData;

    public MyAdapter(Context context, List<APP> myData) {
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
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.custom_item,parent,false);
        }
        TextView textView = (TextView)convertView;

        APP app = getItem(position);
       // convertView.setTag(app);
        textView.setText(app.getAppName());
        //textView.setCompoundDrawables(null,app.getIcon(),null,null);

        //把创建好的布局文件渲染到构造器内 然后返回一个view
       // View view =mLayoutInflater.inflate(R.layout.custom_item,null,false);
       // convertView = view.findViewById(R.id.tv);
       // convertView.getItem(position);
        //view获取自定义布局文件中的控件
        //通过自定义数据类 往构造器内填充数据
        //返回渲染的view 显示出来
        // 通过主函数新建自定义对象类MyAdapter的对象 在声明控件设置构造器
        return convertView ;
    }
}
/*适配器
* 1. 数据
* （1）封装数据对象类MyApp   需要的数据  （图标 应用名 首启动的activity的名称 包名 版本号 版本名）
* （2）数据的获取 创建工具类 通过构造器传参 获取APPS   工具类 util
* （3) 创建适配器继承baseadapter
* 2. 样式条目
* （1）创建一个XML布局文件 用于构造器渲染
*
*
*
* activity_main布局文件中 关于FrameLayout使用 叠加2个布局 gridview 和 listview
* 默然打开显示gridview 则布局文件中先帮listview visiblelly 设置成gone
*
*按键选择器（点击背景颜色有切换） 在drawable中新建 XML文件 构建选择器
* 使用imagebutton 的SRC设置按钮图片    background设置选择器实现点击后 有点击效果触发
*
* */