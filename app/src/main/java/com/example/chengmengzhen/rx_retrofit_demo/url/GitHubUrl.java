package com.example.chengmengzhen.rx_retrofit_demo.url;

import com.example.chengmengzhen.rx_retrofit_demo.bean.Contributor;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by chengmengzhen on 16/7/5.
 */
public interface GitHubUrl {
    /**
     * 使用GET方式访问服务器，将请求中的baseurl和这里的路径合并，这也是为什么baseurl中以/结尾的原因
     * 参数传递的值会通过Path注解，注入到请求路径中去。
     * 这里的访问路径是：https://api.github.com/repos/square/retrofit/contributors
     *
     * @param owner
     * @param repo
     * @return
     */
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);
}