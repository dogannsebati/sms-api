package com.example.mytriggersms.Controller;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyHttpRequest {

    private String url = "istek yapılacak site url";
    private Context ctx;

    public MyHttpRequest(Context ctx) {
        this.ctx = ctx;
    }

    public interface ResultListener {
        public void onReady(boolean isSuccess, String result);
    }
    public void verileriYolla(final String sender, final String content, final String password, final ResultListener resultListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        resultListener.onReady(true, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resultListener.onReady(false, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> values = new HashMap<>();
                values.put("Sender", sender);
                values.put("Content", content);
                values.put("Password", password);
                return values;
            }
        };
        MySingleton.getInstance(this.ctx).addToRequest(stringRequest);
    }

}
