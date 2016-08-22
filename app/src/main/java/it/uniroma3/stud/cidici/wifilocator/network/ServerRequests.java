package it.uniroma3.stud.cidici.wifilocator.network;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import org.json.JSONArray;

/**
 * Tutti i diritti riservati a Christian Micocci
 * Created by christian on 01/08/16.
 */
public class ServerRequests {

    public static final String BASE_URL = "http://ld16.lugroma3.org";

    public static RequestFuture<JSONArray> eventsList() {
        return getList(BASE_URL + "/ws/v1/events.json");
    }

    private static RequestFuture<JSONArray> getList(String url) {
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, future, future);
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 3, 1.1f));

        VolleySingleton.getInstance().getRequestQueue().add(request);
        return future;
    }

}
