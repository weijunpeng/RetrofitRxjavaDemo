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

import android.app.Application;

import com.kevin.hannibai.Hannibai;
import com.kevin.hannibai.converter.gson.GsonConverterFactory;
import com.kevin.retrofitrxjavademo.app.GlobalConfig;

import timber.log.Timber;

/**
 * DemoApplication
 *
 * @author zwenkai@foxmail.com ,Created on 2017-09-28 20:08:06
 *         Major Function：<b>Application</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GlobalConfig.init(this)
                .withApiHost("http://123.57.31.11/androidnet/")
                .withIsReleased(false)
                .configure();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Hannibai.init(this);
        Hannibai.setDebug(true);
        Hannibai.setConverterFactory(GsonConverterFactory.create());
    }
}
