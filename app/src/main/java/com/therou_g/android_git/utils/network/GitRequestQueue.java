package com.therou_g.android_git.utils.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.therou_g.android_git.AppController;
import com.therou_g.android_git.utils.GitLog;

/**
 * Created by gaetan
 *
 * On 18-02-24 at 16:57.
 */

public class GitRequestQueue {
    private static final String TAG = "AndroidGit: GitRequestQueue";

    /** queue for request app */
    private RequestQueue mRequestQueue;

    /** Imageloader */
    private ImageLoader mImageLoader;

    /**
     * Initialize a request queue, network and a cache
     */
    public GitRequestQueue() {
        GitLog.log(TAG, "Create instance");
        Cache mCache = new DiskBasedCache(AppController.get().getContext().getCacheDir(), 1024 * 1024);
        Network mNetwork = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(mCache, mNetwork);

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });

        start();
    }

    /**
     *
     * Add request {@param pRequest} in queue
     *
     * @param pRequest      Request to push in queue
     */
    public void add(GitRequest pRequest) {
        pRequest.setTag(AppController.get().getContext());
        mRequestQueue.add(pRequest);
    }

    /**
     * stop the request in queue
     */
    public void stop() {
        mRequestQueue.stop();
    }

    /**
     * Start request queue
     */
    private void start() {
        mRequestQueue.start();
    }

    /**
     * Cancel request in queue
     */
    public void cancelAll() {
        mRequestQueue.cancelAll(AppController.get().getContext());
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
