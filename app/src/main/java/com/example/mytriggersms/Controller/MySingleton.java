package com.example.mytriggersms.Controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private Context ctx;
    private MySingleton mySingleton;
    private RequestQueue requestQueue;

    private MySingleton(Context context) {
        this.ctx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        return Volley.newRequestQueue(this.ctx);
    }

    public static synchronized MySingleton getInstance(Context context) {
        return new MySingleton(context);
    }

    public void addToRequest(Request request) {
        requestQueue.add(request);
    }
}
