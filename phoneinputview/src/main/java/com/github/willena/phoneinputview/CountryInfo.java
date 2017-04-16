package com.github.willena.phoneinputview;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Guillaume on 16/04/2017.
 */

public class CountryInfo {
    private String code;
    private String name;

    public CountryInfo(String name, String code) {
        this.code = code;
        this.name = name;
    }

    public CountryInfo() {

    }

    public static ArrayList<CountryInfo> fromArray(String[] array) {
        ArrayList<CountryInfo> countries = new ArrayList<>();

        for (String i : array) {
            countries.add(fromString(i));
        }

        return countries;
    }

    public static CountryInfo fromString(String string) {
        String[] sp = string.split("\\|");
        Log.d("COUNTRYINFO", "fromString: " + sp);
        return new CountryInfo(sp[0], sp[1]);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int find(String code, List<CountryInfo> countryList) {
        for (int i = 0; i < countryList.size(); i++) {
            if (countryList.get(i).getCode().equals(code))
                return i;
        }
        return -1;
    }
}