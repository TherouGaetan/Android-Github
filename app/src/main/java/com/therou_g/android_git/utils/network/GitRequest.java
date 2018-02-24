package com.therou_g.android_git.utils.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.therou_g.android_git.utils.GitLog;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaetan
 *
 * On 18-02-24 at 16:57.
 */

public class GitRequest extends Request<String> {
    public enum GitRequestMethod {

        GET(Request.Method.GET),
        POST(Request.Method.POST),
        PUT(Request.Method.PUT),
        PATCH(Request.Method.PATCH),
        DELETE(Request.Method.DELETE);

        private int mMethod;

        GitRequestMethod(int pMethod) {
            mMethod = pMethod;
        }

        public int getMethod() {
            return mMethod;
        }
    }

    private static final String TAG = "Git: GitRequest";

    private static final String mApiInality = "";//AppController.get().getContext().getString(R.string.app_api_url);

    private static final String mToken = "";// = "Bearer " + Auth0User.getInstance().getTokenApi();

    private GitResponseListener mListener;

    private Map<String, String> mParams;

    /**
     * Configuration for a request to API's
     *
     * @param pEndPoint     Endpoint to call request
     */
    public GitRequest(String pEndPoint, GitResponseListener pListener) {
        this("GET", pEndPoint, pListener);
    }

    /**
     * Configuration for a request to API's
     *
     * @param pMethod       Method of this request (get, post, put, delete)
     * @param pEndPoint     Endpoint to call request
     */
    public GitRequest(String pMethod, String pEndPoint, GitResponseListener pListener) {
        this(pMethod, pEndPoint, pListener, mApiInality);
    }

    /**
     * Configuration for a request to API's
     *
     * @param pMethod       Method of this request (get, post, put, delete)
     * @param pEndPoint     Endpoint to call request
     */
    public GitRequest(String pMethod, String pEndPoint, GitResponseListener pListener, String pUri) {
        super(GitRequestMethod.valueOf(pMethod).getMethod(), pUri + pEndPoint, pListener);
        mListener = pListener;
        mParams = new HashMap<>();
        GitLog.log(TAG, "Create request \"" + pEndPoint + "\"");
    }

    /**
     *
     * @return  Type of this request
     */
    @Override
    public int getMethod() {
        return super.getMethod();
    }

    /**
     * Add single parameter to list
     *
     * @param pKey      Key for param
     * @param pValue    Value content
     */
    public void addParam(String pKey, String pValue) {
        mParams.put(pKey, pValue);
    }

    /**
     * Set the param list
     *
     * @param pParams   List of params to send
     */
    public void setParams(Map<String, String> pParams) {
        mParams = pParams;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse pResponse) {
        String parsed;
        try {
            parsed = new String(pResponse.data, HttpHeaderParser.parseCharset(pResponse.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(pResponse.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(pResponse));
    }

    @Override
    protected void deliverResponse(String pResponse) {
        mListener.onResponse(pResponse);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", mToken);
        headers.put("Client-Code", "android");
        GitLog.log(TAG, "AccessToken: " + mToken);
        return headers;
    }

    @Override
    protected Map<String,String> getParams() {
        return mParams;
    }
}
