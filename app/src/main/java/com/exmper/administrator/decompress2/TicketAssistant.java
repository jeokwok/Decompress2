package com.exmper.administrator.decompress2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TicketAssistant extends AppCompatActivity implements View.OnClickListener{
    private EditText mFromStation;
    private EditText mToStation;
    /*build the ticket query button member consturctor*/
    private Button mQuery;
    private Button mBack;
    private Button mStep;

    private EditText date;
    private Spinner to_spinner;
    private Spinner from_spinner;
    private TextView mTop;
    private TextView mLow;
    private TextView mWeatherType;

    ArrayAdapter<String> fromStation_adapter;
    ArrayAdapter<String> toStation_adapter;

    List<String> list = new ArrayList<String>();
    List<String> list_cityID = new ArrayList<String>();
    List<String> cityID = new ArrayList<String>();

    String mDate;
    String toStation_str;
    String fromStation_str;

    String to_str;
    String from_str;

   // TicketInfoAdapter ticketInfoAdapter;
    ListView ticketinfo_listview;

    private List<TicketInfo> costom_list= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getSupportActionBar() != null){getSupportActionBar().hide();}//隐藏标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_assistant);
        initializeView();
        mQuery.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mStep.setOnClickListener(this);

        from_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TicketAssistant.this,list.get(position),Toast.LENGTH_SHORT).show();
                fromStation_str = cityID.get(position);
                from_str = list.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        to_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TicketAssistant.this,list.get(position),Toast.LENGTH_SHORT).show();
                toStation_str = cityID.get(position);
                to_str = list.get(position).toString();
               //myHttpUrlConnection(cityID.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MyTicketDataAdapter myTicketDataAdapter = new MyTicketDataAdapter(this,costom_list);
        ticketinfo_listview.setAdapter(myTicketDataAdapter);
        //mFromStation.getText().toString();
        //mToStation.getText().toString();
       // View ticketinfo_view  = getLayoutInflater().inflate(R.layout.list_view,null);
        //ticketinfo_listview = (ListView)ticketinfo_view.findViewById(R.id.lv_listview);
        /*step1 获取控件 */

        /*数据*/
       // initializeApapterData();
        /*step2 构建ticketInfo适配器*/
         // ticketInfoAdapter = new
               //  TicketInfoAdapter(TicketAssistant.this,R.layout.custom_item_4, costom_list);
        /*step3 为listview绑定适配器*/
        //ticketinfo_listview.setAdapter(ticketInfoAdapter);

    }
     /*往适配器里面填充数据*/
    /*private void initializeApapterData() {

        TicketInfo data1 = new TicketInfo( from_str,"杭州东","G51","07:15","10:25","3小时10分","商务座","一等座","二等座","328.5");
       costom_list.add(data1);
        TicketInfo data2 = new TicketInfo("北京南","杭州东","G51","07:15","10:25","3小时10分","商务座","一等座","二等座","328.5");
        costom_list.add(data2);
        TicketInfo data3 = new TicketInfo("北京南","杭州东","G51","07:15","10:25","3小时10分","商务座","一等座","二等座","328.5");
        costom_list.add(data3);
        TicketInfo data4 = new TicketInfo("北京南","杭州东","G51","07:15","10:25","3小时10分","商务座","一等座","二等座","328.5");
        costom_list.add(data4);
        TicketInfo data5 = new TicketInfo("北京南","杭州东","G51","07:15","10:25","3小时10分","商务座","一等座","二等座","328.5");
        costom_list.add(data5);
    }*/

    private void initializeView() {

         mQuery = (Button) findViewById(R.id.btn_query);
         mBack = (Button) findViewById(R.id.btn_back);
         mStep = (Button) findViewById(R.id.btn_step);

        ticketinfo_listview = (ListView)findViewById(R.id.lv_ticketinfolistview);
        date = (EditText) findViewById(R.id.et_date);
        from_spinner = (Spinner)findViewById(R.id.spinner_formstation);
        to_spinner = (Spinner) findViewById(R.id.spinner);
        mTop = (TextView) findViewById(R.id.tv_top);
        mLow = (TextView) findViewById(R.id.tv_low);
        mWeatherType = (TextView) findViewById(R.id.tv_weathertype);

        list.add("北京");list_cityID.add("101010100");cityID.add("BJP");
        list.add("上海");list_cityID.add("101020100");cityID.add("SHH");
        list.add("杭州");list_cityID.add("101210101");cityID.add("HZH");
        list.add("合肥");list_cityID.add("101220101");cityID.add("HFH");
        list.add("六安");list_cityID.add("1");cityID.add("UAH");

        //list.add("");list_cityID.add("");cityID.add("");

        fromStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        from_spinner.setAdapter(fromStation_adapter);
        toStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        to_spinner.setAdapter(toStation_adapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_back:
                TicketAssistant.this.finish();
                break;
            case R.id.btn_step:
                //构建对话框 用alertdialog内部构建者Builder实现对话框的弹出 实现方法构建链
                AlertDialog.Builder builder = new AlertDialog.Builder(TicketAssistant.this)
                        .setTitle("more")
                        .setIcon(R.drawable.boos_boom);

                /*设置对话框的显示条目 并添加点击事件*/
                final String[] str = {"help","exit"};
                builder.setItems(str, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(str[which])
                        {
                            case"help":
                                Toast.makeText(TicketAssistant.this, "a  queay ticket app ", Toast.LENGTH_SHORT).show();

                                break;
                            case"exit":
                                Toast.makeText(TicketAssistant.this, "exit app", Toast.LENGTH_SHORT).show();
                                TicketAssistant.this.finish();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();

                break;
            case R.id.btn_query:
                mDate = date.getText().toString();
                myHttpUrlConnection(fromStation_str,toStation_str,mDate);
                //刷新适配器数据
                //ticketInfoAdapter.setNotifyOnChange(true);
                break;
            default:
                break;
        }
    }

    private void  myHttpUrlConnection(final String from_cityID, final String to_cityID, final String date){
        if(date != null ){
        new Thread(){
            @Override
            public void run() {
                BufferedReader bufferedReader = null;
                HttpURLConnection httpUrlConnection= null;
                URL url = null;
                try {//"http://www.weather.com.cn/weather1d/"+cityID".shtml"
                    //"http://www.weather.com.cn/date/cityinfo/"+ cityID+".html"
                    url = new URL(new UrlAPI().getUrlAPI(from_cityID,to_cityID,date));
                    httpUrlConnection = (HttpURLConnection) url.openConnection();
                    //httpUrlConnection.setRequestMethod("GET");
                    httpUrlConnection.setConnectTimeout(5000);
                   // httpUrlConnection.setReadTimeout(5000);
                    InputStream inputStream = httpUrlConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    StringBuffer response = new StringBuffer();
                    while ((line = bufferedReader.readLine()) != null)
                    {
                        response.append(line);
                    }
                    showResult(response.toString());

                  //  }
                } catch (Exception e)
                {
                    e.printStackTrace();//在命令行打印异常信息在程序中出错的位置及原因
                }finally {
                    if(httpUrlConnection!= null){
                        httpUrlConnection.disconnect();
                    }
                }

            }
        }.start();}
        else {
            Toast.makeText(TicketAssistant.this, "输入的日期不能为空或输入日期有误", Toast.LENGTH_SHORT).show();
        }
    }

     /*解析返回的字符*/
    private void showResult(final  String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTop.setText( s);
               try {
                   //JSONObject json = new JSONObject(s);
                   //JSONObject json1 = json.getJSONObject("data");
                   //String saa = json1.getString("map");

                   TicketInfoUtils ticketInfoUtils = new TicketInfoUtils(s);
                   costom_list = ticketInfoUtils.getTicketInfo();

                   JSONArray array = new JSONArray(s);
                   for(int i = 0; i<array.length(); i++)
                   {

                       JSONObject ob = (JSONObject) array.get(i);
                       String info = ob.getString("result");
                       mWeatherType.setText(info);


                   }





                   //JSONArray jsonArray = new JSONObject("data").getJSONObject("map").getJSONArray("result");
                   //for(int i = 0; i < jsonArray.length();i++)
                   //{
                     // jsonArray.getString(i);
                  //     System.out.println(i);
                 //  }

                  // JSONObject jsonObject = new JSONObject(s).getJSONObject("data");

                   //JSONObject jsonObject = new JSONObject(s);
                  // JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                   /*JSONObject jsonObject1=jsonObject;
                   String map = jsonObject.getString("map");
                   JSONArray result = jsonObject1.getJSONArray("result");

                   List<String> resultList = new ArrayList<>();
                   for(int i = 0; i < result.length(); i++)
                   {
                       String str = result.getString(i);
                       resultList.add(str);
                       //System.out.println(resultList);
                   }
                   TicketInfo ticketInfo = new TicketInfo();
                   ticketInfo.setMap(map);
                   ticketInfo.setTicketInfo(resultList);
                   System.out.println(ticketInfo.toString());*/

                   //System.out.println(s);
                  /* JSONArray jsonArray = new JSONArray(s);
                   for(int i=0; i<jsonArray.length();i++)
                   {
                       JSONObject jsonObject = jsonArray.getJSONObject(i);
                       String name = jsonObject.getString("result");
                       System.out.println(name);
                   }*/
                   /*JSONObject json = new JSONObject(s);
                   JSONObject json1 = json.getJSONObject("data");
                   JSONObject json2 = json1.getJSONObject("map");
                   //JSONObject json3 = json.getJSONObject("result");
                  // JSONObject json4 = json.getJSONObject("status");

                   String  a = json1.getString("flag");
                  mLow.setText(a);
                   from_str = a.toString();

                   String  b = json1.getString("map");
                   String  d = json2.getString("VNP");
                   String e = json2.getString("HGH");
                   mWeatherType.setText("车站号: "+ b +"出发站："+ d + "  到达站:" + e );


                  /* for(int i = 0; i<json1.getJSONArray("result").length();i++){
                        mTop.setText(json1.getJSONArray("result").getInt(i));
                   }*/
                   //String c = json1.getString("result");
                   //String f = json3.getString("预定");
                   //mTop.setText("总车次：" + c);


                  //Toast.makeText(TicketAssistant.this, "获取返回数据显示", Toast.LENGTH_SHORT).show();


                   /*JSONArray c = json.getJSONArray("result");
                   for(int i = 0;i < c.length(); i++)
                   {
                       String s1 = (String)c.get(i);
                       mTop.setText(s1);
                   }

                   String p = (String) c.get(0);
                   mLow.setText(p);
                   JSONObject json1 = json.getJSONObject("data");
                    String a  = json1.getString("map");
                    mWeatherType.setText("车站号："+ a );
                    a = json1.getString("result");
                    mTop.setText("总车次：" + a);
                   for(int i = 0; i<a.length(); i++)
                   {
                       int s = 0 , d = 0;
                       a.indexOf("预定", s);
                       a.indexOf("Y", d);
                       String b = a.substring(s,d);
                       mLow.setText(b);
                   }*/


                   //String b = a.substring(a.indexOf("预定"),a.indexOf("Y"));
                   //mLow.setText(b);
                 //JSONArray array = new JSONArray(s);
                   //for(int i = 0; i<array.length();i++)
                    //{

                        //String[] b = getString(array[i]);
                        //String b = getString()
                    //}
                   //mLow.setText("车次：" + json1.getString("预定"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*********public class TicketInfoAdapter extends ArrayAdapter{

        private int resourceId;
       // private  List<TicketInfo> mDatas;

        public TicketInfoAdapter(Context context, int textViewResourceId, List<TicketInfo> objects) {
            super(context, textViewResourceId, objects);
           this.resourceId = textViewResourceId;
           // this.mDatas = objects;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TicketInfo info = (TicketInfo) getItem(position);
            View view =LayoutInflater.from(getContext()).inflate(resourceId, null);

            TextView fromStation = (TextView) view.findViewById(R.id.fromstation);
            TextView toStation = (TextView) view.findViewById(R.id.tostation);
            TextView num = (TextView) view.findViewById(R.id.num);
            TextView fromDate = (TextView) view.findViewById(R.id.fromdate);
            TextView toDate = (TextView) view.findViewById(R.id.todate);
            TextView time = (TextView) view.findViewById(R.id.time);
            TextView softSet = (TextView) view.findViewById(R.id.softset);
            TextView oneSet = (TextView) view.findViewById(R.id.oneset);
            TextView twoSet = (TextView) view.findViewById(R.id.twoset);
            TextView price = (TextView) view.findViewById(R.id.price);

            fromStation.setText(info.getFromStation());
            toStation.setText(info.getToStation());
            num.setText(info.getNum());
            fromDate.setText(info.getFromDate());
            toDate.setText(info.getToDate());
            time.setText(info.getTime());
            softSet.setText(info.getSoftSet());
            oneSet.setText(info.getOneSet());
            twoSet.setText(info.getTwoSet());
            price.setText(info.getPrice());

            return view;
        }

    }*/

}
