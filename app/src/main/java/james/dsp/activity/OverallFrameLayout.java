package james.dsp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import james.dsp.R;
import james.dsp.preference.EqualizerSurface;

@SuppressLint("ValidFragment")
public class OverallFrameLayout extends Fragment {

    private String name;
    EqualizerSurface mListEqualizer;
    SharedPreferences preferencesMode;

    public OverallFrameLayout(String name) {
        this.name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Liuhang",  "OverallFrameLayout: " + "onCreate ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.overall_frame, container, false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Liuhang", "OverallFrameLayout: onStart");

        mListEqualizer = (EqualizerSurface)getView().findViewById(R.id.all_equalizer_surface);
        initPrefecence();

        String value = preferencesMode.getString( EqualizerActivity.AUDIO_EQUALIZER_ARRAY, null);
        initEqualizerValue( value );

        if ( mListEqualizer != null ) {
            mListEqualizer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent itent = new Intent();
                    itent.putExtra( MainActivity.ACTIVITY_REQUEST, name );
                    itent.setClass( getContext(), EqualizerActivity.class );
                    startActivity( itent );
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Liuhang", "OverallFrameLayout: " + " onResume");

        String value = preferencesMode.getString( EqualizerActivity.AUDIO_EQUALIZER_ARRAY, null);

        Log.e("Liuhang", "OverallFrameLayout: " + " onResume: " + value);
        initEqualizerValue( value );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Liuhang", "OverallFrameLayout: " + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("Liuhang", "OverallFrameLayout: " + " onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Liuhang", "OverallFrameLayout: " + " onDestroy");
    }

    public void initEqualizerValue( String value ) {
        Log.e("Liuhang", "OverallFrameLayout: initEqualizerValue" + value);
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
        preferencesMode = this.getActivity().getSharedPreferences(EqualizerActivity.SHARED_PREFERENCES_EQUALIZER + "." + name + "."+ "settings", 0);
    }

}
