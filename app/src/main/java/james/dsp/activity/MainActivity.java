package james.dsp.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import james.dsp.R;
import james.dsp.service.HeadsetService;

public class MainActivity extends Activity {
    /**
     * 功能列表视图
     */
    private int m_position;
    private Context mycontext;
    private ListView mFeatureListView;

    public static final String ACTIVITY_REQUEST = "name";
    public static final String NOTIFICATION_CHANNEL = "1";
    public static final String SHARED_PREFERENCES_BASENAME = "james.dsp";
    public static final String ACTION_UPDATE_PREFERENCES = "james.dsp.UPDATE";
    public static final String SHARED_PREFERENCES_EQUALIZER = "james.dsp.EqualizerActivity";
    public static final String AUDIO_EQUALIZER_ARRAY = "audio.equalizer";
    public static final String APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() ;
    public static final String ACOUSTIC_FILE_BASE = "acoustic_";

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

        Intent serviceIntent = new Intent(this, HeadsetService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("Liuhang",  "DSPManager:" + "startForegroundService");
            initializeNotificationChannel();
            startForegroundService(serviceIntent);
        } else startService(serviceIntent);
        Log.e("Liuhang",  "DSPManager:" + "startService over");
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        if (findViewById(R.id.myframelayout) != null) {
            TimeDelayFrameLayout contentView = new TimeDelayFrameLayout(mFeatures[m_position].desc);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.myframelayout, contentView)
                    .commit();
        }
    }

    private void sound_Field() {
        if (findViewById(R.id.myframelayout) != null) {
            AcousticFieldFrameLayout contentView = new AcousticFieldFrameLayout(mFeatures[m_position].desc);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.myframelayout, contentView)
                    .commit();
        }
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
                    overall_Situation();
                    break;
                case 8:
                    sound_Delay();
                    break;
                case 9:
                    sound_Field();
                    break;
                default:
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeNotificationChannel() {
        final CharSequence name = getString(R.string.notification_channel_name);
        final int importance = NotificationManager.IMPORTANCE_LOW;
        Log.e("Liuhang", "HeadsetService: MainActivity: " + " initializeNotificationChannel， name" + name);

        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, name, importance);

        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null)
            notificationManager.createNotificationChannel(channel);
    }
}