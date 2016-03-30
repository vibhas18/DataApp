package in.co.dineout.xoppin.dineoutcollection.utils;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by vibhaschandra on 01/02/16.
 */
public class DineoutPostRequest extends StringRequest {
    Map<String,String> params;
    public DineoutPostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public DineoutPostRequest(int method, String url, Map<String,String>
            params, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {

        this(method, url, listener, errorListener);
        this.params=params;

    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
