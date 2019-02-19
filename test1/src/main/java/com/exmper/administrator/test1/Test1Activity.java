package com.exmper.administrator.test1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Test1Activity extends AppCompatActivity {

    private ListView mListview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
    /*step 1  获取listview 控件*/
        mListview = (ListView) findViewById(R.id.lv1);

        List<User> userList = new ArrayList<>();
        for(int i = 'a'; i <= 'z';i++)
        {
            userList.add(new User("当前数据是"+ (char)i));
        }
        /*构建数据适配器*/
        MyAdapter myAdapter = new MyAdapter(this,userList);
        /*应用构建的适配器*/
        mListview.setAdapter(myAdapter);
    }
}
