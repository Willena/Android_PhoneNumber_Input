package com.github.willena.android_phonenumber_input;

import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.github.willena.phoneinputview.CountryConfigurator;
import com.github.willena.phoneinputview.PhoneInputView;
import com.github.willena.phoneinputview.events.OnValidEntryListener;

public class MainActivity extends AppCompatActivity {

    private PhoneInputView phoneView;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = (Button)findViewById(R.id.button_validate);


        phoneView = (PhoneInputView) findViewById(R.id.phoneId);

        CountryConfigurator config = new CountryConfigurator();
        config.setDisplayFlag(false);
        config.setDisplayCountryCode(true);
        config.setDisplayDialingCode(false);

        phoneView.setConfig(config);

        phoneView.addOnValidEntryListener(new OnValidEntryListener() {
            @Override
            public void onValidEntry(Boolean valid) {
                b.setEnabled(valid);
                Log.d("MAINACTIVITY", "onValidEntry: " + phoneView.getFormatedNumber());
            }
        });

        phoneView.setPhoneNumber("0100000000", "FR");
    }
}
