package marvel.h19.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import marvel.h19.ui.DataAdapter;

/**
 * Created by ankitkha on 25-Mar-16.
 */
public class WebService implements IWebService {
    public WebService(Context context) {

    }
    private static final String TAG = "WebServiceMock";

    @Override
    public void getAllCharacters(HttpRequest.IResponseCallBack callBack) {

    }

    @Override
    public void getComicsForCharacter(String characterId, HttpRequest.IResponseCallBack callBack) {

    }

    @Override
    public void searchCharacterWithName(String characterName, HttpRequest.IResponseCallBack callBack, String tag) {

    }

    @Override
    public void searchCharacterWithNameStartingWith(String characterName, HttpRequest.IResponseCallBack callBack, String tag) {

    }

    @Override
    public void getComicsForCharacterWithOffset(int limit, int offset, String characterId, HttpRequest.IResponseCallBack callBack) {

    }

    @Override
    public void getCharactersWithOffset(int limit, int offset, HttpRequest.IResponseCallBack callBack) {
        Log.d(TAG, " mock implementation empty");

        JSONObject obj = null;
        try {
            obj = new JSONObject(EMPTY_RESPONSE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callBack.onSuccess(obj);
    }

    @Override
    public void getComponentsWithOffset(int limit, int offset, String characterId, DataAdapter.AdapterType type, HttpRequest.IResponseCallBack callBack) {

    }

    @Override
    public void cancellAllRequestsWithTag(String tag) {

    }

    private static String EMPTY_RESPONSE ="{\n" +
            "  \"code\": 200,\n" +
            "  \"status\": \"Ok\",\n" +
            "  \"copyright\": \"© 2016 MARVEL\",\n" +
            "  \"attributionText\": \"Data provided by Marvel. © 2016 MARVEL\",\n" +
            "  \"attributionHTML\": \"<a href=\\\"http://marvel.com\\\">Data provided by Marvel. © 2016 MARVEL</a>\",\n" +
            "  \"etag\": \"79ef3436d0dc139b17693635b99776556e29f495\",\n" +
            "  \"data\": {\n" +
            "    \"offset\": 0,\n" +
            "    \"limit\": 20,\n" +
            "    \"total\": 0,\n" +
            "    \"count\": 0,\n" +
            "    \"results\": []\n" +
            "  }\n" +
            "}";
}
