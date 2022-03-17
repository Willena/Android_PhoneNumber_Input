package com.github.willena.phoneinputview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private List<String> countryWhitelist;

    public CountryConfigurator() {
        displayFlag = true;
        displayCountryCode = true;
        displayDialingCode = true;
        phoneNumberHintType = HintType.MOBILE;
        defaultCountry = null;
        countryWhitelist = new ArrayList<>(0);
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

    public List<String> getCountryWhitelist() {
        return countryWhitelist;
    }

    /**
     * @param countryWhitelist list of 2 let names
     */
    public void setCountryWhitelist(String... countryWhitelist) {
        this.countryWhitelist = Arrays.asList(countryWhitelist);
    }

    public void setCountryWhitelist(List<String> countryWhitelist) {
        this.countryWhitelist = countryWhitelist;
    }
}
