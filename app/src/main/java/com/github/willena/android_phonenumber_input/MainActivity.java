package com.github.willena.android_phonenumber_input;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.github.willena.phoneinputview.CountryConfigurator;
import com.github.willena.phoneinputview.PhoneInputView;
import com.github.willena.phoneinputview.events.OnValidEntryListener;

public class MainActivity extends AppCompatActivity {

    private PhoneInputView phoneView1;
    private Button b;
    private PhoneInputView phoneView2;
    private PhoneInputView phoneView0;
    private PhoneInputView phoneView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Default config
        phoneView0 = (PhoneInputView) findViewById(R.id.phoneId1);
        phoneView0.setConfig(new CountryConfigurator());


        // Define number and country
        b = (Button) findViewById(R.id.button_validate2);
        phoneView1 = (PhoneInputView) findViewById(R.id.phoneId2);
        phoneView1.setConfig(new CountryConfigurator());
        phoneView1.addOnValidEntryListener(new OnValidEntryListener() {
            @Override
            public void onValidEntry(Boolean valid) {
                b.setEnabled(valid);
                Log.d("MAINACTIVITY", "onValidEntry: " + phoneView1.getFormatedNumber());
            }
        });
        phoneView1.setPhoneNumber("0102030405", "FR");


        //Define default country value
        phoneView2 = (PhoneInputView) findViewById(R.id.phoneId3);
        CountryConfigurator config2 = new CountryConfigurator();
        config2.setDefaultCountry("US");
        phoneView2.setConfig(config2);


        // Only flag and code
        phoneView4 = (PhoneInputView) findViewById(R.id.phoneId4);
        CountryConfigurator config = new CountryConfigurator();
        config.setDisplayFlag(true);
        config.setDisplayCountryCode(true);
        config.setDisplayDialingCode(false);

        phoneView4.setConfig(config);
    }
}
