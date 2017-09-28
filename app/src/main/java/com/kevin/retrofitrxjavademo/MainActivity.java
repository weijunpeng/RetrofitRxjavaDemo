package com.kevin.retrofitrxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kevin.retrofitrxjavademo.model.entity.ArticleListResult;
import com.kevin.retrofitrxjavademo.model.entity.JokeResult;
import com.kevin.retrofitrxjavademo.model.net.HttpBuilder;
import com.kevin.retrofitrxjavademo.model.net.HttpHelper;

import java.util.Random;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView mTvContent;
    CompositeDisposable mSubscriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mTvContent = (TextView) this.findViewById(R.id.tv_content);

        mSubscriptions = new CompositeDisposable();
    }

    public void onJokeClick(View view) {
        Random random = new Random();
        int id = random.nextInt(11);
        HttpHelper.request(mSubscriptions, HttpBuilder.getAPIService().getJokeById1(id))
                .subscribe(new Consumer<JokeResult>() {
                    @Override
                    public void accept(JokeResult jokeResult) {
                        // 处理返回数据
                        mTvContent.setText(jokeResult.joke);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        // 处理错误数据
                        Log.e(TAG, "accept() called with: throwable = [" + throwable + "]");
                    }
                });
    }

    public void onArticleClick(View view) {
        Random random = new Random();
        int id = random.nextInt(5);
        HttpHelper.request(mSubscriptions, HttpBuilder.getAPIService().getArticleList2(2, id))
                .subscribe(new Consumer<ArticleListResult>() {
                    @Override
                    public void accept(ArticleListResult articleListResult) {
                        // 处理返回数据
                        mTvContent.setText(articleListResult.list.get(0).content);
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