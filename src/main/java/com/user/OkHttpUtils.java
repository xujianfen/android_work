package com.user;

/**
 * Created by 枫 on 2018/8/9.
 */
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 刘楠 on 2016-03-27.
 */
public class OkHttpUtils {

    OkHttpClient client = new OkHttpClient();

    /**
     * 获取流
     * @param url 请求地址
     * @return 输入流
     */
    public InputStream getInpuStream(String url) throws IOException {
        //设置 请求
        Request request = new Request.Builder()
                .url(url).build();


        //获取行响应

        InputStream in = client.newCall(request).execute().body().byteStream();

        return in;


    }

    /**
     * 返回字符串
     * @param url
     * @return 返回字符串
     * @throws IOException
     */
    public String getString(String url) throws IOException {
        //设置 请求
        Request request = new Request.Builder()
                .url(url).build();


        //获取行响应

        Response response = client.newCall(request).execute();

        return response.body().string();


    }

}

