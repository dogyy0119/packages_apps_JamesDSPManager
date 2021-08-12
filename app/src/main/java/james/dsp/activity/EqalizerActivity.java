package james.dsp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import james.dsp.R;
import james.dsp.preference.EqualizerSurface;

public class EqalizerActivity extends Activity {

    private EqualizerSurface mDialogEqualizer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.equalizer);
        setContentView(R.layout.equalizer_popup);

        mDialogEqualizer = (EqualizerSurface) super.findViewById(R.id.FrequencyResponse);
        mDialogEqualizer.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                Log.e("Liuhang",  "EqualizerPreference:" + "onBindDialogView:" + " onTouch");
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
//                updateDspFromDialogEqualizer();
                return true;
            }
        });
        Log.e("Liuhang", "EqalizerActivity onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EqualizerSurface surfaceview = findViewById(R.id.FrequencyResponse);

        if (surfaceview != null)
            surfaceview.setZOrderOnTop(true);
    }
}
