package com.github.willena.phoneinputview;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.i18n.phonenumbers.PhoneNumberUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillaume on 23/02/2017.
 */

class SpinnerCountryArrayAdapter extends ArrayAdapter<String> {

    private final CountryConfigurator config;
    private final PhoneNumberUtil phoneUtils;

    public SpinnerCountryArrayAdapter(Context context, CountryConfigurator config, PhoneNumberUtil phoneUtil,
                                      List<String> objects) {
        super(context, R.layout.phone_input_spinner_item, objects);
        this.phoneUtils = phoneUtil;
        this.config = config;
        //this.config.setDisplayCountryCode(false);
        //this.config.setDisplayFlag(false);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(R.layout.phone_input_spinner_item, parent, false);

        ImageView flag = (ImageView) row.findViewById(R.id.phone_input_spinner_item_flag);
        TextView countryCode = (TextView) row.findViewById(R.id.phone_input_spinner_item_country);
        TextView dialCode = (TextView) row.findViewById(R.id.phone_input_spinner_item_dialcode);

        if (this.config.getDisplayCountryCode())
            countryCode.setText(getItem(position));
        else
            countryCode.setVisibility(View.GONE);

        if (this.config.getDisplayFlag()) {
            Resources resources = row.getResources();
            int resourceId = resources.getIdentifier(getItem(position).toLowerCase() + "_", "drawable", row.getContext().getPackageName());


            if (resourceId <= 0)
                flag.setImageDrawable(null);
            else
                flag.setImageDrawable(resources.getDrawable(resourceId));
        } else
            flag.setVisibility(View.GONE);


        if (this.config.getDisplayDialingCode()) {
            try {
                dialCode.setText("(+" + phoneUtils.getExampleNumber(getItem(position)).getCountryCode() + ")");
            } catch (Exception e) {
                dialCode.setText("");

            }
        } else {
            dialCode.setVisibility(View.GONE);
        }

        return row;
    }

}
