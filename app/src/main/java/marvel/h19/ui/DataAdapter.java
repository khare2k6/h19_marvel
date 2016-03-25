package marvel.h19.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import marvel.h19.R;
import marvel.h19.network.NetworkConnection;
import marvel.h19.schema.Character;
import marvel.h19.schema.Comic;
import marvel.h19.schema.Event;
import marvel.h19.schema.SeriesSingular;
import marvel.h19.schema.Story;
import marvel.h19.utils.Constants;

/**
 * Data adapter for all the recycler views. The given requirement are such that single adapter
 * of generic type will be able to suffice for all the component types i.e. Characters, Comics,
 * Series and Stories. The row layout is also almost similar i.e. one networkImageView and one title.
 * So as of now, in given conditions, this adapter serves the purpose.
 */
public class DataAdapter<T> extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private static final String TAG = DataAdapter.class.getSimpleName();
    private Context mContext;
    /*Data collection for this adapter*/
    private List<T> mDataList;
    /*Listeners which are to be informed when scrolling has reached end of list*/
    private List<IScrollListenor> mScrolListenersList;
    /*Adapter type based on component type i.e. Characters,comics,series or stores*/
    private AdapterType mAdapterType;
    /*Horizontal or vertical layout to be used based on adapterType */
    private int mRowLayoutId;

    public DataAdapter(Context context, List<T> charactersList, AdapterType type) {
        this.mContext = context;
        this.mDataList = charactersList;
        mAdapterType = type;
        mScrolListenersList = new ArrayList<IScrollListenor>();
        mRowLayoutId = getHorizontalVerticalLayout(type);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(mRowLayoutId, parent, false);
        ViewHolder holder = new ViewHolder(row);
        return holder;
    }

    /**
     * Adds newly fetched data fetched from server in the already existing data collection.
     * @param list
     */
    public void addNewDataInCollection(List<T> list) {
        mDataList.addAll(list);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Card card = new Card();
        card.init(mAdapterType,position);
        holder.tvCharacterName.setText(card.getTitle());
        if (card.getUrl() == null) {
            holder.networkImageView.setDefaultImageResId(R.drawable.image_not_available);
        } else {
            holder.networkImageView.setImageUrl(card.getUrl(), NetworkConnection.getInstance(mContext).getImageLoader());
        }

        //if end of the list is reached,inform the listeners. As of now , informing when 1 element
        //is left in the list before it hits the end
        if (position == mDataList.size()- 1) {
            Log.d(TAG, "end readched inform listners");
            for (IScrollListenor listenor : mScrolListenersList) {
                Log.d(TAG, "informing listner->"+listenor);
                listenor.onListEndReached(mAdapterType);
            }
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (mDataList != null) {
            size = mDataList.size();
        }
        return size;
    }

    public T getDataAtPosition(int pos) {
        return mDataList.get(pos);
    }

    /**
     * Register listeners interested for receiving scrolling has reached end callbacks
     * @param listenor
     */
    public void registerScrollListener(IScrollListenor listenor) {
        Log.d(TAG,"register called for :" + listenor+" contains:"+mScrolListenersList.contains(listenor));
        if (!mScrolListenersList.contains(listenor)) {
            mScrolListenersList.add(listenor);
        }
    }

    public void removeScrollListener(IScrollListenor listenor) {
        if (mScrolListenersList.contains(listenor)) {
            mScrolListenersList.remove(listenor);
        }
    }

    /**
     * View holder class for this adapter
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCharacterName;
        public NetworkImageView networkImageView;

        public ViewHolder(View row) {
            super(row);
            tvCharacterName = (TextView) row.findViewById(R.id.tv_character_name);
            networkImageView = (NetworkImageView) row.findViewById(R.id.niv_character_image);
        }

    }

    /**
     * Components interested to know user action of scrolling up or down should implement this
     * interface and register with this adapter
     */
    public interface IScrollListenor {
        /**
         * Inform the listener that scrolling has reached till end of adapter
         * @param type
         */
        public void onListEndReached(AdapterType type);
    }

    /**
     * This single Data adapter can support below five types of components
     * <li>Characters<li/>
     * <li>Comics
     * <li>Series
     * <li>Events
     * <li>Stories
     */
    public enum AdapterType {
        CHARACTERS_LIST,
        COMICS,
        SERIES,
        EVENTS,
        STORIES,
        SEARCH
    }

    /**
     * On the basis of adapter type, select orientation of row scrolling
     * @param type
     * @return
     */
    private int getHorizontalVerticalLayout(AdapterType type) {
        switch (type) {
            case CHARACTERS_LIST:
                return R.layout.row_characters_list_vertical;
            case SEARCH:
                return R.layout.row_characters_list_search;
            default:
                return R.layout.row_characters_list_horizontal;
        }
    }
    /**
     * Let each row be like a card, with a title and image
     */
    private class Card {
        /*Name of hte character/comics/series/stories*/
        private String title;
        /*url of the image to be fetched*/
        private String url;

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public void init(AdapterType type, int position) {
            switch (type) {
                case CHARACTERS_LIST:
                case SEARCH:
                    Character character = (Character) mDataList.get(position);
                    url = (character.getThumbnail() == null)?null:character.getThumbnail().getPath() + Constants.KEY_DOT + character.getThumbnail().getExtension();
                    title = character.getName();
                    break;

                case COMICS:
                    Comic comic = (Comic) mDataList.get(position);
                    title = comic.getTitle();
                    url = (comic.getThumbnail() ==null)?null:comic.getThumbnail().getPath()+ Constants.KEY_DOT + comic.getThumbnail().getExtension();
                    break;

                case EVENTS:
                    Event event = (Event) mDataList.get(position);
                    title = event.getTitle();
                    url = (event.getThumbnail()==null)?null:event.getThumbnail().getPath()+ Constants.KEY_DOT + event.getThumbnail().getExtension();
                    break;

                case SERIES:
                    SeriesSingular series = (SeriesSingular) mDataList.get(position);
                    title = series.getTitle();
                    url = (series.getThumbnail()==null)?null:series.getThumbnail().getPath()+ Constants.KEY_DOT + series.getThumbnail().getExtension();
                    break;

                case STORIES:
                    Story story = (Story) mDataList.get(position);
                    title = story.getTitle();
                    url = (story.getThumbnail() == null)?null:(story.getThumbnail().getPath()+ Constants.KEY_DOT + story.getThumbnail().getExtension());
                    break;
            }
        }
    }
}
