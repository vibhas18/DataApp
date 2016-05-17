package com.example.datanetworkmodule;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.dineout.android.volley.DefaultRetryPolicy;
import com.dineout.android.volley.Request;
import com.dineout.android.volley.Request.Method;
import com.dineout.android.volley.RequestQueue;
import com.dineout.android.volley.RequestQueue.GlobalRequestQueueListener;
import com.dineout.android.volley.RequestQueue.RequestFilter;
import com.dineout.android.volley.Response;
import com.dineout.android.volley.Response.ErrorListener;
import com.dineout.android.volley.Response.Listener;
import com.dineout.android.volley.VolleyError;
import com.dineout.android.volley.toolbox.JsonObjectRequest;
import com.dineout.android.volley.toolbox.StringRequest;
import com.dineout.android.volley.toolbox.Volley;
import com.example.dineoutnetworkmodule.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DineoutNetworkManager
        implements
        RequestFilter,
        GlobalRequestQueueListener<Object> {

    public static String NETWORK_TYPE;

    private static RequestQueue requestQueue;
    private static String BASE_API_URL;
    private static HashMap<String, String> headers;
    private Context mContext;
    private Context context;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    //private static Tracker tracker;

    private DineoutNetworkManager(Context context, String savedBaseUrl) {

        mContext = context;
        // getHeaders(context);

        if (requestQueue == null) {

            BASE_API_URL = TextUtils.isEmpty(savedBaseUrl)
                    ? context.getString(R.string.base_url)
                    : savedBaseUrl;

            requestQueue = Volley.newRequestQueue(context, null,
                    Volley.DEFAULT_CACHE_DIR, 3, true, false);

            requestQueue.setGlobalRequestQueueListener(this);

            requestQueue.start();
        }
    }

    public static String getBaseAPIUrl() {
        return BASE_API_URL;
    }

    public static void setBaseAPIUrl(String baseApiUrl) {
        BASE_API_URL = baseApiUrl;
    }

    public static DineoutNetworkManager newInstance(Context context, String baseAPIUrl) {
        DineoutNetworkManager instance = new DineoutNetworkManager(context, baseAPIUrl);
        return instance;
    }

    public static String appendBaseUrlIfNeeded(String urlPath) {
        if (!TextUtils.isEmpty(urlPath) && !urlPath.startsWith("http")) {
            urlPath = BASE_API_URL + urlPath;
        }
        return urlPath;
    }

    public static String generateGetUrl(String url, Map<String, String> params) {
        Builder uriBuilder = Uri.parse(url).buildUpon();

        if (params != null) {
            Set<String> keySet = new TreeSet<>(params.keySet());

            for (String key : keySet) {
                String value = params.get(key);

                if (TextUtils.isEmpty(value)) { // Check for Empty
                    value = "";

                    uriBuilder.appendQueryParameter(key, value);

                } else if (value.contains("|")) { // Check for multiple values
                    String[] values = value.split("\\|");

                    for (String val : values) {
                        if (!TextUtils.isEmpty(val)) {
                            uriBuilder.appendQueryParameter(key, val);
                        }
                    }
                } else {
                    uriBuilder.appendQueryParameter(key, value);
                }
            }
        }

        //uriBuilder.appendQueryParameter(APP_VERSION, appVersionName);
        return uriBuilder.toString();
    }


    public HashMap<String, String> getHeaders(Context context) {

        headers = DataPreferences.getDefaultHeaders(context);

        return (HashMap<String, String>) DineoutNetworkManager.headers.clone();
    }

    public Request<?> jsonRequest(JsonObjectRequest request, int identifier,
                                  Map<String, String> requestMap, boolean shouldCache) {
        request.setIdentifier(identifier);
        request.setShouldCache(shouldCache);
        request.setTag(this);

        request.setHeaders((mContext != null)
                ? getHeaders(mContext)
                : DineoutNetworkManager.headers);
        request.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        addRequest(request);
        return request;
    }

    public Request<?> stringRequest(StringRequest request, int identifier,
                                    boolean shouldCache) {
        request.setIdentifier(identifier);
        request.setShouldCache(shouldCache);
        request.setTag(this);
        request.setHeaders((mContext != null)
                ? getHeaders(mContext)
                : null);
        request.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        addRequest(request);
        return request;
    }

    public void deleteRequest(Request<?> request) {
        request.setDeleteCache(true);
        addRequest(request);
    }

    public void addRequest(final Request<?> request) {
        if (!request.isDeleteCache() && request.hasHadResponseDelivered()) {
            throw new UnsupportedOperationException(
                    "Cannot reuse Request which has already served the request");
        }
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                requestQueue.add(request);
            }
        });
    }

    public Request<?> jsonRequestPost(int identifier, String url,
                                      Map<String, String> requestMap, Listener<JSONObject> listener,
                                      ErrorListener errorListener, boolean shouldCache) {
        JSONObject jsonRequest = (requestMap == null)
                ? null
                : new JSONObject(requestMap);

        if (!TextUtils.isEmpty(url) && !url.startsWith("http")) {
            url = BASE_API_URL + url;
        }

        JsonObjectRequest request = new JsonObjectRequest(url, jsonRequest,
                listener, errorListener, Method.POST);

        return jsonRequest(request, identifier, requestMap, shouldCache);
    }


    public Request<?> jsonRequestGet(int identifier, String urlPath,
                                     Map<String, String> requestMap, Listener<JSONObject> listener,
                                     ErrorListener errorListener, boolean shouldCache) {
        if (!TextUtils.isEmpty(urlPath) && !urlPath.startsWith("http")) {
            urlPath = BASE_API_URL + urlPath;
        }
        String url = generateGetUrl(urlPath, requestMap);

        return jsonRequest(identifier, url, listener, errorListener,
                shouldCache);
    }

    public Request<?> jsonRequest(int identifier, String url,
                                  JSONObject requestParameterObj, Listener<JSONObject> listener,
                                  ErrorListener errorListener, boolean shouldCache) {
        if (!TextUtils.isEmpty(url) && !url.startsWith("http")) {
            url = BASE_API_URL + url;
        }
        JsonObjectRequest request = new JsonObjectRequest(url,
                requestParameterObj, listener, errorListener);
        return jsonRequest(request, identifier, null, shouldCache);
    }

    public Request<?> jsonRequest(int identifier, String url,
                                  Listener<JSONObject> listener, ErrorListener errorListener,
                                  boolean shouldCache) {
        if (!TextUtils.isEmpty(url) && !url.startsWith("http")) {
            url = BASE_API_URL + url;
        }

        JsonObjectRequest request = new JsonObjectRequest(url, null, listener,
                errorListener);
        return jsonRequest(request, identifier, null, shouldCache);
    }

    public Request<?> stringRequestPost(int identifier, String url,
                                        Map<String, String> params, Listener<String> listener,
                                        ErrorListener errorListener, boolean shouldCache) {
        if (!TextUtils.isEmpty(url) && !url.startsWith("http")) {
            url = BASE_API_URL + url;
        }
//        url=generatePostUrlPayload(url,params);
        DineoutPostRequest request = new DineoutPostRequest(Method.POST, url,
                params, listener,
                errorListener);
        request.setIdentifier(identifier);
        request.setShouldCache(shouldCache);
        request.setTag(this);

        request.setHeaders((mContext != null)
                ? getHeaders(mContext)
                : DineoutNetworkManager.headers);
        request.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        addRequest(request);
        return request;
    }

    public void cancel() {
        requestQueue.cancelAll(this);
    }

    public boolean hasRunningRequests() {
        return requestQueue.hasRequestsWithTag(this);
    }

    @Override
    public boolean apply(Request<?> request) {
        return request.getTag() == this;
    }

    @Override
    public void onErrorResponse(Request request, VolleyError error) {

    }

    @Override
    public void onResponse(Request request, Object responseObject,
                           Response response) {
        final long startTimeInMillis = response.getStartTimeInMillis();
        final long endTimeInMillis = response.getEndTimeInMillis();

        if (startTimeInMillis > 0 && endTimeInMillis > 0) {
            long diff = (endTimeInMillis - startTimeInMillis);
//			HitBuilders.TimingBuilder time = new HitBuilders.TimingBuilder();
//			time.setCustomMetric(1, response.getDataSize());
//			time.setCustomDimension(1, String.valueOf(response.getDataSize()));
//			time.setCategory(response.isCachedResponse() ? "cached" : "live");
//			Uri parse = Uri.parse(request.getUrl());
//			time.setLabel(parse.getLastPathSegment());
//			time.setValue(diff);
//			time.setVariable(NETWORK_TYPE);
//			time.set("size", String.valueOf(response.getDataSize()));
//			tracker.setScreenName("NetworkLogging");
//			tracker.send(time.build());
        }
    }
}
