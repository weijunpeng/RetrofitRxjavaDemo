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
package com.kevin.retrofitrxjavademo.model.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kevin.retrofitrxjavademo.model.entity.HttpResult;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import timber.log.Timber;

/**
 * ResultTransform
 *
 * @author zwenkai@foxmail.com ,Created on 2017-08-10 21:56:43
 *         Major Function：<b>数据实体转化器</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class ResultTransform<T> implements Function<HttpResult<T>, T> {

    private static final String TAG = "ResultTransform";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private ResultTransform() {
    }

    public static ResultTransform getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final ResultTransform INSTANCE = new ResultTransform();
    }

    @Override
    public T apply(@NonNull HttpResult<T> httpResult) throws Exception {
        if (!"OK".equals(httpResult.status)) {
            throw new APIException(httpResult.status, httpResult.msg);
        }
        try {
            String json = gson.toJson(httpResult);
            Timber.tag("Response").i(json);
        } catch (Exception e) {
            Timber.tag(TAG).w(e, "Error when serialize %s", httpResult.toString());
        }
        return httpResult.data;
    }
}
