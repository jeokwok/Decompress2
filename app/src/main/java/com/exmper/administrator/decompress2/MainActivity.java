package com.exmper.administrator.decompress2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.support.design.widget.TabLayout;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener ,
        ViewPager.OnPageChangeListener{
    //String url = "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg";
    /*class variable */
    private static final String FLAG = "MainActivity";
    /*instance variable*/
    private int isSelect = 0;


    ImageView set_imageView ;
    TextView chat_textView;
    TextView out_textView;
    TextView acme_textView;
    TextView story_textView;
    TextView set_textView;

    private List<DecompressObjectItem> costom_item_list_1 = new ArrayList<DecompressObjectItem>();
    private List<DecompressObjectItem> costom_item_list_2 = new ArrayList<DecompressObjectItem>();
    private List<DecompressObjectItem> costom_item_list_3 = new ArrayList<DecompressObjectItem>();
    private List<DecompressObjectItem> costom_item_list_4 = new ArrayList<DecompressObjectItem>();
    private List<DecompressObjectItem> costom_item_list_5 = new ArrayList<DecompressObjectItem>();

    /*class method*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*实现标题栏的隐藏*/
        if (getSupportActionBar() != null) {getSupportActionBar().hide();}

        super.onCreate(savedInstanceState);
        /*去除当前activity的头title 方法一 */
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        //找到ViewPager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

       // getImageBitmap(url);
        //定义一个视图集合（用来装左右滑动的页面视图）
        final List<View> viewList = new ArrayList<View>();

        //定义两个视图，两个视图都加载同一个布局文件list_view.xml
        View view1 = getLayoutInflater().inflate(R.layout.list_view,null);
        View view2 = getLayoutInflater().inflate(R.layout.grid_view,null);
        View view3 = getLayoutInflater().inflate(R.layout.list_view,null);
        View view4 = getLayoutInflater().inflate(R.layout.grid_view,null);
        View view5 = getLayoutInflater().inflate(R.layout.list_view,null);

        //将两个视图添加到视图集合viewList中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);

        //为ViewPager设置适配器
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                //这个方法是返回总共有几个滑动的页面（）
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                //该方法判断是否由该对象生成界面。
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //这个方法返回一个对象，该对象表明PagerAapter选择哪个对象放在当前的ViewPager中。这里我们返回当前的页面
                viewPager.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //这个方法从viewPager中移动当前的view。（划过的时候）
                viewPager.removeView(viewList.get(position));
            }
        });

        viewPager.setOnPageChangeListener(this);
        //——————————————————————————————————重点理解——————————————————————————————————
        // 原来findviewById是View这个类中的方法，默认调用时其实应该是：this.findviewById();
        //由于listview标签的声明并不在当前的viewPager所在的xml布局中，所以直接通过findviewById方法是不能得到该listview的实例的。所以我们要用view1.findViewById（）方法找到listview
        ListView listView1 = (ListView) view1.findViewById(R.id.lv_listview);
        GridView listView2 = (GridView) view2.findViewById(R.id.gv_gridview);
        ListView listView3 = (ListView) view3.findViewById(R.id.lv_listview);
        GridView listView4 = (GridView) view4.findViewById(R.id.gv_gridview);
        ListView listView5 = (ListView) view5.findViewById(R.id.lv_listview);
        //———————————————————————————————————重点理解——————————————————————————————————



        View chat_botton = findViewById(R.id.btn_chat);
        View out_botton = findViewById(R.id.btn_out);
        View acme_botton = findViewById(R.id.btn_acme);
        View story_botton = findViewById(R.id.btn_story);
        View set_botton = findViewById(R.id.btn_set);

        set_imageView =  (ImageView) findViewById(R.id.iv_set_imageview);

        chat_textView = (TextView)findViewById(R.id.tv_chat_textview);
        out_textView = (TextView)findViewById(R.id.tv_out_textview);
        acme_textView = (TextView)findViewById(R.id.tv_acme_textview);
        story_textView = (TextView)findViewById(R.id.tv_story_textview);
        set_textView = (TextView)findViewById(R.id.tv_set_textview);

        /*启动后的第一个页面显示状态 chat页面*/
        chat_textView.setTextColor(Color.GREEN);

        chat_botton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Click chat" , Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(0);
                isSelect = 0;
                return false;
            }
        });
        out_botton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Click out" , Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1);
                isSelect = 1;
                return false;
            }
        });

        acme_botton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Click acme" , Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(2);
                //viewPager.setCurrentItem((Integer) v.getTag());
                isSelect = 2;
                return false;
            }
        });

        story_botton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Click story" , Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(3);
                isSelect = 3;
                return false;
            }
        });
        set_botton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Click set", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(4);
                isSelect = 4;
                return false;
            }
        });


        //这里我们传入数据
        //String[] data = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
        //String[] data_word = {"a","b","c","f","e","f","g","h","i","j","k","l","m"};

        //android.R.layout.simple_list_item_1是android自带的一个布局，只有一个textview
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        // ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data_word);
        //ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data_word);
        //ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data_word);
        //ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data_word);

        CustomAdapter customAdapter_1 = new CustomAdapter(MainActivity.this,R.layout.custom_item_1,costom_item_list_1);
        CustomAdapter customAdapter_2 = new CustomAdapter(MainActivity.this,R.layout.custom_item_1,costom_item_list_2);
        CustomAdapter customAdapter_3 = new CustomAdapter(MainActivity.this,R.layout.custom_item_1,costom_item_list_3);
        CustomAdapter customAdapter_4 = new CustomAdapter(MainActivity.this,R.layout.custom_item_1,costom_item_list_4);
        CustomAdapter customAdapter_5 = new CustomAdapter(MainActivity.this,R.layout.custom_item_1,costom_item_list_5);

        //为ListView设置适配器
        initApapterData_1();
        listView1.setAdapter(customAdapter_1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        Toast.makeText(MainActivity.this, FLAG + "Click item A" + position, Toast.LENGTH_SHORT).show();
                        /*加载网络图片*/
                        Intent intent_download = new Intent(MainActivity.this, GriyViewActivity.class);
                        startActivity(intent_download);
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Click item A" + position, Toast.LENGTH_SHORT).show();
                        Intent intent_ticketassistant = new Intent(MainActivity.this,TicketAssistant.class);
                        startActivity(intent_ticketassistant);
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Click item A" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "Click item A" + position, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;

                }
            }
        });

        initApapterData_2();
        listView2.setAdapter(customAdapter_2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        Toast.makeText(MainActivity.this, "Click item B" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Click item B" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Click item B" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "Click item B" + position, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        initApapterData_3();
        listView3.setAdapter(customAdapter_3);
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        Toast.makeText(MainActivity.this, "Play Plane Game" + position, Toast.LENGTH_SHORT).show();
                        //Intent planegame_intent = new Intent(MainActivity.this,PlaneGameActivity.class);
                        //startActivity(planegame_intent);
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Open Flash Light" + position, Toast.LENGTH_SHORT).show();
                        Intent flashlight_intent = new Intent(MainActivity.this,FlashLightActivity.class);
                        startActivity(flashlight_intent);
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Ticket" + position, Toast.LENGTH_SHORT).show();
                        Intent ticket_intent = new Intent(MainActivity.this,GetTicketActivity.class);
                        startActivity(ticket_intent);
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "NetWorkLogin" + position, Toast.LENGTH_SHORT).show();
                        Intent networklogin_intent = new Intent(MainActivity.this,NetworkLoginActivity.class);
                        startActivity(networklogin_intent);
                        break;
                    default:
                        break;

                }
            }
        });

        initApapterData_4();
        listView4.setAdapter(customAdapter_4);
        listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        Toast.makeText(MainActivity.this, "Click item D" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Click item D" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Click item D" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "Click item D" + position, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        initApapterData_5();
        listView5.setAdapter(customAdapter_5);
        listView5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        Toast.makeText(MainActivity.this, "Click item E" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Click item E" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Click item E" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "Click item E" + position, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, "Click item" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position ){
            case 0:
                chat_textView.setTextColor(Color.GREEN);
                out_textView.setTextColor(Color.BLACK);
                acme_textView.setTextColor(Color.BLACK);
                story_textView.setTextColor(Color.BLACK);
                set_textView.setTextColor(Color.BLACK);
                break;
            case 1:
                chat_textView.setTextColor(Color.BLACK);
                out_textView.setTextColor(Color.GREEN);
                acme_textView.setTextColor(Color.BLACK);
                story_textView.setTextColor(Color.BLACK);
                set_textView.setTextColor(Color.BLACK);
                break;
            case  2:
                chat_textView.setTextColor(Color.BLACK);
                out_textView.setTextColor(Color.BLACK);
                acme_textView.setTextColor(Color.GREEN);
                story_textView.setTextColor(Color.BLACK);
                set_textView.setTextColor(Color.BLACK);
                break;
            case 3:
                chat_textView.setTextColor(Color.BLACK);
                out_textView.setTextColor(Color.BLACK);
                acme_textView.setTextColor(Color.BLACK);
                story_textView.setTextColor(Color.GREEN);
                set_textView.setTextColor(Color.BLACK);
                break;
            case 4:
                chat_textView.setTextColor(Color.BLACK);
                out_textView.setTextColor(Color.BLACK);
                acme_textView.setTextColor(Color.BLACK);
                story_textView.setTextColor(Color.BLACK);
                set_textView.setTextColor(Color.GREEN);
                break;
                default:
                    chat_textView.setTextColor(Color.GREEN);
                    break;
        }

        if(isSelect == 0 )
        {

        }else if(isSelect  == 1)
        {
            position = 1;
        }else if(isSelect == 2)
        {
            position = 2;
        }
        else if(isSelect == 3)
        {
            position = 3;
        }
        else if(isSelect == 4)
        {
            position = 4;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class CustomAdapter extends ArrayAdapter {

        private  int resourceId ;

        public CustomAdapter(Context context, int textViewResourceId, List<DecompressObjectItem> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DecompressObjectItem decompressObjectItem = (DecompressObjectItem) getItem(position); // 获取当前项的Fruit实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象

            ImageView fruitImage = (ImageView) view.findViewById(R.id.customitem_image);//获取该布局内的图片视图
            TextView fruitName = (TextView) view.findViewById(R.id.customitem_name);//获取该布局内的文本视图
            TextView fruitNameDetal = (TextView) view.findViewById(R.id.customitem_detail);
            TextView fruitTime = (TextView) view.findViewById(R.id.custom_time);

            fruitImage.setImageResource(decompressObjectItem.getImageId());//为图片视图设置图片资源
            fruitName.setText(decompressObjectItem.getName());//为文本视图设置文本内容
            fruitNameDetal.setText(decompressObjectItem.getDetail());
            fruitTime.setText(decompressObjectItem.getTime());
            return view;
            //return super.getView(position, convertView, parent);
        }
    }

    public class DecompressObjectItem {
        private String name;
        private int imageId;
        private String Detail;
        private String time;

        public DecompressObjectItem(String name, int imageId,String nameDetail,String time) {
            this.name = name;
            this.imageId = imageId;
            this.Detail = nameDetail;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public int getImageId() {return imageId; }

        public String getDetail() {return Detail; }

        public String getTime(){return time;}
    }


    private void initApapterData_1() {
         DecompressObjectItem l = new  DecompressObjectItem("Search Image",R.mipmap.ic_launcher,
                 "enter URL return image",null);
        costom_item_list_1.add(l);
        DecompressObjectItem k = new  DecompressObjectItem("Web View",R.mipmap.ic_launcher,
                "web view ",null);
        costom_item_list_1.add(k);
        DecompressObjectItem j = new  DecompressObjectItem("person 3",R.mipmap.ic_launcher,
                "begin a random chat with evey one Num three",null);
        costom_item_list_1.add(j);
        DecompressObjectItem i = new  DecompressObjectItem("person 4",R.mipmap.ic_launcher,
                "begin a random chat with evey one Num four",null);
        costom_item_list_1.add(i);
    }
    private void initApapterData_2() {
        DecompressObjectItem l = new DecompressObjectItem("12", R.mipmap.ic_launcher, "ABCDEFG",null);
        costom_item_list_2.add(l);
        DecompressObjectItem k = new DecompressObjectItem("11", R.mipmap.ic_launcher, "123456",null);
        costom_item_list_2.add(k);
        DecompressObjectItem j = new DecompressObjectItem("10", R.mipmap.ic_launcher, "123456",null);
        costom_item_list_2.add(j);
        DecompressObjectItem i = new DecompressObjectItem("9", R.mipmap.ic_launcher, "123456",null);
        costom_item_list_2.add(i);
    }

    private void initApapterData_3() {
        DecompressObjectItem b = new DecompressObjectItem("Plane Game", R.mipmap.ic_launcher,
                "a new game for the plane\n.if you want try it you can !",">");
        costom_item_list_3.add(b);
        DecompressObjectItem a = new DecompressObjectItem("Security Flash Light", R.mipmap.ic_launcher,
                "a secutity flash light in you hand \n" +
                        "make your life colourful",">");
        costom_item_list_3.add(a);
        DecompressObjectItem l = new DecompressObjectItem("Get Ticket", R.mipmap.ic_launcher, "ticket",">");
        costom_item_list_3.add(l);
        DecompressObjectItem k = new DecompressObjectItem("NetWorkLogin", R.mipmap.ic_launcher, "a app",">");
        costom_item_list_3.add(k);

    }
    private void initApapterData_4() {
        DecompressObjectItem l = new  DecompressObjectItem("12",R.mipmap.ic_launcher, "ABCDEFG",null);
        costom_item_list_4.add(l);
        DecompressObjectItem k = new  DecompressObjectItem("12",R.mipmap.ic_launcher, "ABCDEFG",null);
        costom_item_list_4.add(k);
        DecompressObjectItem j = new  DecompressObjectItem("12",R.mipmap.ic_launcher, "ABCDEFG",null);
        costom_item_list_4.add(j);
        DecompressObjectItem i = new  DecompressObjectItem("12",R.mipmap.ic_launcher, "ABCDEFG",null);
        costom_item_list_4.add(i);
    }

    private void initApapterData_5() {
        DecompressObjectItem l = new DecompressObjectItem("NO LOGIN", R.mipmap.ic_launcher, null,null);
        costom_item_list_5.add(l);
        DecompressObjectItem g = new DecompressObjectItem("使用帮助", R.mipmap.ic_launcher,null,">");
        costom_item_list_5.add(g);
        DecompressObjectItem f = new DecompressObjectItem("设置", R.mipmap.ic_launcher, null,">");
        costom_item_list_5.add(f);
        DecompressObjectItem e = new DecompressObjectItem("实验室", R.mipmap.ic_launcher, null,">");
        costom_item_list_5.add(e);

    }
/*
* 获取网络图片
* */
    public Bitmap getImageBitmap(String url) throws MalformedURLException {
        URL imgUrl = new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545125593562&di=76c352cc2414782ae6e5578a767d5a4e&imgtype=0&src=http%3A%2F%2Fwww.xnnews.com.cn%2Fwenyu%2Flxsj%2F201611%2FW020161114828261827516.jpg");
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
