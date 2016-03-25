package marvel.h19.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.toolbox.NetworkImageView;

import marvel.h19.R;
import marvel.h19.network.NetworkConnection;
import marvel.h19.utils.Constants;

public class ComponentDetailActivity extends AppCompatActivity {

    private static final String TAG = ComponentDetailActivity.class.getSimpleName();
    private NetworkImageView mNivComponentDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_detail);
        mNivComponentDetail = (NetworkImageView) findViewById(R.id.niv_component_detail_view);

        Intent intent = getIntent();
        String url = intent.getStringExtra(Constants.KEY_DETAIL_VIEW_URL);
        Log.d(TAG, "open for url:" + url);
        if (!TextUtils.isEmpty(url)) {
            mNivComponentDetail.setImageUrl(url, NetworkConnection.getInstance(this).getImageLoader());
        }
    }
}
