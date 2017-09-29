/*
 * Copyright (c) 2017 Kevin zhou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kevin.retrofitrxjavademo.model.net.service;

import com.kevin.retrofitrxjavademo.model.entity.HttpResult;
import com.kevin.retrofitrxjavademo.model.entity.JokeResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * JokeService
 *
 * @author zwenkai@foxmail.com ,Created on 2017-09-29 11:31:09
 *         Major Function：<b>Joke WEB API</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public interface JokeService {

    @GET("getJoke")
    Observable<HttpResult<JokeResult>> getJokeById1(
            @Query("id") int id);

    @FormUrlEncoded
    @POST("getJoke")
    Observable<HttpResult<JokeResult>> getJokeById2(
            @Field("id") int id);
}
