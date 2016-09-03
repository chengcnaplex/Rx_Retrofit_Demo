package com.example.chengmengzhen.rx_retrofit_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chengmengzhen.rx_retrofit_demo.bean.Contributor;
import com.example.chengmengzhen.rx_retrofit_demo.url.GitHubUrl;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.functions.Action1;


public class MainActivity extends AppCompatActivity {
    public static final String API_URL = "https://api.github.com/";
    private Call<List<Contributor>> call;

    @InjectView(R.id.sync)
    public Button sync;
    @InjectView(R.id.async)
    public Button async;
    @InjectView(R.id.rxjava)
    public Button rxjava;
    @InjectView(R.id.clean)
    public Button clean;

    public String showText = "";
    public GitHubUrl gitHubUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);  // 注入注解对象

        accessRemoteInit();

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncAccessRemote();
            }
        });

        async.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asyncAccessRemote();
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView)findViewById(R.id.showText)).setText("");
            }
        });
    }

    private void asyncAccessRemote() {
        // 给框架填入数据，成为一个完整的url
        call = gitHubUrl.contributors("square", "retrofit");

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

                for (Contributor contributor : contributors)
                    showText += contributor.login + ": " + contributor.contributions + "\n";

                ((TextView)findViewById(R.id.showText)).setText(showText);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("MainActivity", t.toString());
            }
        });
    }

    private void syncAccessRemote() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    // 给框架填入数据，成为一个完整的url
                    call = gitHubUrl.contributors("square", "retrofit");

                    Response<List<Contributor>> response = call.execute();

                    List<Contributor> contributors = response.body();
                    for (Contributor contributor : contributors)
                        showText += contributor.login + ": " + contributor.contributions + "\n";

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView)findViewById(R.id.showText)).setText(showText);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void accessRemoteInit() {
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
        gitHubUrl = retrofit.create(GitHubUrl.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        call.cancel();
    }
}
