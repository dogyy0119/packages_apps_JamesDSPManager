package james.dsp.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences preferencesMode;

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
        initPrefecence();

        String value = preferencesMode.getString( EqalizerActivity.AUDIO_EQUALIZER_ARRAY, null);
        initEqualizerValue( value );

        if ( mListEqualizer != null ) {
            Log.e("Liuhang", " mListEqualizer all_equalizer_surface ! = null");
            mListEqualizer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent itent = new Intent();
                    itent.putExtra( MainActivity.ACTIVITY_REQUEST, name );
                    itent.setClass( getContext(), EqalizerActivity.class );
                    startActivity( itent );
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Liuhang", "SingleFrameLayout: " + " onResume");

        String value = preferencesMode.getString( EqalizerActivity.AUDIO_EQUALIZER_ARRAY, null);

        Log.e("Liuhang", "SingleFrameLayout: " + " onResume: " + value);
        initEqualizerValue( value );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Liuhang", "SingleFrameLayout: " + " onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("Liuhang", "SingleFrameLayout: " + " onStop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Liuhang", "SingleFrameLayout: " + " onDestroy");
    }

    public void initEqualizerValue( String value ) {
        Log.e("Liuhang", "SingleFrameLayout: initEqualizerValue" + value);
        if( mListEqualizer != null ) {
            if (value != null && mListEqualizer != null)
            {
                String[] levelsStr = value.split(";");
                for (int i = 0; i < mListEqualizer.getNum(); i++)
                    mListEqualizer.setBand(i, Float.valueOf(levelsStr[i]));
            }
        }
    }

    private void initPrefecence () {
        preferencesMode = this.getActivity().getSharedPreferences(EqalizerActivity.SHARED_PREFERENCES_EQUALIZER + "." + name + "."+ "settings", 0);
    }

}