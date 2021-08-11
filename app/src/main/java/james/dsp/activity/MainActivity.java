package james.dsp.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import james.dsp.R;
import james.dsp.preference.EqualizerPreference;
import james.dsp.preference.EqualizerSurface;

public class MainActivity extends Activity {

    /** 功能列表视图 */
    private Context mycontext;
    private ListView mFeatureListView;
    protected EqualizerSurface mListEqualizer, mDialogEqualizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mycontext = this;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.basic_menu);

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 横屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 竖屏
        Log.e("Liuhang",  "2");

        if (findViewById(R.id.myframelayout) != null) {
            OverallFrameLayout contentView = new OverallFrameLayout( "全局");

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.myframelayout, contentView)
                    .commit();

            if(findViewById(R.id.equalizerSurface) != null)
                Log.e("Liuhang",  "! = null");

            mListEqualizer = (EqualizerSurface) findViewById(R.id.equalizerSurface);
            if (mListEqualizer != null) {
                mListEqualizer.setOnClickListener(listener);
            }
        }

        mFeatureListView = (ListView) findViewById(R.id.feature_list);
        String[] items = new String[mFeatures.length];
        for (int i = 0; i < mFeatures.length; ++i) {
            items[i] = mFeatures[i].desc;
        }
        mFeatureListView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, items));
        mFeatureListView.setOnItemClickListener(mFeatureListItemClickListener);

    }

    @Override
    protected void onStart () {
        super.onStart();
        if(findViewById(R.id.equalizerSurface) != null) {
            Log.e("Liuhang", "! = null");
            mListEqualizer = (EqualizerSurface) findViewById(R.id.equalizerSurface);
            if (mListEqualizer != null) {
                mListEqualizer.setOnClickListener(listener);
            }
        }
    }

    /**
     * 功能列表项点击回调
     */
    private AdapterView.OnItemClickListener mFeatureListItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("Liuhang",  "MainActivity: " + " mFeatureListItemClickListener");

            Feature feature = mFeatures[position];

        }
    };

    private final View.OnClickListener listener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v) {
            Log.e("Liuhang",  "OnClickListener: " + "onClick ");
//            EqualizerPreference tem= new EqualizerPreference(mycontext,null);
//            EqalizerActivity tmp = new EqalizerActivity();
            Intent itent=new Intent();
            itent.setClass(MainActivity.this, EqalizerActivity.class);
            startActivity(itent);
//            EqalizerActivity.this.finish();

        }
    };

    private final Feature[] mFeatures = {
            new Feature("退出"),
            new Feature("全局"),
            new Feature("左A柱"),
            new Feature("右A柱"),
            new Feature("左前门"),
            new Feature("右前门"),
            new Feature("左后门"),
            new Feature("右后门"),
            new Feature("延时"),
            new Feature("声场"),
    };

    private class Feature {
        /** 描述信息 */
        final public String desc;
        /** 点击启动的Activity */
        final public Class<? extends Fragment> activityClass;

        /**
         * 构造函数
         * @param desc 描述信息
         */
        public Feature(String desc) {
            this.desc = desc;
            this.activityClass = Fragment.class;
          ;
        }
    }
}