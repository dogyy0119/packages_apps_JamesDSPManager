package james.dsp.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import james.dsp.R;
import james.dsp.preference.EqualizerSurface;

public class MainActivity extends Activity {

    /**
     * 功能列表视图
     */
    private int m_position;
    private Context mycontext;
    private ListView mFeatureListView;
    protected EqualizerSurface mListEqualizer, mDialogEqualizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mycontext = getApplicationContext();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.basic_menu);

        // 菜单button
        if (findViewById(R.id.feature_list) != null) {
            mFeatureListView = (ListView) findViewById(R.id.feature_list);
            String[] items = new String[mFeatures.length];
            for (int i = 0; i < mFeatures.length; ++i) {
                items[i] = mFeatures[i].desc;
            }
            mFeatureListView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, items));
            mFeatureListView.setOnItemClickListener(mFeatureListItemClickListener);
        }

        // frame
        if (findViewById(R.id.myframelayout) != null) {
            OverallFrameLayout contentView = new OverallFrameLayout("全局");
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.myframelayout, contentView)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (findViewById(R.id.all_equalizer_surface) != null) {
            Log.e("Liuhang", "onStart ! = null");
            mListEqualizer = (EqualizerSurface) findViewById(R.id.all_equalizer_surface);
            if (mListEqualizer != null) {
                mListEqualizer.setOnClickListener(listener);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void save() {

    }

    private void quit() {
        save();
        finish();
    }

    private void overall_Situation() {
        if (findViewById(R.id.myframelayout) != null) {
            OverallFrameLayout contentView = new OverallFrameLayout(mFeatures[m_position].desc);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.myframelayout, contentView)
                    .commit();
        }
    }

    private void sound_Channel_Choose() {
        if (findViewById(R.id.myframelayout) != null) {
            SingleFrameLayout contentView = new SingleFrameLayout(mFeatures[m_position].desc);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.myframelayout, contentView)
                    .commit();
        }
    }

    private void sound_Delay() {
    }

    private void sound_Field() {
    }

    private void unsetOnClickListener() {
        mListEqualizer.setOnClickListener(null);
    }

    /**
     * 功能列表项点击回调
     */
    private AdapterView.OnItemClickListener mFeatureListItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("Liuhang", "MainActivity: " + " mFeatureListItemClickListener， position" + position);
            if (position == m_position) return;
            m_position = position;
            switch (position) {
                case 0:
                    quit();
                    break;
                case 1:
                    unsetOnClickListener();
                    overall_Situation();
                    break;
                case 8:
                    unsetOnClickListener();
                    sound_Delay();
                    break;
                case 9:
                    unsetOnClickListener();
                    sound_Field();
                    break;
                default:
                    unsetOnClickListener();
                    sound_Channel_Choose();
                    break;
            }
        }
    };

    /**
     * 均衡器
     */
    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("Liuhang", "OnClickListener: " + "onClick ");
            Intent itent = new Intent();
            itent.setClass(MainActivity.this, EqalizerActivity.class);
            startActivity(itent);
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
        /**
         * 描述信息
         */
        final public String desc;
        /**
         * 点击启动的Activity
         */
        final public Class<? extends Fragment> activityClass;

        /**
         * 构造函数
         *
         * @param desc 描述信息
         */
        public Feature(String desc) {
            this.desc = desc;
            this.activityClass = Fragment.class;
            ;
        }
    }
}