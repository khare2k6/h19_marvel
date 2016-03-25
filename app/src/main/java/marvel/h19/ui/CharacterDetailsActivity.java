package marvel.h19.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import marvel.h19.R;
import marvel.h19.network.HttpRequest;
import marvel.h19.network.IWebService;
import marvel.h19.network.NetworkConnection;
import marvel.h19.network.WebService;
import marvel.h19.schema.Character;
import marvel.h19.schema.Comic;
import marvel.h19.schema.Event;
import marvel.h19.schema.SeriesSingular;
import marvel.h19.schema.Story;
import marvel.h19.schema.Url;
import marvel.h19.utils.Constants;
import marvel.h19.utils.TranslateResponseToList;

/**
 * Activity showing Detail view for selected character.
 */
public class CharacterDetailsActivity extends AppCompatActivity implements DataAdapter.IScrollListenor{
    private static final String TAG = CharacterDetailsActivity.class.getSimpleName();
    /*Character object for which detail view has to be shown*/
    private Character mCharacter;
    /*To show image of the character*/
    private NetworkImageView mNivCharacterImage;
    /*Character name*/
    private TextView mTvCharacterName;
    /*Character description*/
    private TextView mTvCharacterDescription;
    /*Recycler views for comics, events, series and stories*/
    private RecyclerView mRvComicsList,mRvEventsList,mRvSeriesList,mRvStoriesList;
    /*Comics data adapter*/
    private DataAdapter<Comic> mComicsAdapter;
    /*Events data adapter*/
    private DataAdapter<Event> mEventsAdapter;
    /*Series data adapter*/
    private DataAdapter<SeriesSingular> mSeriesAdapter;
    /*Stories data adapter*/
    private DataAdapter<Story> mStoryAdapter;
    /*Respective horizontal layout managers*/
    private RecyclerView.LayoutManager mComicsLayoutManager,mEventsLayoutManager,mSeriesLayoutManager,mStoriesLayoutManager;
    /*Related links provided for character like Detail url, wiki url etc*/
    private LinearLayout mRelatedLinkContainer;
    /*To make http calls to server*/
    private IWebService mWebService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        mCharacter = SelectedCharacter.getInstance().get();

        mTvCharacterName = (TextView) findViewById(R.id.tv_character_name_detail_view);
        mTvCharacterDescription = (TextView) findViewById(R.id.tv_description);
        mNivCharacterImage = (NetworkImageView) findViewById(R.id.niv_character_image_detail_view);
        mRvComicsList = (RecyclerView) findViewById(R.id.rv_comics_list);
        mRvEventsList = (RecyclerView) findViewById(R.id.rv_events_list);
        mRvStoriesList = (RecyclerView) findViewById(R.id.rv_stories_list);
        mRvSeriesList = (RecyclerView) findViewById(R.id.rv_series_list);
        mRelatedLinkContainer = (LinearLayout) findViewById(R.id.ll_related_link_container);

        mComicsLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mEventsLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mSeriesLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mStoriesLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        mRvComicsList.setHasFixedSize(true);
        mRvEventsList.setHasFixedSize(true);
        mRvSeriesList.setHasFixedSize(true);
        mRvStoriesList.setHasFixedSize(true);

        mRvComicsList.setLayoutManager(mComicsLayoutManager);
        mRvEventsList.setLayoutManager(mEventsLayoutManager);
        mRvSeriesList.setLayoutManager(mSeriesLayoutManager);
        mRvStoriesList.setLayoutManager(mStoriesLayoutManager);

