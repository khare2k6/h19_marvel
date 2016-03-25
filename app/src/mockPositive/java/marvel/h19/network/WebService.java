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
        Log.d(TAG, " mock implementation Positive");

        JSONObject obj = null;
        try {
            obj = new JSONObject(POSITIVE_RESPONSE);
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

    private static String POSITIVE_RESPONSE ="{\n" +
            "  \"code\": 200,\n" +
            "  \"status\": \"Ok\",\n" +
            "  \"copyright\": \"© 2016 MARVEL\",\n" +
            "  \"attributionText\": \"Data provided by Marvel. © 2016 MARVEL\",\n" +
            "  \"attributionHTML\": \"<a href=\\\"http://marvel.com\\\">Data provided by Marvel. © 2016 MARVEL</a>\",\n" +
            "  \"etag\": \"a4f30ded56374e52b9a2c91528df8428cf0b2b05\",\n" +
            "  \"data\": {\n" +
            "    \"offset\": 0,\n" +
            "    \"limit\": 1,\n" +
            "    \"total\": 1485,\n" +
            "    \"count\": 1,\n" +
            "    \"results\": [\n" +
            "      {\n" +
            "        \"id\": 1011334,\n" +
            "        \"name\": \"3-D Man\",\n" +
            "        \"description\": \"\",\n" +
            "        \"modified\": \"2014-04-29T14:18:17-0400\",\n" +
            "        \"thumbnail\": {\n" +
            "          \"path\": \"http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784\",\n" +
            "          \"extension\": \"jpg\"\n" +
            "        },\n" +
            "        \"resourceURI\": \"http://gateway.marvel.com/v1/public/characters/1011334\",\n" +
            "        \"comics\": {\n" +
            "          \"available\": 11,\n" +
            "          \"collectionURI\": \"http://gateway.marvel.com/v1/public/characters/1011334/comics\",\n" +
            "          \"items\": [\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/21366\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #14\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/24571\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #14 (SPOTLIGHT VARIANT)\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/21546\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #15\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/21741\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #16\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/21975\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #17\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/22299\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #18\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/22300\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #18 (ZOMBIE VARIANT)\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/22506\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #19\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/10223\",\n" +
            "              \"name\": \"Marvel Premiere (1972) #35\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/10224\",\n" +
            "              \"name\": \"Marvel Premiere (1972) #36\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/10225\",\n" +
            "              \"name\": \"Marvel Premiere (1972) #37\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"returned\": 11\n" +
            "        },\n" +
            "        \"series\": {\n" +
            "          \"available\": 2,\n" +
            "          \"collectionURI\": \"http://gateway.marvel.com/v1/public/characters/1011334/series\",\n" +
            "          \"items\": [\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/series/1945\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007 - 2010)\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/series/2045\",\n" +
            "              \"name\": \"Marvel Premiere (1972 - 1981)\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"returned\": 2\n" +
            "        },\n" +
            "        \"stories\": {\n" +
            "          \"available\": 17,\n" +
            "          \"collectionURI\": \"http://gateway.marvel.com/v1/public/characters/1011334/stories\",\n" +
            "          \"items\": [\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/19947\",\n" +
            "              \"name\": \"Cover #19947\",\n" +
            "              \"type\": \"cover\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/19948\",\n" +
            "              \"name\": \"The 3-D Man!\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/19949\",\n" +
            "              \"name\": \"Cover #19949\",\n" +
            "              \"type\": \"cover\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/19950\",\n" +
            "              \"name\": \"The Devil's Music!\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/19951\",\n" +
            "              \"name\": \"Cover #19951\",\n" +
            "              \"type\": \"cover\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/19952\",\n" +
            "              \"name\": \"Code-Name:  The Cold Warrior!\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/47185\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #14 - Int\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/47499\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #15 - Int\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/47792\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #16\",\n" +
            "              \"type\": \"cover\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/47793\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #16 - Int\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/48362\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #17 - Int\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/49104\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #18 - Int\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/49106\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #18, Zombie Variant - Int\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/49888\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #19\",\n" +
            "              \"type\": \"cover\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/49889\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #19 - Int\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/54371\",\n" +
            "              \"name\": \"Avengers: The Initiative (2007) #14, Spotlight Variant - Int\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/96303\",\n" +
            "              \"name\": \"Deadpool (1997) #44\",\n" +
            "              \"type\": \"interiorStory\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"returned\": 17\n" +
            "        },\n" +
            "        \"events\": {\n" +
            "          \"available\": 1,\n" +
            "          \"collectionURI\": \"http://gateway.marvel.com/v1/public/characters/1011334/events\",\n" +
            "          \"items\": [\n" +
            "            {\n" +
            "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/events/269\",\n" +
            "              \"name\": \"Secret Invasion\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"returned\": 1\n" +
            "        },\n" +
            "        \"urls\": [\n" +
            "          {\n" +
            "            \"type\": \"detail\",\n" +
            "            \"url\": \"http://marvel.com/characters/74/3-d_man?utm_campaign=apiRef&utm_source=7d2c2442eae79d5468738ccbc32521ed\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"wiki\",\n" +
            "            \"url\": \"http://marvel.com/universe/3-D_Man_(Chandler)?utm_campaign=apiRef&utm_source=7d2c2442eae79d5468738ccbc32521ed\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"comiclink\",\n" +
            "            \"url\": \"http://marvel.com/comics/characters/1011334/3-d_man?utm_campaign=apiRef&utm_source=7d2c2442eae79d5468738ccbc32521ed\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";
}
