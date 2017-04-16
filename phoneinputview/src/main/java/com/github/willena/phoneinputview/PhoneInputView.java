package com.github.willena.phoneinputview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.willena.phoneinputview.events.OnCountryChangedListener;
import com.github.willena.phoneinputview.events.OnValidEntryListener;
import com.google.i18n.phonenumbers.AsYouTypeFormatter;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PhoneInputView extends LinearLayout {

    private static final String TAG = "PHONE_INPUT_VIEW";
    private CountryConfigurator config;

    private TextWatcherAdapter.TextWatcherListener textChangedListener;
    private Spinner spinnerView;
    private ClearableEditText textInput;
    private PhoneNumberUtil phoneUtil;
    private ArrayList<OnCountryChangedListener> countryChangeListeners;
    private ArrayList<OnValidEntryListener> validEntryListeners;
    private List<String> countryCodeList;
    private String formatedNumber;
    private boolean nextNumber;

    {
        countryChangeListeners = new ArrayList<>();
        validEntryListeners = new ArrayList<>();
        countryCodeList = Arrays.asList(getContext().getResources().getStringArray(R.array.countryCodes));

        init();
    }

    public PhoneInputView(Context context) {
        super(context);
    }

    public PhoneInputView(Context context, CountryConfigurator cfg) {
        super(context);
        this.config = cfg;
    }

    public PhoneInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhoneInputView(Context context, CountryConfigurator cfg, AttributeSet attrs) {
        super(context, attrs);
        this.config = cfg;
    }

    public PhoneInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PhoneInputView(Context context, CountryConfigurator cfg, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.config = cfg;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PhoneInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PhoneInputView(Context context, CountryConfigurator cfg, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.config = cfg;
    }

    private void init() {
        if (this.config == null)
            this.config = new CountryConfigurator();


        phoneUtil = PhoneNumberUtil.getInstance();
        inflate(getContext(), R.layout.phone_input_view, this);

        this.spinnerView = (Spinner) findViewById(R.id.phone_input_country_spinner);
        this.textInput = (ClearableEditText) findViewById(R.id.phone_input_edit_text);

        spinnerView.setAdapter(new SpinnerCountryArrayAdapter(getContext(), this.config, phoneUtil, countryCodeList));
        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String code = (String) spinnerView.getSelectedItem();
                Log.d("PHONE_DIALOG", "onItemSelected: " + code);
                textInput.setHint(phoneUtil.format(phoneUtil.getExampleNumberForType((String) spinnerView.getSelectedItem(), PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE), PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
                textInput.setText(textInput.getText());
                triggerCountryChange(code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        textChangedListener = new TextWatcherAdapter.TextWatcherListener() {
            @Override
            public void onTextChanged(EditText view, String text) {
                textInput.removeOnTextChangedListenner(textChangedListener);

                Log.d(TAG, "onTextChanged: the new text is : " + text);

                String number = getFomatedNumberFromDigit(text);

                Log.d("NUMBER", "afterTextChanged: " + number);

                textInput.setText("");
                textInput.append(number);

                boolean validity = isValid(textInput.getText().toString());
                triggerValidEntry(validity);
                if (validity)
                    textInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(text.length())});
                else
                    textInput.setFilters(new InputFilter[]{});

                textInput.addOnTextChangedListenner(textChangedListener);
            }
        };

        textInput.addOnTextChangedListenner(textChangedListener);


    }

    public CountryConfigurator getConfig() {
        return this.config;
    }

    public void setConfig(CountryConfigurator c) {
        this.config = c;
        spinnerView.setAdapter(new SpinnerCountryArrayAdapter(getContext(), this.config, phoneUtil, countryCodeList));
    }

    private String getFomatedNumberFromDigit(String onlydigit) {
        String tmp = onlyDigit(onlydigit);
        AsYouTypeFormatter formatter = phoneUtil.getAsYouTypeFormatter((String) spinnerView.getSelectedItem());
        String number = "";
        for (char c : onlyDigit(tmp).toCharArray()) {
            number = formatter.inputDigit(c);
        }
        return number;
    }

    private String onlyDigit(String text) {
        return text.replaceAll("\\D+", "");
    }

    private Boolean isValid(String number) {
        Phonenumber.PhoneNumber phoneProto = null;
        try {
            phoneProto = phoneUtil.parse(number, (String) spinnerView.getSelectedItem());
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            return false;
        }
        if (phoneProto != null)
            return phoneUtil.isValidNumber(phoneProto);


        return false;
    }

    public void addOnCountryChangeListener(OnCountryChangedListener listener) {
        countryChangeListeners.add(listener);
    }

    public ArrayList<OnCountryChangedListener> getCountryChangeListeners() {
        return countryChangeListeners;
    }

    public void addOnValidEntryListener(OnValidEntryListener listener) {
        validEntryListeners.add(listener);
    }

    public ArrayList<OnValidEntryListener> getValidEntryListeners() {
        return validEntryListeners;
    }

    private void triggerCountryChange(String country) {
        for (OnCountryChangedListener l : countryChangeListeners)
            l.onCountryChange(country);
    }

    private void triggerValidEntry(Boolean valid) {
        for (OnValidEntryListener l : validEntryListeners) {
            l.onValidEntry(valid);
        }
    }

    public String getFormatedNumber() {
        return getFormatedNumber(PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
    }

    public String getFormatedNumber(PhoneNumberUtil.PhoneNumberFormat format) {
        Phonenumber.PhoneNumber phoneProto;
        try {
            phoneProto = phoneUtil.parse(textInput.getText().toString(), (String) spinnerView.getSelectedItem());
            return phoneUtil.format(phoneProto, format);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            return null;
        }
    }

    public void setPhoneNumber(String n, String country) {
        int pos = countryCodeList.indexOf(country.toUpperCase());

        if (pos > 0) {
            spinnerView.setSelection(pos);
            try {

                Phonenumber.PhoneNumber proto = phoneUtil.parse(n, countryCodeList.get(pos));
                if (phoneUtil.isValidNumber(proto)){
                    textInput.setText("");
                    for (char c : onlyDigit(n).toCharArray())
                        textInput.append(""+c);
                }

            } catch (NumberParseException e) {
                return;
            }
        }

    }

}
