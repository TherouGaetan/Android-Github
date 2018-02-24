package com.therou_g.android_git;

import android.app.Application;
import android.content.Context;

import com.therou_g.android_git.utils.network.GitRequestQueue;

/**
 * Created by gaetan
 *
 * On 18-02-24 at 17:06.
 */

public class AppController extends Application {
    private GitRequestQueue mRequestQueue = null;

    private static AppController mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController get() {
        return mInstance;
    }

    public GitRequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = new GitRequestQueue();
        }
        return this.mRequestQueue;
    }

    public Context getContext() {
        return getApplicationContext();
    }
}
