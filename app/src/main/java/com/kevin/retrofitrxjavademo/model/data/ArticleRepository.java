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
package com.kevin.retrofitrxjavademo.model.data;

import com.kevin.hannibai.Hannibai;
import com.kevin.retrofitrxjavademo.model.entity.ArticleListResult;
import com.kevin.retrofitrxjavademo.model.net.HttpBuilder;
import com.kevin.retrofitrxjavademo.model.net.HttpHelper;
import com.kevin.retrofitrxjavademo.preference.DemoPreferenceHandle;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * ArticleRepository
 *
 * @author zwenkai@foxmail.com ,Created on 2017-09-29 11:23:57
 *         Major Function：<b>ArticleListResult</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class ArticleRepository extends DataRepository {

    private DemoPreferenceHandle mPreferenceHandle;

    public ArticleRepository(CompositeDisposable subscriptions) {
        super(subscriptions);
        mPreferenceHandle = Hannibai.create(DemoPreferenceHandle.class);
    }

    public Observable<ArticleListResult> getArticleList1(int pageSize, int page) {
        if (mPreferenceHandle.containsArticleListResult()) {
            Timber.tag("Fuck").d("sp获取");
            return mPreferenceHandle.getArticleListResult1();
        }

        Timber.tag("Fuck").d("网络 获取");
        return HttpHelper.request(mSubscriptions,
                HttpBuilder.getArticleService().getArticleList1(pageSize, page),
                new Consumer<ArticleListResult>() {
                    @Override
                    public void accept(ArticleListResult articleListResult) throws Exception {
                        mPreferenceHandle.setArticleListResult(articleListResult);
                    }
                });
    }

    public Observable<ArticleListResult> getArticleList2(int pageSize, int page) {
        return HttpHelper.request(mSubscriptions, HttpBuilder.getArticleService().getArticleList2(pageSize, page));
    }

}
