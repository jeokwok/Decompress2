package com.exmper.administrator.decompress2;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetTicketActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<View> viewList = new ArrayList<View>();
    //List<Fragment> fragments = new ArrayList<>();


    private String[] titles = new String[]{"最新", "热门", "我的"};
    private String[] data1 = { "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };
    private String[] data2 = { "苹果", "香蕉", "桔子", "Watermelon",
            "梨", "葡萄", "Pineapple", "Strawberry", "Cherry", "Mango" };
    private String[] data3 = { "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {getSupportActionBar().hide();} /*实现标题栏的隐藏*/
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_ticket);
        initialize();

    }

    private void initApapterData_1() {
    }

    private void initialize() {
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        for (int i = 0; i < titles.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }

        for (int i = 0; i < titles.length; i++) {
            mTabLayout.getTabAt(i).setText(titles[i]);

        }
        View view1 = getLayoutInflater().inflate(R.layout.list_view,null);
        View view2 = getLayoutInflater().inflate(R.layout.list_view,null);
        View view3 = getLayoutInflater().inflate(R.layout.list_view,null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        ListView listview1 = (ListView) view1.findViewById(R.id.lv_listview);
        ListView listview2 = (ListView) view2.findViewById(R.id.lv_listview);
        ListView listview3 = (ListView) view3.findViewById(R.id.lv_listview);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                GetTicketActivity.this, android.R.layout.simple_list_item_1, data1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                GetTicketActivity.this, android.R.layout.simple_list_item_1, data2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                GetTicketActivity.this, android.R.layout.simple_list_item_1, data3);
        listview1.setAdapter(adapter1);
        listview2.setAdapter(adapter2);
        listview3.setAdapter(adapter3);

        mViewPager.setAdapter(MyAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                switch(tab.getPosition()){
                    case 0:
                        Toast.makeText(GetTicketActivity.this, "tablayout A", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(GetTicketActivity.this, "tablayout B", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(GetTicketActivity.this, "tablayout C", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    PagerAdapter MyAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //这个方法返回一个对象，该对象表明PagerAapter选择哪个对象放在当前的ViewPager中。这里我们返回当前的页面
            mViewPager.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //这个方法从viewPager中移动当前的view。（划过的时候）
            mViewPager.removeView(viewList.get(position));
        }
    };


}




   /* TextView mView;
    Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ticket);

        initialize();
        getTicket();
    }

    private void initialize() {
         mView = (TextView) findViewById(R.id.tv_disticketinfo);
         mBtn = (Button) findViewById(R.id.btn_gettickinfo);
    }

    public void  getTicket() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    public void run() {
                        HttpURLConnection connection;
                        try {
                    /*创建一个表示资源网络地址的URL对象*/
                            /*URL url = new URL("https://kyfw.12306.cn/otn/leftTicket/queryZ");
                    /*创建一个HttpURLConnection的对象，它能够加载URL并连接到相应的站点*/
                            /*connection = (HttpURLConnection) url.openConnection();
                            connection.getContent();
                    /*使用HttpURLConnection对象的getContent()方法来创建一个InputStreamReader,用于读取来自URL的数据流*/
                          //  InputStreamReader inputStreamReader = new InputStreamReader((InputStream)connection.getContent());
                    /*使用输入流阅读器来创建一个BufferedReader对象 高效的从输入流中读取字符*/
                            /*BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                            String line;
                            line = bufferedReader.readLine();
                            mView.setText(line.toString());

                        } catch (MalformedURLException e) {
                            Toast.makeText(GetTicketActivity.this,"not found URL ",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (IOException e) {
                            Toast.makeText(GetTicketActivity.this,"not found IO ",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
}*/
