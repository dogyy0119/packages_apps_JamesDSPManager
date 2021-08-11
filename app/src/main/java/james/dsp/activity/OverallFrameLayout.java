package james.dsp.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import james.dsp.R;
import james.dsp.preference.EqualizerSurface;

@SuppressLint("ValidFragment")
public class OverallFrameLayout extends Fragment {

    private String name;
    EqualizerSurface mListEqualizer;

    public OverallFrameLayout(String name) {
        this.name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Liuhang",  "OverallFrameLayout: " + "onCreate ");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.overall_frame, container, false);
    }
}
