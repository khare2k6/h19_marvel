package marvel.h19.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import marvel.h19.ui.DataAdapter;
import marvel.h19.utils.Constants;

/**
 * Implementation of {@link IWebService} achieved via volley
 */
public class WebService implements IWebService {

    private static final String TAG = WebService.class.getSimpleName();

    private final Context mContext;

    public WebService(Context context) {
        this.mContext = context;
    }

    @Override
    public void getAllCharacters(HttpRequest.IResponseCallBack callBack) {
        JSONObject params = new JSONObject();
        try {
            long time = System.currentTimeMillis();
            params.put(Constants.KEY_API, Constants.API_KEY_MARVEL);
            params.put(Constants.KEY_HASH, Constants.getHash(time));
            params.put(Constants.KEY_TIME_STAMP, String.valueOf(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpRequest.HttpRequestBuilder builder = new HttpRequest.HttpRequestBuilder();
        builder.setUrl(getUrlWithParamsAppended(Constants.URL_ALL_CHARACTERS,params))
                .setMethodType(HttpRequest.HttpMethod.GET)
                .setCallback(callBack);

        NetworkConnection.getInstance(this.mContext).addHttpJsonRequest(builder.getNewHttpRequest());
    }
    /**
     * Takes base url and query params, appends params to url and returns the same. This is required
     * for GET requests with marvel server
     * @param url Base URL
     * @param params
     */
    public String getUrlWithParamsAppended(String url, JSONObject params) {
        if(params == null)
            return url;
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url);
        //params are not null, so append question mark to start adding params
        urlBuilder.append(Constants.KEY_QUESTION_MARK);
        //loop through each param build the url
        Iterator<String> keys = params.keys();
        while (keys.hasNext()) {
            try {
                String paramKey = keys.next();
                String paramValue = (String)params.get(paramKey);
                try {
                    paramValue= URLEncoder.encode(paramValue, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                urlBuilder.append(paramKey + Constants.KEY_EQUAL + paramValue);
                //add '&' only if still more params are pending
                if (keys.hasNext()) {
                    urlBuilder.append(Constants.KEY_AMPERCENT);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "returning url:" + urlBuilder.toString());
      //  Log.d(TAG, "returning url:" + URLEncoder.encode(urlBuilder.toString()),"UTF-8");
        return urlBuilder.toString();
    }

    @Override
    public void getComicsForCharacter(String characterId, HttpRequest.IResponseCallBack callBack) {
        JSONObject params = getCommonParams();
        String url = Constants.URL_ALL_CHARACTERS + Constants.FORWARD_SLASH+characterId + Constants.FORWARD_SLASH + Constants.COMICS;
        HttpRequest.HttpRequestBuilder builder = new HttpRequest.HttpRequestBuilder();
        builder.setUrl(getUrlWithParamsAppended(url,params))
                .setMethodType(HttpRequest.HttpMethod.GET)
                .setCallback(callBack);

        NetworkConnection.getInstance(this.mContext).addHttpJsonRequest(builder.getNewHttpRequest());
    }

    @Override
    public void searchCharacterWithName(String characterName, HttpRequest.IResponseCallBack callBack,String tag) {
        JSONObject params = getCommonParams();
        try {
            params.put(Constants.KEY_SEARCH_NAME, characterName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.HttpRequestBuilder builder = new HttpRequest.HttpRequestBuilder();
        builder.setUrl(getUrlWithParamsAppended(Constants.URL_ALL_CHARACTERS,params))
                .setMethodType(HttpRequest.HttpMethod.GET)
                .setRequestTag(tag)
                .setCallback(callBack);

        NetworkConnection.getInstance(this.mContext).addHttpJsonRequest(builder.getNewHttpRequest());

    }

    @Override
    public void searchCharacterWithNameStartingWith(String characterName, HttpRequest.IResponseCallBack callBack, String tag) {
        JSONObject params = getCommonParams();
        try {
            params.put(Constants.KEY_SEARCH_NAME_START_WITH, characterName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.HttpRequestBuilder builder = new HttpRequest.HttpRequestBuilder();
        builder.setUrl(getUrlWithParamsAppended(Constants.URL_ALL_CHARACTERS,params))
                .setMethodType(HttpRequest.HttpMethod.GET)
                .setRequestTag(tag)
                .setCallback(callBack);

        NetworkConnection.getInstance(this.mContext).addHttpJsonRequest(builder.getNewHttpRequest());
    }

    @Override
    public void getComicsForCharacterWithOffset(int limit, int offset, String characterId, HttpRequest.IResponseCallBack callBack) {
        JSONObject params = getCommonParams();
        try {
            params.put(Constants.KEY_LIMIT, String.valueOf(limit));
            params.put(Constants.KEY_OFFSET, String.valueOf(offset));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = Constants.URL_ALL_CHARACTERS + Constants.FORWARD_SLASH+characterId + Constants.FORWARD_SLASH + Constants.COMICS;
        HttpRequest.HttpRequestBuilder builder = new HttpRequest.HttpRequestBuilder();
        builder.setUrl(getUrlWithParamsAppended(url,params))
                .setMethodType(HttpRequest.HttpMethod.GET)
                .setCallback(callBack);

        NetworkConnection.getInstance(this.mContext).addHttpJsonRequest(builder.getNewHttpRequest());

    }

    /**
     * Marvel server requires hash,public key and time stamp to be in query params for each request
     * @return Json object with hash,public key and time stamp.
     */
    private JSONObject getCommonParams() {
        JSONObject params = new JSONObject();
        try {
            long time = System.currentTimeMillis();
            params.put(Constants.KEY_API, Constants.API_KEY_MARVEL);
            params.put(Constants.KEY_HASH, Constants.getHash(time));
            params.put(Constants.KEY_TIME_STAMP, String.valueOf(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    @Override
    public void getCharactersWithOffset(int limit, int offset, HttpRequest.IResponseCallBack callBack) {
        JSONObject params = getCommonParams();
        try {
            params.put(Constants.KEY_LIMIT, String.valueOf(limit));
            params.put(Constants.KEY_OFFSET, String.valueOf(offset));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.HttpRequestBuilder builder = new HttpRequest.HttpRequestBuilder();
        builder.setUrl(getUrlWithParamsAppended(Constants.URL_ALL_CHARACTERS,params))
                .setMethodType(HttpRequest.HttpMethod.GET)
                .setCallback(callBack);

        NetworkConnection.getInstance(this.mContext).addHttpJsonRequest(builder.getNewHttpRequest());

    }

    @Override
    public void getComponentsWithOffset(int limit, int offset, String characterId, DataAdapter.AdapterType type, HttpRequest.IResponseCallBack callBack) {
        JSONObject params = getCommonParams();
        try {
            params.put(Constants.KEY_LIMIT, String.valueOf(limit));
            params.put(Constants.KEY_OFFSET, String.valueOf(offset));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String component = null;
        switch (type) {
            case COMICS:
                component = Constants.COMICS;
                break;

            case SERIES:
                component = Constants.SERIES;
                break;

            case EVENTS:
                component = Constants.EVENTS;
                break;

            case STORIES:
                component = Constants.STORIES;
                break;

        }
        /* creating  url for comopnent http://gateway.marvel.com/v1/public/character unique id/component name like comics,events etc*/
        String url = Constants.URL_ALL_CHARACTERS + Constants.FORWARD_SLASH+characterId + Constants.FORWARD_SLASH + component;
        HttpRequest.HttpRequestBuilder builder = new HttpRequest.HttpRequestBuilder();
        builder.setUrl(getUrlWithParamsAppended(url,params))
                .setMethodType(HttpRequest.HttpMethod.GET)
                .setCallback(callBack);

        NetworkConnection.getInstance(this.mContext).addHttpJsonRequest(builder.getNewHttpRequest());
    }

    @Override
    public void cancellAllRequestsWithTag(String tag) {
        NetworkConnection.getInstance(this.mContext).cancelAllRequestWithTag(tag);
    }
}
