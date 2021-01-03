package com.github.willena.phoneinputview;

/**
 * Created by Guillaume on 15/04/2017.
 */

public class CountryConfigurator {

    private Boolean displayFlag;
    private Boolean displayCountryCode;
    private Boolean displayDialingCode;
    private String defaultCountry;

    public CountryConfigurator() {
        displayFlag = true;
        displayCountryCode = true;
        displayDialingCode = true;
        defaultCountry = null;
    }

    public void setDefaultCountry(String defaultCode) {
        this.defaultCountry = defaultCode;
    }

    public String getDefaultCountry() {
        return defaultCountry;
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

    public Boolean getDisplayCountryCode() {
        return displayCountryCode;
    }

    public Boolean getDisplayDialingCode() {
        return displayDialingCode;
    }

    public Boolean getDisplayFlag() {
        return displayFlag;
    }
}
