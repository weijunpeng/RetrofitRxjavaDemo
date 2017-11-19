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

import com.kevin.retrofitrxjavademo.model.entity.HttpResult;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * HttpHelper
 *
 * @author zwenkai@foxmail.com ,Created on 2017-08-10 23:24:18
 *         Major Function：<b>网络访问帮助类</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class HttpHelper {

    public static <T> Observable<T> request(final CompositeDisposable subscriptions,
                                            final Observable<HttpResult<T>> observable) {
        return request(subscriptions, observable, null, null);
    }

    public static <T> Observable<T> request(final CompositeDisposable subscriptions,
                                            final Observable<HttpResult<T>> observable,
                                            final Consumer<T> success) {
        return request(subscriptions, observable, success, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> Observable<T> request(final CompositeDisposable subscriptions,
                                            final Observable<HttpResult<T>> observable,
                                            final Consumer<T> success,
                                            final Consumer<Throwable> error) {
        final RequestDisposable disposableWrap = new RequestDisposable();
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<T> e) throws Exception {
                disposableWrap.disposable = observable
                        .map(ResultTransform.getInstance())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn(ErrorReturnHolder.ERROR_RETURN)
                        .subscribe(
                                new Consumer<Object>() {
                                    @Override
                                    public void accept(Object o) throws Exception {
                                        if (o instanceof Throwable) {
                                            e.onError((Throwable) o);
                                            Timber.tag("HttpHelper.request()").w((Throwable) o);
                                        } else {
                                            e.onNext((T) o);
                                            e.onComplete();

                                            if (null != success) {
                                                success.accept((T) o);
                                            }
                                        }
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        if (null != disposableWrap.disposable) {
                                            subscriptions.remove(disposableWrap.disposable);
                                        }
                                        Timber.tag("HttpHelper.request()").w(throwable);
                                        e.onError(throwable);

                                        if (null != error) {
                                            error.accept(throwable);
                                        }
                                    }
                                },
                                new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        if (null != disposableWrap.disposable) {
                                            subscriptions.remove(disposableWrap.disposable);
                                        }
                                    }
                                });
                subscriptions.add(disposableWrap.disposable);
            }
        });
    }

    private static class RequestDisposable {
        Disposable disposable;
    }

    private static final class ErrorReturnHolder {

        private static final Function ERROR_RETURN = new Function<Throwable, Throwable>() {
            @Override
            public Throwable apply(@NonNull Throwable throwable) throws Exception {
                return throwable;
            }
        };
    }

}
