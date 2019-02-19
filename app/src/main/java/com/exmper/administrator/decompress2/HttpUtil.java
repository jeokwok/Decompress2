package com.exmper.administrator.decompress2;

/**
 * Created by Administrator on 2019/1/5.
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class HttpUtil
{
    /**
     * 向对应的网址发送get请求,以String的形式返回服务器的相应
     *
     * @author cjyong at 2017/3/5
     * @param url 发送请求的网址
     * @param usecookie 是否使用cookie
     * @param cookie 需要携带的cookie
     * @param encoding 编码格式
     * @return 以string的形式返回服务器的响应
     * @throws Exception
     */
    public static String sendGetRequest(final String url,final boolean usecookie,final String cookie,final String encoding) throws Exception
    {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        URL turl = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) turl.openConnection();
                        //设置时间限制,抛出异常
                        conn.setConnectTimeout(5000);
                        conn.setReadTimeout(5000);
                        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        if(usecookie)
                            conn.setRequestProperty("Cookie", cookie);
                        InputStream is = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is,encoding));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = reader.readLine())!= null)
                            sb.append(line+"\n");
                        return sb.toString();
                    }
                });
        //格外进行一个线程进行网络操作,防止堵塞
        new Thread(task).start();
        return task.get();
    }

    /**
     * 向对应的网址发送post请求,以String的形式返回服务器的相应
     *
     * @author cjyong at 2017/3/5
     * @param url 发送请求的网址
     * @param data 发送post请求携带的数据
     * @param usecookie 是否使用cookie
     * @param cookie 需要携带的cookie
     * @param encoding 编码格式
     * @return 以string的形式返回服务器的响应
     * @throws Exception
     */
    public static String sendPostRequest(final String url,final String data,final boolean usecookie,final String cookie,final String encoding) throws Exception
    {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        URL turl = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) turl.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        //设置时间限制,抛出异常
                        conn.setConnectTimeout(5000);
                        conn.setReadTimeout(5000);
                        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        if(usecookie)
                            conn.setRequestProperty("Cookie", cookie);
                        OutputStream outStream = conn.getOutputStream();
                        outStream.write(data.getBytes());
                        outStream.flush();
                        outStream.close();
                        InputStream is = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is,encoding));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = reader.readLine())!= null)
                            sb.append(line+"\n");
                        return sb.toString();
                    }
                });
        //格外进行一个线程进行网络操作,防止堵塞
        new Thread(task).start();
        return task.get();
    }


    /**
     * 向对应的网址发送post请求,获取对应的cookie,以备后用
     *
     * @author cjyong at 2017/3/5
     * @param url 发送请求的网址
     * @param data 发送post请求携带的数据
     * @return 以string的形式返回服务器的响应
     * @throws Exception
     */

    public static String getCookie(final String url,final String data)throws Exception
    {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>()
                {

                    @Override
                    public String call() throws Exception
                    {
                        byte[] Data = data.getBytes();
                        URL turl=new URL(url);
                        HttpURLConnection conn = (HttpURLConnection)turl.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        //设置连接与读取时间过期返回异常
                        conn.setConnectTimeout(5000);
                        conn.setReadTimeout(5000);
                        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        OutputStream outStream = conn.getOutputStream();
                        outStream.write(Data);
                        outStream.flush();
                        outStream.close();
                        String Cookie=conn.getHeaderField("Set-Cookie");
                        return Cookie;
                    }

                }
        );
        //格外进行一个线程进行网络操作,防止堵塞
        new Thread(task).start();
        return task.get();
    }

}