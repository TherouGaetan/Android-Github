package com.therou_g.android_git.utils.network;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by gaetan
 *
 * On 18-02-24 at 16:58.
 */

public interface GitResponseListener extends Response.Listener<String>, Response.ErrorListener {
    @Override
    void onResponse(String response);

    @Override
    void onErrorResponse(VolleyError error);
}
