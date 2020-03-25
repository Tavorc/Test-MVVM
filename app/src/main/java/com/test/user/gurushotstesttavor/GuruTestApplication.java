package com.test.user.gurushotstesttavor;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import okhttp3.OkHttpClient;

public class GuruTestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setStetho();
    }

    private void setStetho(){
        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
        }
    }
}
