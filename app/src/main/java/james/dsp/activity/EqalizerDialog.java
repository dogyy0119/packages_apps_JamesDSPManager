package james.dsp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import james.dsp.R;
import james.dsp.preference.EqualizerSurface;

public class EqalizerDialog  extends Dialog {
    public EqalizerDialog(@NonNull @org.jetbrains.annotations.NotNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equalizer_popup);

//        surfaceview.setZOrderOnTop(true);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        EqualizerSurface surfaceview = findViewById(R.id.FrequencyResponse);

        surfaceview.setZOrderOnTop(true);
    }
}
