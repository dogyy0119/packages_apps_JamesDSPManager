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

public class MainActivity extends Activity {

    /** 功能列表视图 */
    private ListView mFeatureListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

//            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//            DisplayMetrics dm = new DisplayMetrics();
//            wm.getDefaultDisplay().getMetrics(dm);
//            int width = dm.widthPixels;// 屏幕宽度（像素）
//
//            Log.e("Liuhang",  "width :" + width );
//            FrameLayout fl_center=(FrameLayout)findViewById(R.id.myframelayout);
//            Log.e("Liuhang",  "width1 :" + fl_center.getLayoutParams().width );
//            fl_center.getLayoutParams().width = width - 80;
//            Log.e("Liuhang",  "width2 :" + fl_center.getLayoutParams().width );
        }

        mFeatureListView = (ListView) findViewById(R.id.feature_list);
        String[] items = new String[mFeatures.length];
        for (int i = 0; i < mFeatures.length; ++i) {
            items[i] = mFeatures[i].desc;
        }
        mFeatureListView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, items));
        mFeatureListView.setOnItemClickListener(mFeatureListItemClickListener);

    }

    /**
     * 功能列表项点击回调
     */
    private AdapterView.OnItemClickListener mFeatureListItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Feature feature = mFeatures[position];

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