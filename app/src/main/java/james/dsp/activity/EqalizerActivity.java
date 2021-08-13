package james.dsp.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import james.dsp.R;
import james.dsp.preference.EqualizerSurface;
import james.dsp.service.HeadsetService;

public class EqalizerActivity extends Activity {

    public static final String SHARED_PREFERENCES_EQUALIZER = "james.dsp.EqualizerActivity";
    public static final String AUDIO_EQUALIZER_ARRAY = "audio.equalizer";

    public String name = "";
    private EqualizerSurface mDialogEqualizer;
    private HeadsetService mHeadsetService;
    private SharedPreferences preferencesMode;


    private final ServiceConnection connectionForDialog = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder)
        {
            mHeadsetService = ((HeadsetService.LocalBinder) binder).getService();
            Log.e("Liuhang",  "EqualizerActivity:" + "ServiceConnection:" + " onServiceConnected");
            updateDspFromDialogEqualizer();
        }
        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mHeadsetService = null;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent intent = getIntent();
        name = intent.getStringExtra( MainActivity.ACTIVITY_REQUEST );

        initPrefecence();

        setContentView(R.layout.equalizer);
//        setContentView(R.layout.equalizer_popup);

        mDialogEqualizer = (EqualizerSurface) super.findViewById(R.id.FrequencyResponse);
        mDialogEqualizer.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                Log.e("Liuhang",  "EqualizerActivity:" + "onBindDialogView:" + " onTouch");
                float x = event.getX();
                float y = event.getY();
                /* Which band is closest to the position user pressed? */
                int band = mDialogEqualizer.findClosest(x);
                int wy = v.getHeight();
                float level = (y / wy) * (EqualizerSurface.MIN_DB - EqualizerSurface.MAX_DB) - EqualizerSurface.MIN_DB;
                if (level < EqualizerSurface.MIN_DB)
                    level = EqualizerSurface.MIN_DB;
                if (level > EqualizerSurface.MAX_DB)
                    level = EqualizerSurface.MAX_DB;
                mDialogEqualizer.setBand(band, level);
                updateDspFromDialogEqualizer();
                return true;
            }
        });

        updateListEqualizerFromValue();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EqualizerSurface surfaceview = findViewById(R.id.FrequencyResponse);

        if (surfaceview != null)
            surfaceview.setZOrderOnTop(true);

        getApplicationContext().bindService(new Intent(getApplicationContext(), HeadsetService.class), connectionForDialog, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveListEqualizer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Liuhang",  "EqualizerActivity:" + "onStop:" + "");

        saveListEqualizer();
        if (mHeadsetService != null)
            mHeadsetService.setEqualizerLevels(null);
        getApplicationContext().unbindService(connectionForDialog);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void initPrefecence () {
        preferencesMode = getSharedPreferences(SHARED_PREFERENCES_EQUALIZER + "." + name + "."+ "settings", 0);
    }

    protected void updateDspFromDialogEqualizer()
    {
        Log.e("Liuhang",  "EqualizerActivity:" + "updateDspFromDialogEqualizer:");
        if (mHeadsetService != null)
        {
            float[] levels = new float[ mDialogEqualizer.getNum() ];
            for (int i = 0; i < levels.length; i++)
                levels[i] = mDialogEqualizer.getBand(i);
            mHeadsetService.setEqualizerLevels(levels);
        }
    }

    private void updateListEqualizerFromValue()
    {
        String value = preferencesMode.getString( AUDIO_EQUALIZER_ARRAY, null);
        Log.e("Liuhang",  "EqualizerActivity:" + "updateListEqualizerFromValue:" + value);

        if (value != null && mDialogEqualizer != null)
        {
            String[] levelsStr = value.split(";");
            for (int i = 0; i < mDialogEqualizer.getNum(); i++)
                mDialogEqualizer.setBand(i, Float.valueOf(levelsStr[i]));
        }
    }

    private void saveListEqualizer() {
        String values="";
        for (int i=0; i< mDialogEqualizer.getNum(); i ++) {
            values += Float.toString( mDialogEqualizer.getBand(i) ) + ";";
        }
        Log.e("Liuhang",  "EqualizerActivity:" + "saveListEqualizer。" + values );
        preferencesMode.edit().putString( AUDIO_EQUALIZER_ARRAY, values).commit();
    }
}
