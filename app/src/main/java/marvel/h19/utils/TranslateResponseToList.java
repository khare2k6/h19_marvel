package marvel.h19.utils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Helper generic class converts json response object received from server for any of the GET
 * queries and converts it to appropriate component type i.e. Comic,Event,Character or Story
 */
public class TranslateResponseToList<T> {
    private static final String TAG = TranslateResponseToList.class.getSimpleName();

    /**
     * Takes JsonObject received in the response which is having a json array listed
     * against key "results". This json array is array of components received as result of query.
     * Returns a list of component type.
     * @param response json object received as response of the http GET call for the component.
     * @param cls generic class object for array of component required by gson to json array to objects.
     *            eg Character[].class
     * @return list containing translated component objects.
     */
    public List<T> getList(JSONObject response, final Class<T[]> cls) {
        JSONObject object = null;
        JSONArray jsonArray = null;
        try {
            object = (JSONObject) response.get(Constants.KEY_DATA);
            jsonArray = (JSONArray) object.get(Constants.KEY_RESULTS);
            T[] some = new Gson().fromJson(jsonArray.toString(), cls);
            return new LinkedList<T>(Arrays.asList(some));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


//    private Context mContext;
//    private String mCharacterId;
//    private DataAdapter.AdapterType mAdapterType;
//    private DataAdapter<T> mAdapter;
//    private RecyclerView mRecyclerView;
//    public TranslateResponseToList(){}
//    public TranslateResponseToList(Context context, String characterId, DataAdapter.AdapterType type) {
//        mContext = context;
//        mCharacterId = characterId;
//        mAdapterType = type;
//    }
//    /**
//     * // Not used now
//     * Returns the current size of the adapter
//     * @return
//     */
//    public int getAdapterSize() {
//        return (mAdapter == null) ? 0 : mAdapter.getItemCount();
//    }
//
//    /**
//     * // Not used now
//     * Generic API which gets response for a component, creates and maintains its adapter. So from
//     * activity no need to have different onSuccess and onFailure implementation
//     * @param adapter {@link DataAdapter} of component to be fetched
//     * @param rc recyclerView to display results for respective adapter
//     * @param listener {@link marvel.h19.ui.DataAdapter.IScrollListenor} to inform end has reached.
//     * @param cls class type of component's array class, required by gson
//     */
//    public void downloadList( DataAdapter<T> adapter, RecyclerView rc, final DataAdapter.IScrollListenor listener, final Class<T[]> cls) {
//        if (mAdapter == null) {
//            mAdapter = adapter;
//        }
//        if (mRecyclerView == null) {
//            mRecyclerView = rc;
//        }
//        int limit = Constants.LIMIT;
//        int offset = (mAdapter == null) ? (Constants.INITIAL_OFFSET) : (mAdapter.getItemCount());
//        IWebService webService = new WebService(mContext);
//        webService.getComponentsWithOffset(limit, offset, mCharacterId, mAdapterType, new HttpRequest.IResponseCallBack() {
//            @Override
//            public void onFailure(String msg) {
//
//            }
//
//            @Override
//            public void onSuccess(JSONObject response) {
//                Log.d(TAG, "onSuccess:"+mAdapterType);
//                Gson gson = new Gson();
//                //List<Comic> list = null;
//                Type type = new TypeToken<T[]>() {
//                }.getType();
//                List<T> list = getList(response, cls);
//
//                if (mAdapter == null) {
//                    mAdapter = new DataAdapter<T>(mContext, list, mAdapterType);
//                    mRecyclerView.setAdapter(mAdapter);
//                    mAdapter.registerScrollListener(listener);
//                } else {
//                    mAdapter.addNewDataInCollection(list);
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//        });
//    }
}
