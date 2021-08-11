package james.dsp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import james.dsp.R;
import james.dsp.preference.EqualizerSurface;

public class EqalizerActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equalizer);

        Log.e("Liuhang", "EqalizerActivity onCreate");
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.equalizer_popup);
//
////        surfaceview.setZOrderOnTop(true);
//    }

    @Override
    protected void onStart()
    {
        super.onStart();
        EqualizerSurface surfaceview = findViewById(R.id.FrequencyResponse);

        surfaceview.setZOrderOnTop(true);
    }
}
