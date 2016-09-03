# Rx_Retrofit_Demo
Retrofit的例子说明。

## 初始化
```
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // 构建url框架
    GitHubUrl gitHubUrl = retrofit.create(GitHubUrl.class);

    // 给框架填入数据，成为一个完整的url
    call = gitHubUrl.contributors("square", "retrofit");
```

## 同步访问
```
    new Thread() {
        @Override
        public void run() {
            super.run();
            try {
                Response<List<Contributor>> response = call.execute();
                List<Contributor> contributors = response.body();
                for (Contributor contributor : contributors) {
                    Log.e("MainActivity", contributor.login + " (" + contributor.contributions + ")");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }.start();
```

## 异步访问
```
    call.enqueue(new Callback<List<Contributor>>() {
        @Override
        public void onResponse(Response<List<Contributor>> response, Retrofit retrofit) {

            List<Contributor> contributors = response.body();

            String showText = "";
            for (Contributor contributor : contributors)
                showText += contributor.login + ": " + contributor.contributions + "\n";

            ((TextView)findViewById(R.id.showText)).setText(showText);
        }
    }
```
