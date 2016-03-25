package marvel.h19.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import marvel.h19.R;
import marvel.h19.network.HttpRequest;
import marvel.h19.network.IWebService;
import marvel.h19.network.WebService;
import marvel.h19.schema.Character;
import marvel.h19.utils.Constants;
import marvel.h19.utils.TranslateResponseToList;

/**
 * Activity for allowing user to search character by name.
 */
public class SearchActivity extends AppCompatActivity implements TextWatcher{
    private static final String TAG = SearchActivity.class.getSimpleName();
    private RecyclerView mSearchListRecyclerView;
    private LinearLayoutManager mLayoutManger;
    private EditText mEtSearch;
    private ThisHandler mHandler;
    private DataAdapter<Character> mSearchCharacterAdapter;
    private IWebService mWebService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchListRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mLayoutManger = new LinearLayoutManager(this);
        mWebService = new WebService(this);
        mHandler = new ThisHandler();
        mSearchListRecyclerView.setLayoutManager(mLayoutManger);
        mEtSearch.addTextChangedListener(this);

        mSearchListRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onClick on item on Searched Character List:" + position);
                SelectedCharacter.getInstance().setCharacter( mSearchCharacterAdapter.getDataAtPosition(position));
                Intent intent = new Intent(SearchActivity.this, CharacterDetailsActivity.class);
                startActivity(intent);
            }
        }));
    }

    private void showAlertDialog(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.title_error);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(getString(R.string.title_ok), null);
        alertDialogBuilder.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "afterTextChanged,new S->" + s.toString());
        //if handler has any message to download character list, cancel it.
        if (mHandler.hasMessages(ThisHandler.MSG_TEXT_ENTERED)) {
            mHandler.removeMessages(ThisHandler.MSG_TEXT_ENTERED);
            Log.d(TAG, "previous msg removed from handler");
        }
        Bundle bundle = new Bundle();
        bundle.putString(ThisHandler.NEW_STRING, s.toString());

        Message msg = new Message();
        msg.what = ThisHandler.MSG_TEXT_ENTERED;
        msg.setData(bundle);
        //send delayed message to handler to get characters list from server
        mHandler.sendMessageDelayed(msg, Constants.DELAY);
    }

    private void searchCharacters(String name) {
        if (TextUtils.isEmpty(name)) {
            Log.d(TAG, "search string cannot be empty");
            return;
        }

        this.mWebService.searchCharacterWithNameStartingWith(name, new HttpRequest.IResponseCallBack() {

            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "onFailreu:" + msg);
                showAlertDialog(msg);
            }

            @Override
            public void onSuccess(JSONObject response) {
                Gson gson = new Gson();
                List<Character> list = new TranslateResponseToList<Character>().getList(response,Character[].class);
                    mSearchCharacterAdapter = new DataAdapter<Character>(SearchActivity.this,list , DataAdapter.AdapterType.SEARCH);
                    mSearchListRecyclerView.setAdapter(mSearchCharacterAdapter);
            }
        },SearchActivity.class.getSimpleName());
    }

    /**
     * Delayed messages are sent to this handler to perform search on the basis of partial name
     * entered for search and provide suggestion to the user
     */
    private class ThisHandler extends Handler{
        public static final int MSG_TEXT_ENTERED = 100;
        public static final String NEW_STRING = "new_string";

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_TEXT_ENTERED:
                    String updatedString = msg.getData().getString(NEW_STRING);
                    Log.d(TAG, "new string received" + updatedString);
                    mWebService.cancellAllRequestsWithTag(SearchActivity.class.getSimpleName());
                    searchCharacters(updatedString);
                    break;
            }
        }
    }
}
