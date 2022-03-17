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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Default config
        PhoneInputView phoneView0 = findViewById(R.id.phoneId1);
        phoneView0.setConfig(new CountryConfigurator());


        // Define number and country
        b = findViewById(R.id.button_validate2);
        phoneView1 = findViewById(R.id.phoneId2);
        phoneView1.setConfig(new CountryConfigurator());
        phoneView1.addOnValidEntryListener(valid -> {
            b.setEnabled(valid);
            Log.d("MAINACTIVITY", "onValidEntry: " + phoneView1.getFormatedNumber());
        });
        phoneView1.setPhoneNumber("0102030405", "FR");


        //Define default country value
        PhoneInputView phoneView2 = findViewById(R.id.phoneId3);
        CountryConfigurator config2 = new CountryConfigurator();
        config2.setDefaultCountry("US");
        phoneView2.setConfig(config2);


        // Only flag and code
        PhoneInputView phoneView4 = findViewById(R.id.phoneId4);
        CountryConfigurator config = new CountryConfigurator();
        config.setDisplayFlag(true);
        config.setDisplayCountryCode(true);
        config.setDisplayDialingCode(false);
        config.setPhoneNumberHintType(null);
        phoneView4.setConfig(config);


        // Using whitelisted countries
        PhoneInputView phoneView5 = findViewById(R.id.phoneId5);
        CountryConfigurator config3 = new CountryConfigurator();
        config3.setDefaultCountry("GB");
        config3.setCountryWhitelist("FR", "GB", "US");
        phoneView5.setConfig(config3);

    }
}
