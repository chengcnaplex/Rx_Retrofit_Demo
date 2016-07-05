package com.example.chengmengzhen.rx_retrofit_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.chengmengzhen.rx_retrofit_demo.bean.Contributor;
import com.example.chengmengzhen.rx_retrofit_demo.url.GitHubUrl;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends AppCompatActivity {
    public static final String API_URL = "https://api.github.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /********************************************************
         *      在Retrofit 1.9中，GsonConverter 包含在了package 中而且自动在RestAdapter创建的时候被初始化。
         *      这样来自服务器的son结果会自动解析成定义好了的Data Access Object（DAO）
         *
         *      但是在Retrofit 2.0中，Converter 不再包含在package 中了。
         *      你需要自己插入一个Converter 不然的话Retrofit 只能接收字符串结果。
         *      同样的，Retrofit 2.0也不再依赖于Gson 。
         *
         *      如果你想接收json 结果并解析成DAO，你必须把Gson Converter 作为一个独立的依赖添加进来。
         *
         *      compile 'com.squareup.retrofit:converter-gson:2.0.0-beta1'
         */
        // 如果你的公司用的服务器API_URL不会变，可以考虑封装起来
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 构建url框架
        GitHubUrl github = retrofit.create(GitHubUrl.class);

        // 给框架填入数据，成为一个完整的url
        final Call<List<Contributor>> call = github.contributors("square", "retrofit");
        // 发送url请求后的回调  异步请求
        call.enqueue(new Callback<List<Contributor>>() {
            /****************************************************************************
             *
             * 在Retrofit 1.9中，如果获取的 response 不能背解析成定义好的对象，则会调用failure。
             * 但是在Retrofit 2.0中，不管 response 是否能被解析。onResponse总是会被调用。
             * 但是在结果不能被解析的情况下，response.body()会返回null。别忘了处理这种情况。
             *
             ****************************************************************************/
            @Override
            public void onResponse(Response<List<Contributor>> response, Retrofit retrofit) {
                List<Contributor> contributors = response.body();
                for (Contributor contributor : contributors) {
                    Log.e("MainActivity", contributor.login + " (" + contributor.contributions + ")");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("MainActivity", t.toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
