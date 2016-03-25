package marvel.h19.network;

import org.json.JSONObject;

/**
 * HTTP request class.
 * Wrapper for sending requests via Volley
 */
public class HttpRequest {
    /**
     * Web url of the http request
     */
    private String mUrl;
    /**
     * One of the type GET,POST,PUT.{@link HttpMethod}
     */
    private HttpMethod mMethodType;
    /**
     * Callback to be called after request is successfully completed or failed.
     * {@link IResponseCallBack}
     */
    private IResponseCallBack mCallback;
    /**
     * The request parameters to be sent to the server
     */
    private JSONObject mJsonRequestParams;


    private String mRequestTag;

    //getters
    public IResponseCallBack getmCallback() {
        return mCallback;
    }

    public String getmUrl() {
        return mUrl;
    }

    public HttpMethod getmMethodType() {
        return mMethodType;
    }

    public JSONObject getmJsonRequestParams() { return mJsonRequestParams; }

    public String getRequestTag() {return mRequestTag; }

    public void setmJsonRequestParams(JSONObject params) { mJsonRequestParams = params; }

    /**
     * Specifies type of http method
     */
    public enum HttpMethod{
        GET,
        POST,
        PUT
    };

    /**
     * Whichever class is creating this web request will give implementation of these methods which
     * will be called after response is received
     */
    public  interface IResponseCallBack{
        public void onFailure(String msg);
        public void onSuccess(JSONObject response);
    }

    /**
     * Constructor of http request
     */
    public HttpRequest(HttpRequestBuilder builder){
        mUrl = builder.Url;
        mMethodType = builder.MethodType;
        mCallback = builder.Callback;
        mJsonRequestParams = builder.JsonRequestParams;
        mRequestTag = builder.RequestTag;
    }

    /**
     * Builder class for creating an instance of above mentioned HttpRequest class
     */
    public static class HttpRequestBuilder{
         String Url;
         HttpMethod MethodType;
         IResponseCallBack Callback;
         JSONObject JsonRequestParams;
         String RequestTag;

        public HttpRequestBuilder setUrl(String url) {
            Url = url;
            return this;
        }

        public HttpRequestBuilder setMethodType(HttpMethod methodType) {
            MethodType = methodType;
            return this;
        }

        public HttpRequestBuilder setCallback(IResponseCallBack callback) {
            Callback = callback;
            return this;
        }

        public HttpRequestBuilder setJsonRequestParams(JSONObject jsonRequestParams) {
            JsonRequestParams = jsonRequestParams;
            return this;
        }

        public HttpRequestBuilder setRequestTag(String tag) {
            RequestTag = tag;
            return this;
        }

        public HttpRequest getNewHttpRequest(){
            return new HttpRequest(this);
        }

    }
}