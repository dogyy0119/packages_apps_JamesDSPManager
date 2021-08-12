package james.dsp.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

import james.dsp.R;
import james.dsp.preference.EqualizerSurface;


@SuppressLint("ValidFragment")
public class SingleFrameLayout extends Fragment {

    private String name;
    EqualizerSurface mListEqualizer;

    public SingleFrameLayout(String name) {
        this.name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.single_framelayout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mListEqualizer = (EqualizerSurface)getView().findViewById(R.id.one_equalizer_surface);
        if ( mListEqualizer != null ) {
            Log.e("Liuhang", " mListEqualizer all_equalizer_surface ! = null");
                mListEqualizer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent itent = new Intent();
                        itent.setClass(getContext(), EqalizerActivity.class);
                        startActivity(itent);
                    }
                });

        }
    }
}