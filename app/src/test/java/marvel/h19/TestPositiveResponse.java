package marvel.h19;

import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import marvel.h19.ui.MainActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by ankitkha on 24-Mar-16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml",constants = BuildConfig.class,sdk=21)
public class TestPositiveResponse {
    private MainActivity mActivity;

    public TestPositiveResponse(){

    }
    @Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;
    }

    @Test
    public void setUpTest() throws Exception {
        mActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        assertNotNull(mActivity);
        RecyclerView view = (RecyclerView) mActivity.findViewById(R.id.recycler_view);
        assertEquals(view.getAdapter().getItemCount(),1);
        //assertEquals(1,2);
    }


}
