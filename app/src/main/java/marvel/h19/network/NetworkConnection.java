package marvel.h19.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Network Connection class , a wrapper over volley apis
 */
public class NetworkConnection {
    /*singleton instance*/
    private static NetworkConnection mInstance;
    /* volley request queue*/
    private RequestQueue mRequestQueue;
    private Context mContext;
    private String TAG = NetworkConnection.class.getSimpleName();
    /*Used for fetching images over network*/
    private ImageLoader mImageLoader;

    /**
     * Get singleton instance of volley wrapper class.
     * @param context
     * @return NetworkConnection's instance
     */
    public static NetworkConnection getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkConnection(context);
        }
        return mInstance;
    }

    private NetworkConnection(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mContext = context;
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });
    }

    /**
     * Maps {@link HttpRequest.HttpMethod} with volley's Request.Method types
     * @param methodType i.e GET,PUT,POST
     * @return volley supported Methods
     */
    public int getMethod(HttpRequest.HttpMethod methodType) {
        switch (methodType) {
            case GET:
                return Request.Method.GET;
            case POST:
                return Request.Method.POST;
            case PUT:
                return Request.Method.PUT;
            default:
                return Request.Method.GET;
        }
    }

    /**
     * Gets volley's imageLoader required for downloading images on web and loading on NetworkImageView
     * @return ImageLoader instance
     */
    public ImageLoader getImageLoader(){
        return mImageLoader;
    }

    /**
     * Adds a request to volley queue which takes json parameters and returns JsonObject in
     * callback response.
     * @param request {@link HttpRequest} having details like url, params, callback.
     */
    public void addHttpJsonRequest(final HttpRequest request){
        JsonObjectRequest newVolleyReqest = new JsonObjectRequest(
                //method type of request
                getMethod(request.getmMethodType()),
                //url of request
                request.getmUrl(),
                //json request params
                request.getmJsonRequestParams(),
                //success listner
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG,"on response received.."+response);
                        HashMap<String,JSONObject>resultMap = new HashMap<>();
                        resultMap.put("results", response);
                        request.getmCallback().onSuccess(response);
                    }
                },
                //error listener
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "error obj = " + error);
                            request.getmCallback().onFailure(error.getMessage());
                    }
                }
        );
        if (!TextUtils.isEmpty(request.getRequestTag())) {
            newVolleyReqest.setTag(request.getRequestTag());
        }
        mRequestQueue.add(newVolleyReqest);
    }

    /**
     * Cancell all pending requests from volley queue with given tag
     * @param tag
     */
    public void cancelAllRequestWithTag(String tag) {
        mRequestQueue.cancelAll(tag);
    }
}
