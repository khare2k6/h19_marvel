package marvel.h19.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener ,DataAdapter.IScrollListenor{
    // For calling all the marvel APIs
    private IWebService mWebService;
    //For listing all the characters
    private RecyclerView mRecycleView;
    private DataAdapter<Character> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog mProgressDialog;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //If any error occurs, show user some error message
    private TextView mTvErrorTitle;
    private String TAG = MainActivity.class.getSimpleName();


    @Override
    public void onRefresh() {
        Log.d(TAG, "pulled down for refreshing");
//        mSwipeRefreshLayout.setRefreshing(true);
        downloadCharactersList();
    }

    @Override
    public void onListEndReached(DataAdapter.AdapterType type) {
        Log.d(TAG, "List end reached ,fetch more data");
        downloadCharactersList();
    }

    /**
     * Defines two types of view states for activity.<li/>
     * <li>SHOW_CHARACTERS_LIST, when succesful call to marvel server is made, show the recycler view
     * <li>SHOW_ERROR_CONTENT, incase of any error , show try again or pull to refresh message
     */
    public enum ViewState {
        SHOW_CHARACTERS_LIST,
        SHOW_ERROR_CONTENT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mWebService = new WebService(this);
        this.mRecycleView = (RecyclerView) findViewById(R.id.recycler_view);
        this.mLayoutManager = new LinearLayoutManager(this);

        this.mRecycleView.setHasFixedSize(true);
        this.mRecycleView.setLayoutManager(this.mLayoutManager);
//        mProgressDialog = new ProgressDialog(this);

        mTvErrorTitle = (TextView) findViewById(R.id.tv_error);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        downloadCharactersList();
    }

    private void showProgressDialog(int title, int msg) {
        mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setTitle(title);
            mProgressDialog.setMessage(getResources().getString(msg));
            mProgressDialog.show();
     }

    private void clearProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected:" + item.getItemId());
        switch (item.getItemId()) {
            case R.id.search:
               // onSearchRequested();
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearProgressDialog();
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(null);
        }
        if (mRecycleView != null) {
            mRecycleView.addOnItemTouchListener(null);
        }
        if (mAdapter != null) {
            mAdapter.removeScrollListener(this);
            mAdapter = null;
        }
    }

    private void showAlertDialog(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.title_error);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(getString(R.string.title_ok), null);
        alertDialogBuilder.show();
    }
    /**
     * Changes visibility of different views of activity according to current state of call to
     * marvel server
     * @param state, {@link ViewState}
     */
    private void changeViewState(ViewState state) {
        Log.d(TAG, "changing state to " + state);
//        mTvErrorTitle.setText(msg);
        switch (state) {
            case SHOW_CHARACTERS_LIST:
                mRecycleView.setVisibility(View.VISIBLE);
                mTvErrorTitle.setVisibility(View.GONE);
                break;

            case SHOW_ERROR_CONTENT:
                mRecycleView.setVisibility(View.GONE);
                mTvErrorTitle.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void downloadCharactersList() {
        mSwipeRefreshLayout.setRefreshing(true);
        int limit = Constants.LIMIT;
        int offset = (mAdapter == null)?(Constants.INITIAL_OFFSET):(mAdapter.getItemCount());

//        showProgressDialog((R.string.title_loading), R.string.title_please_wait);

        this.mWebService.getCharactersWithOffset(limit, offset, new HttpRequest.IResponseCallBack() {

            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "onFailreu:" + msg);
                mSwipeRefreshLayout.setRefreshing(false);
//                clearProgressDialog();
                showAlertDialog(msg);
                changeViewState(ViewState.SHOW_ERROR_CONTENT);
            }

            @Override
            public void onSuccess(JSONObject response) {
//                Log.d(TAG, "onSuccess:" + response);
//                clearProgressDialog();
                Gson gson = new Gson();
                List<Character> list = new TranslateResponseToList<Character>().getList(response,Character[].class);

                if (mAdapter == null ) {
                    mAdapter = new DataAdapter<Character>(MainActivity.this,list , DataAdapter.AdapterType.CHARACTERS_LIST);
                    mRecycleView.setAdapter(mAdapter);
                    mRecycleView.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Log.d(TAG, "onClick on item on Main Character List:" + position);
                            SelectedCharacter.getInstance().setCharacter( mAdapter.getDataAtPosition(position));
                            Intent intent = new Intent(MainActivity.this, CharacterDetailsActivity.class);
                            startActivity(intent);
                        }
                    }));
                    mAdapter.registerScrollListener(MainActivity.this);

                }else{
                    mAdapter.addNewDataInCollection(list);
                    //mRecycleView.setAdapter(mAdapter);//this is new
                    mAdapter.notifyDataSetChanged();
                }
                 changeViewState(ViewState.SHOW_CHARACTERS_LIST);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
