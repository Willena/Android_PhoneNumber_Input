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
    private List<CountryInfo> countryList;
    private String formatedNumber;
    private boolean nextNumber;

    private final List<CountryInfo> defaultCountryList;

    {
        countryChangeListeners = new ArrayList<>();
        validEntryListeners = new ArrayList<>();
        countryList = new ArrayList<>(0);
        defaultCountryList = CountryInfo.fromArray(getContext().getResources().getStringArray(R.array.countryCodes));
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

        phoneUtil = PhoneNumberUtil.getInstance();

        inflate(getContext(), R.layout.phone_input_view, this);
        this.spinnerView = findViewById(R.id.phone_input_country_spinner);
        this.textInput = findViewById(R.id.phone_input_edit_text);

        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String code = ((CountryInfo) spinnerView.getSelectedItem()).getCode();
                Log.d("PHONE_DIALOG", "onItemSelected: " + code);
                if (config.getPhoneNumberHintType() == CountryConfigurator.HintType.MOBILE) {
                    textInput.setHint(phoneUtil.format(phoneUtil.getExampleNumberForType(((CountryInfo) spinnerView.getSelectedItem()).getCode(), PhoneNumberUtil.PhoneNumberType.MOBILE), PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
                } else if (config.getPhoneNumberHintType() == CountryConfigurator.HintType.FIXED) {
                    textInput.setHint(phoneUtil.format(phoneUtil.getExampleNumberForType(((CountryInfo) spinnerView.getSelectedItem()).getCode(), PhoneNumberUtil.PhoneNumberType.FIXED_LINE), PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
                } else {
                    textInput.setHint(null);
                }

                textInput.setText(textInput.getText());
                triggerCountryChange(code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setConfig(this.config);


        textChangedListener = (view, text) -> {
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
        };
        textInput.addOnTextChangedListenner(textChangedListener);

        if (config.getDefaultCountry() != null)
            setCountry(config.getDefaultCountry());

    }

    public CountryConfigurator getConfig() {
        return this.config;
    }

    public void setConfig(CountryConfigurator c) {
        this.config = c;
        if (this.config == null)
            this.config = new CountryConfigurator();

        // Set to only a short list of country.
        if (this.config.getCountryWhitelist().size() > 0) {
            // To keep compat with older version.
            // Also writable as stream instead...
            countryList.clear();
            countryList = new ArrayList<>(defaultCountryList.size());
            for (CountryInfo instance : defaultCountryList) {
                if (this.config.getCountryWhitelist().contains(instance.getCode())) {
                    countryList.add(instance);
                }
            }
        } else {
            countryList.addAll(defaultCountryList);
        }

        spinnerView.setAdapter(new SpinnerCountryArrayAdapter(getContext(), this.config, phoneUtil, countryList));
        if (config.getDefaultCountry() != null)
            setCountry(config.getDefaultCountry());
    }

    public void setCountry(String code) {
        int pos = CountryInfo.find(code, countryList);
        if (pos >= 0)
            spinnerView.setSelection(pos);
    }

    private String getFomatedNumberFromDigit(String onlydigit) {
        String tmp = onlyDigit(onlydigit);
        AsYouTypeFormatter formatter = phoneUtil.getAsYouTypeFormatter(((CountryInfo) spinnerView.getSelectedItem()).getCode());
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
        Phonenumber.PhoneNumber phoneProto;
        try {
            phoneProto = phoneUtil.parse(number, ((CountryInfo) spinnerView.getSelectedItem()).getCode());
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e);
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
            phoneProto = phoneUtil.parse(textInput.getText().toString(), ((CountryInfo) spinnerView.getSelectedItem()).getCode());
            return phoneUtil.format(phoneProto, format);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e);
            return null;
        }
    }

    public List<CountryInfo> getCountryList() {
        return countryList;
    }

    public void setPhoneNumber(String n, String country) {
        int pos = CountryInfo.find(country, countryList);

        if (pos >= 0) {
            spinnerView.setSelection(pos);
            try {

                Phonenumber.PhoneNumber proto = phoneUtil.parse(n, countryList.get(pos).getCode());
                if (phoneUtil.isValidNumber(proto)) {
                    textInput.setText("");
                    for (char c : onlyDigit(n).toCharArray())
                        textInput.append("" + c);
                }

            } catch (NumberParseException ignored) {
            }
        }

    }

}
