package com.github.willena.phoneinputview;

/**
 * Created by Guillaume on 15/04/2017.
 */

public class CountryConfigurator {

    private Boolean displayFlag;
    private Boolean displayCountryCode;
    private Boolean displayDialingCode;
	private Boolean displayMobileHint;
    private String defaultCountry;

    public CountryConfigurator() {
        displayFlag = true;
        displayCountryCode = true;
        displayDialingCode = true;
		displayMobileHint = true;
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
	
	public void setDisplayMobileHint(Boolean displayMobileHint){
		this.displayMobileHint = displayMobileHint;
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
	
	public Boolean getDisplayMobileHint() {
		return displayMobileHint;
	}
	
	public String getDefaultCountry() {
        return defaultCountry;
    }
}
