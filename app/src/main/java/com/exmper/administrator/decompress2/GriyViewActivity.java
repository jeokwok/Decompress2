package com.exmper.administrator.decompress2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GriyViewActivity extends AppCompatActivity {
    private static final String LOG_TAG = "GriyViewActivity";

    ImageView imageView;
    Button mDownload;
    Button mBack;
    EditText editText;
    Bitmap bitmap;
    String imgUrl;

    /*搜索图片网址的预网址头*/
    String webSearchHead = "http//:www.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*实现标题栏的隐藏*/
        try {
            if (getSupportActionBar() != null) {getSupportActionBar().hide();}
        } catch (Exception e)
        {
            Log.i(LOG_TAG,"no title is exception");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_griy_view);

       StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        imageView = (ImageView) findViewById(R.id.iv_imagedownload);
        mDownload = (Button)findViewById(R.id.btn_imagedownload_download);
        mBack = (Button) findViewById(R.id.btn_imagedownload_back);
        editText = (EditText)findViewById(R.id.et_imagedownload_edittext);

        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* try {
                    URL url = new URL(imgUrl);
                } catch (Exception e) {
                    Toast.makeText(GriyViewActivity.this, "未找到有效的图片地址", Toast.LENGTH_SHORT).show();
                }*/
                try {
                    imgUrl = editText.getText().toString();
                    bitmap =  getImageBitmap(imgUrl);
                   imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Toast.makeText(GriyViewActivity.this, "无法显示图片地址", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /*
* 获取网络图片
* */
    public Bitmap getImageBitmap(String path) throws IOException {
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == 200) {
                    InputStream inputStream = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
    }
}
