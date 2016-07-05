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
    /*****************************************************************
     *              Retrofit 2.0 只能定义这样的模式(如下)
     *
     *              // Synchronous Call in Retrofit 2.0
     *              Call<Contributor> call = GitHubUrl.contributors();
     *              Contributor contributor = call.execute();
     *
     *                 // Synchronous Call in Retrofit 2.0
     *              Call<Contributor> call = GitHubUrl.contributors();
     *              call.enqueue(new Callback<Contributor>() {
     *                  @Override
     *                  public void onResponse(Response<Contributor> response) {
     *                      Get result Repo from response.body()
     *                  }
     *
     *                  @Override
     *                  public void onFailure(Throwable t) {
     *
     *              }
     *          });
     ********************************************************************/
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);




    /*****************************************************************
     *
     *         Retrofit 1.9 同步请求
     *       Synchronous in Retrofit 1.9  
     *  @GET("repos/{owner}/{repo}/contributors")
     *  List<Contributor> contributors(@Path("owner") String owner,@Path("repo") String repo);
     *
     * *************************************************************/

    /*****************************************************************
     *
     *         Retrofit 1.9 异步请求
     *      Asynchronous in Retrofit 1.9 
     *  @GET("repos/{owner}/{repo}/contributors")
     *  void contributors( @Path("owner") String owner, @Path("repo") String repo,Callback<Contributor> cb);
     *
     * *************************************************************/

}