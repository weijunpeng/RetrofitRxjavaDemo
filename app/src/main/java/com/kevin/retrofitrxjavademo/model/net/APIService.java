package com.kevin.retrofitrxjavademo.model.net;

import com.kevin.retrofitrxjavademo.model.entity.ArticleListResult;
import com.kevin.retrofitrxjavademo.model.entity.HttpResult;
import com.kevin.retrofitrxjavademo.model.entity.JokeResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhouwenkai on 2017/9/22.
 */

public interface APIService {

    @GET("getArticleList")
    Observable<HttpResult<ArticleListResult>> getArticleList1(
            @Query("pageSize") int pageSize,
            @Query("page") int page);


    @FormUrlEncoded
    @POST("getArticleList")
    Observable<HttpResult<ArticleListResult>> getArticleList2(
            @Field("pageSize") int pageSize,
            @Field("page") int page);

    @GET("getJoke")
    Observable<HttpResult<JokeResult>> getJokeById1(
            @Query("id") int id);

    @FormUrlEncoded
    @POST("getJoke")
    Observable<HttpResult<JokeResult>> getJokeById2(
            @Field("id") int id);
}
