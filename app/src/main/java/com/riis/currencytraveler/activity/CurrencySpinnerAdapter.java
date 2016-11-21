package com.riis.currencytraveler.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.riis.currencytraveler.model.CountryCurrency;

import java.util.List;

class CurrencySpinnerAdapter extends BaseAdapter {
    private Context context;
    private final List<CountryCurrency> countryCurrencies;

    CurrencySpinnerAdapter(Context context, List<CountryCurrency> countryCurrencies) {
        this.context = context;
        this.countryCurrencies = countryCurrencies;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(android.R.layout.simple_spinner_item, viewGroup, false);
        }

         ((TextView) view).setText(countryCurrencies.get(i).getCountryCurrencyType().getCountryCurrencyResourceId());

        return view;
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(android.R.layout.simple_spinner_dropdown_item, viewGroup, false);
        ((TextView) view).setText(countryCurrencies.get(i).getCountryCurrencyType().getCountryCurrencyResourceId());

        return view;
    }

    @Override
    public int getCount() {
        return countryCurrencies.size();
    }

    @Override
    public Object getItem(int i) {
        return countryCurrencies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
