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
package com.kevin.retrofitrxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kevin.retrofitrxjavademo.model.data.ArticleRepository;
import com.kevin.retrofitrxjavademo.model.data.JokeRepository;
import com.kevin.retrofitrxjavademo.model.entity.ArticleListResult;
import com.kevin.retrofitrxjavademo.model.entity.JokeResult;

import java.util.Random;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * MainActivity
 *
 * @author zwenkai@foxmail.com ,Created on 2017-09-28 20:08:06
 *         Major Function：<b>主界面</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView mTvContent;
    CompositeDisposable mSubscriptions;
    JokeRepository mJokeRepository;
    ArticleRepository mArticleRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mTvContent = (TextView) this.findViewById(R.id.tv_content);

        mSubscriptions = new CompositeDisposable();
        mJokeRepository = new JokeRepository(mSubscriptions);
        mArticleRepository = new ArticleRepository(mSubscriptions);
    }

    public void onJokeClick(View view) {
        Random random = new Random();
        int id = random.nextInt(11);
        mJokeRepository.getJokeById1(id)
                .subscribe(new Consumer<JokeResult>() {
                    @Override
                    public void accept(JokeResult jokeResult) {
                        // 处理返回数据
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        // 处理错误数据
                    }
                });

    }

    public void onArticleClick(View view) {
        mArticleRepository.getArticleList1(10, 1)
                .subscribe(new Consumer<ArticleListResult>() {
                    @Override
                    public void accept(ArticleListResult articleListResult) {
                        // 处理返回数据
                        Random random = new Random();
                        int index = random.nextInt(10);
                        mTvContent.setText(articleListResult.list.get(index).content);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        // 处理错误数据
                        Log.e(TAG, "accept() called with: throwable = [" + throwable + "]");
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }
}