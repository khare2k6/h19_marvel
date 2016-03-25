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
        Log.d(TAG, " mock implementation malformed");

        JSONObject obj = null;
        try {
            obj = new JSONObject(MALFORMED_RESPONSE);
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

    private static String MALFORMED_RESPONSE="{\n" +
            "  \"code\": 200,\n" +
            "  \"status\": \"Ok\",\n" +
            "  \"copyright\": \"© 2016 MARVEL\",\n" +
            "  \"attributionText\": \"Data provided by Marvel. © 2016 MARVEL\",\n" +
            "  \"attributionHTML\": \"<a href=\\\"http://marvel.com\\\">Data provided by Marvel. © 2016 MARVEL</a>\",\n" +
            "  \"etag\": \"dba3400fdf9f13c96425c48195b1441de97a2f75\"\n" +
            "}";
}
