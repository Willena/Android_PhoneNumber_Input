package com.github.willena.phoneinputview;

/**
 * Created by Guillaume on 15/04/2017.
 */

public class CountryConfigurator {

    public enum HintType {
        MOBILE,
        FIXED,
        NONE
    }

    private Boolean displayFlag;
    private Boolean displayCountryCode;
    private Boolean displayDialingCode;
    private HintType phoneNumberHintType;
    private String defaultCountry;

    public CountryConfigurator() {
        displayFlag = true;
        displayCountryCode = true;
        displayDialingCode = true;
        phoneNumberHintType = HintType.MOBILE;
        defaultCountry = null;
    }

    public void setDefaultCountry(String defaultCode) {
        this.defaultCountry = defaultCode;
    }

    public void setDisplayCountryCode(Boolean displayCountryCode) {
        this.displayCountryCode = displayCountryCode;
    }

    public void setDisplayDialingCode(Boolean displayDialingCode) {
        this.displayDialingCode = displayDialingCode;
    }

    public void setDisplayFlag(Boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public void setPhoneNumberHintType(HintType hint) {
        if (hint == null)
            this.phoneNumberHintType = HintType.NONE;
        else
            this.phoneNumberHintType = hint;
    }

    public Boolean getDisplayCountryCode() {
        return displayCountryCode;
    }

    public Boolean getDisplayDialingCode() {
        return displayDialingCode;
    }

    public Boolean getDisplayFlag() {
        return displayFlag;
    }

    public HintType getPhoneNumberHintType() {
        return phoneNumberHintType;
    }

    public String getDefaultCountry() {
        return defaultCountry;
    }
}