        mWebService = new WebService(this);
        mNivCharacterImage.setImageUrl(mCharacter.getThumbnail().getPath() + Constants.KEY_DOT + mCharacter.getThumbnail().getExtension(),
                NetworkConnection.getInstance(this).getImageLoader());
        mTvCharacterName.setText(mCharacter.getName());
        if (!TextUtils.isEmpty(mCharacter.getDescription())) {
            mTvCharacterDescription.setText(mCharacter.getDescription());
        }
        downloadComicList();
        downloadEventList();
        downloadSeriesList();
        downloadStoriesList();
        addAdditionalLinks();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRvComicsList != null) {
            mRvComicsList.addOnItemTouchListener(null);
        }
        if (mRvEventsList != null) {
            mRvEventsList.addOnItemTouchListener(null);
        }
        if (mRvStoriesList != null) {
            mRvStoriesList.addOnItemTouchListener(null);
        }
        if (mRvSeriesList != null) {
            mRvSeriesList.addOnItemTouchListener(null);
        }

        if (mComicsAdapter != null) {
            mComicsAdapter.removeScrollListener(this);
            mComicsAdapter = null;
        }
        if (mEventsAdapter != null) {
            mEventsAdapter.removeScrollListener(this);
            mEventsAdapter = null;
        }
        if (mStoryAdapter != null) {
            mStoryAdapter.removeScrollListener(this);
            mStoryAdapter = null;
        }
        if (mSeriesAdapter != null) {
            mSeriesAdapter.removeScrollListener(this);
            mSeriesAdapter = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCharacter == null) {
            Log.d(TAG, "something is wrong, return!");
            finish();
        }
        Log.d(TAG, "setting chacaters details");
    }

    @Override
    public void onListEndReached(DataAdapter.AdapterType type) {
        Log.d(TAG, "onEndReached for "+type+",download more");
        switch (type) {
            case COMICS:
                if (mComicsAdapter.getItemCount() < mCharacter.getComics().getAvailable()) {
                    downloadComicList();
                }

                break;
            case EVENTS:
                if (mEventsAdapter.getItemCount() < mCharacter.getEvents().getAvailable()) {
                    downloadEventList();
                }
                break;

            case SERIES:
                if (mSeriesAdapter.getItemCount() < mCharacter.getSeries().getAvailable()) {
                    downloadSeriesList();
                }
                break;

            case STORIES:
                if (mStoryAdapter.getItemCount() < mCharacter.getStories().getAvailable()) {
                    downloadStoriesList();
                }
                break;
        }
    }

    /**
     * Download events collection for this selectedcharacter.
     */
    private void downloadEventList(){
//        mEventList.downloadList(mEventsAdapter,mRvEventsList,this,Event[].class);
        int limit = Constants.LIMIT;
        int offset = (mEventsAdapter == null)?(Constants.INITIAL_OFFSET):(mEventsAdapter.getItemCount());

        mWebService.getComponentsWithOffset(limit,offset,String.valueOf(mCharacter.getId()), DataAdapter.AdapterType.EVENTS,new HttpRequest.IResponseCallBack() {
            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "Something wrong here");
            }

            @Override
            public void onSuccess(JSONObject response) {
                Log.d(TAG, "onSuccess event");
                List<Event> list = new TranslateResponseToList<Event>().getList(response,Event[].class);

                if (mEventsAdapter == null) {
                    mEventsAdapter = new DataAdapter<Event>(CharacterDetailsActivity.this, list, DataAdapter.AdapterType.EVENTS);
                    mRvEventsList.setAdapter(mEventsAdapter);
                    mEventsAdapter.registerScrollListener(CharacterDetailsActivity.this);
                    mRvEventsList.addOnItemTouchListener(new RecyclerItemClickListener(CharacterDetailsActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Log.d(TAG, "event recycler view clicked for position:" + position);
                            Event event =  mEventsAdapter.getDataAtPosition(position);
                            if (event.getThumbnail() == null) {
                                Toast.makeText(CharacterDetailsActivity.this, "Thumbnail is null", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.d(TAG, " opening detail view for event..");
                            String url = event.getThumbnail().getPath() + Constants.KEY_DOT + event.getThumbnail().getExtension();
                            Intent intent = new Intent(CharacterDetailsActivity.this, ComponentDetailActivity.class);
                            intent.putExtra(Constants.KEY_DETAIL_VIEW_URL, url);
                            startActivity(intent);
                        }
                    }));
                }else{
                    mEventsAdapter.addNewDataInCollection(list);
                    mEventsAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * Download comics collection for this selected character.
     */
    private void downloadComicList() {
        int limit = Constants.LIMIT;
        int offset = (mComicsAdapter == null)?(Constants.INITIAL_OFFSET):(mComicsAdapter.getItemCount());

        mWebService.getComponentsWithOffset(limit,offset,String.valueOf(mCharacter.getId()), DataAdapter.AdapterType.COMICS,new HttpRequest.IResponseCallBack() {
            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "Something wrong here");
            }

            @Override
            public void onSuccess(JSONObject response) {
                Log.d(TAG, "onSuccess comics");
                Gson gson = new Gson();
                List<Comic> list = new TranslateResponseToList<Comic>().getList(response,Comic[].class);

                if (mComicsAdapter == null) {
                    mComicsAdapter = new DataAdapter<Comic>(CharacterDetailsActivity.this, list, DataAdapter.AdapterType.COMICS);
                    mRvComicsList.setAdapter(mComicsAdapter);
                    mRvComicsList.addOnItemTouchListener(new RecyclerItemClickListener(CharacterDetailsActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Log.d(TAG, "comics recycler view clicked for position:" + position);
                            Comic comic = mComicsAdapter.getDataAtPosition(position);
                            if (comic.getThumbnail() == null) {
                                Toast.makeText(CharacterDetailsActivity.this, "Thumbnail is null", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.d(TAG, " opening detail view for comic..");
                            String url = comic.getThumbnail().getPath() + Constants.KEY_DOT + comic.getThumbnail().getExtension();
                            Intent intent = new Intent(CharacterDetailsActivity.this, ComponentDetailActivity.class);
                            intent.putExtra(Constants.KEY_DETAIL_VIEW_URL, url);
                            startActivity(intent);
                        }
                    }));
                    mComicsAdapter.registerScrollListener(CharacterDetailsActivity.this);
                }else{
                    mComicsAdapter.addNewDataInCollection(list);
                    mComicsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Download series collection for this selectedcharacter.
     */
    private void downloadSeriesList() {
        int limit = Constants.LIMIT;
        int offset = (mSeriesAdapter == null)?(Constants.INITIAL_OFFSET):(mSeriesAdapter.getItemCount());

        mWebService.getComponentsWithOffset(limit,offset,String.valueOf(mCharacter.getId()), DataAdapter.AdapterType.SERIES,new HttpRequest.IResponseCallBack() {
            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "Something wrong here");
            }

            @Override
            public void onSuccess(JSONObject response) {
                Log.d(TAG, "onSuccess Series");
                Gson gson = new Gson();
                //List<Comic> list = null;
                List<SeriesSingular> list = new TranslateResponseToList<SeriesSingular>().getList(response,SeriesSingular[].class);

                if (mSeriesAdapter == null) {
                    mSeriesAdapter = new DataAdapter<SeriesSingular>(CharacterDetailsActivity.this, list, DataAdapter.AdapterType.SERIES);
                    mRvSeriesList.setAdapter(mSeriesAdapter);
                    mSeriesAdapter.registerScrollListener(CharacterDetailsActivity.this);
                    mRvSeriesList.addOnItemTouchListener(new RecyclerItemClickListener(CharacterDetailsActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Log.d(TAG, "series recycler view clicked for position:" + position);
                            SeriesSingular singularSeries =  mSeriesAdapter.getDataAtPosition(position);
                            if (singularSeries.getThumbnail() == null) {
                                Toast.makeText(CharacterDetailsActivity.this, "Thumbnail is null", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.d(TAG, " opening detail view for series..");
                            String url = singularSeries.getThumbnail().getPath() + Constants.KEY_DOT + singularSeries.getThumbnail().getExtension();
                            Intent intent = new Intent(CharacterDetailsActivity.this, ComponentDetailActivity.class);
                            intent.putExtra(Constants.KEY_DETAIL_VIEW_URL, url);
                            startActivity(intent);
                        }
                    }));
                }else{
                    mSeriesAdapter.addNewDataInCollection(list);
                    mSeriesAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Download stories collection for this selected character.
     */
    private void downloadStoriesList() {
        int limit = Constants.LIMIT;
        int offset = (mStoryAdapter == null)?(Constants.INITIAL_OFFSET):(mStoryAdapter.getItemCount());

        mWebService.getComponentsWithOffset(limit,offset,String.valueOf(mCharacter.getId()), DataAdapter.AdapterType.STORIES,new HttpRequest.IResponseCallBack() {
            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "Something wrong here");
            }

            @Override
            public void onSuccess(JSONObject response) {
                Log.d(TAG, "onSuccess Stories");
                Gson gson = new Gson();
                //List<Comic> list = null;
                List<Story> list = new TranslateResponseToList<Story>().getList(response,Story[].class);

                if (mStoryAdapter == null) {
                    mStoryAdapter = new DataAdapter<Story>(CharacterDetailsActivity.this, list, DataAdapter.AdapterType.STORIES);
                    mRvStoriesList.setAdapter(mStoryAdapter);
                    mStoryAdapter.registerScrollListener(CharacterDetailsActivity.this);
                    mRvStoriesList.addOnItemTouchListener(new RecyclerItemClickListener(CharacterDetailsActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Log.d(TAG, "Stories recycler view clicked for position:" + position);
                            Story story =  mStoryAdapter.getDataAtPosition(position);
                            if (story.getThumbnail() == null) {
                                Toast.makeText(CharacterDetailsActivity.this, "Thumbnail is null", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.d(TAG, " opening detail view for comic..");
                            String url = story.getThumbnail().getPath() + Constants.KEY_DOT + story.getThumbnail().getExtension();
                            Intent intent = new Intent(CharacterDetailsActivity.this, ComponentDetailActivity.class);
                            intent.putExtra(Constants.KEY_DETAIL_VIEW_URL, url);
                            startActivity(intent);
                        }
                    }));
                }else{
                    mStoryAdapter.addNewDataInCollection(list);
                    mStoryAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Download additional links like Detail url, wiki link and comics link for this character
     */
    private void addAdditionalLinks() {
        for (Url thisUrl : mCharacter.getUrls()) {
            TextView tv = new TextView(this);
            tv.setTextColor(getResources().getColor(R.color.color_white));

            Spanned html = Html.fromHtml(
                    "<a href='"+thisUrl.getUrl()+"'>"+thisUrl.getType().toUpperCase()+"</a>");

            tv.setMovementMethod(LinkMovementMethod.getInstance());
            tv.setText(html);
            tv.setPadding(Constants.DEFAULT_PADDING,Constants.DEFAULT_PADDING,
                    Constants.DEFAULT_PADDING,Constants.DEFAULT_PADDING);
            mRelatedLinkContainer.addView(tv);
        }
    }
}
