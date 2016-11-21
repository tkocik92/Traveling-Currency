package com.riis.currencytraveler.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.riis.currencytraveler.CountryCurrencyType;
import com.riis.currencytraveler.R;
import com.riis.currencytraveler.model.CountryCurrency;
import com.riis.currencytraveler.util.CurrencyChangedListener;
import com.riis.currencytraveler.util.CurrencySpinnerOnItemSelectedListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CurrencyChangedListener {

    private EditText currencyAmountEditText;
    private EditText usdAmountEditText;
    private ProgressDialog progressDialog;
    private AppCompatSpinner currencySpinner;
    private TextView brlTextView;
    private TextView eurTextView;
    private TextView gbpTextView;
    private TextView jpyTextView;
    private TextView convertedCurrencyAmountEditText;
    private TextView convertedUSDAmountEditText;
    private Toolbar toolbar;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();

        mainPresenter = new MainPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.getCurrencyConversions(CountryCurrencyType.USD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.main_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refreshRates) {
            mainPresenter.getCurrencyConversions(CountryCurrencyType.USD);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCurrencyChanged(CountryCurrencyType countryCurrencyType) {
        switch (countryCurrencyType) {
            case BRL:
                convertedCurrencyAmountEditText.setText(R.string.zero_reales);
                currencyAmountEditText.setHint(R.string.amount_in_reales);
                break;
            case EUR:
                convertedCurrencyAmountEditText.setText(R.string.zero_euro);
                currencyAmountEditText.setHint(R.string.amount_in_euros);
                break;
            case GBP:
                convertedCurrencyAmountEditText.setText(R.string.zero_pounds);
                currencyAmountEditText.setHint(R.string.amount_in_pounds);
                break;
            case JPY:
                convertedCurrencyAmountEditText.setText(R.string.zero_yen);
                currencyAmountEditText.setHint(R.string.amount_in_yen);
                break;
        }

        usdAmountEditText.setText("");
        currencyAmountEditText.setText("");
        convertedUSDAmountEditText.setText(R.string.zero_dollar);

        mainPresenter.setCountryCurrencyType(countryCurrencyType);
    }

    public void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.fetch_rates));
            progressDialog.setCancelable(false);
        }

        progressDialog.show();
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    public void failedToFetchConversionRates() {
        Toast.makeText(this, R.string.could_not_fetch, Toast.LENGTH_SHORT).show();
    }

    public void displayConversionRates(List<CountryCurrency> countryCurrencies) {
        SharedPreferences sharedPreferences = getSharedPreferences("currency", MODE_PRIVATE);

        brlTextView.setText(sharedPreferences.getString(CountryCurrencyType.BRL.name(), "0"));
        eurTextView.setText(sharedPreferences.getString(CountryCurrencyType.EUR.name(), "0"));
        gbpTextView.setText(sharedPreferences.getString(CountryCurrencyType.GBP.name(), "0"));
        jpyTextView.setText(sharedPreferences.getString(CountryCurrencyType.JPY.name(), "0"));

        currencySpinner.setAdapter(new CurrencySpinnerAdapter(this, countryCurrencies));
        currencySpinner.setOnItemSelectedListener(new CurrencySpinnerOnItemSelectedListener(this));
    }

    private void setUpViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        setSupportActionBar(toolbar);

        brlTextView = (TextView) findViewById(R.id.brlConversionRate);
        eurTextView = (TextView) findViewById(R.id.eurConversionRate);
        gbpTextView = (TextView) findViewById(R.id.gbpConversionRate);
        jpyTextView = (TextView) findViewById(R.id.jpyConversionRate);

        currencySpinner = (AppCompatSpinner) findViewById(R.id.currencySpinner);

        usdAmountEditText = (EditText) findViewById(R.id.usdAmount);
        convertedCurrencyAmountEditText = (TextView) findViewById(R.id.convertedCurrencyAmount);
        currencyAmountEditText = (EditText) findViewById(R.id.currencyAmount);
        convertedUSDAmountEditText = (TextView) findViewById(R.id.convertedUSDAmount);

        usdAmountEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String resultAmount = mainPresenter.convertUSDToCurrency(v.getText().toString());

                    switch (mainPresenter.getCountryCurrencyType()) {
                        case BRL:
                            convertedCurrencyAmountEditText.setText(getString(R.string.reales_s, resultAmount));
                            break;
                        case EUR:
                            convertedCurrencyAmountEditText.setText(getString(R.string.euros_s, resultAmount));
                            break;
                        case GBP:
                            convertedCurrencyAmountEditText.setText(getString(R.string.pounds_s, resultAmount));
                            break;
                        case JPY:
                            convertedCurrencyAmountEditText.setText(getString(R.string.yen_s, resultAmount));
                            break;
                    }

                    return true;
                }

                return false;
            }
        });

        currencyAmountEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String resultAmount = mainPresenter.convertCurrencyToUSD(v.getText().toString());

                    convertedUSDAmountEditText.setText(getString(R.string.dollars_s, resultAmount));
                    return true;
                }

                return false;
            }
        });
    }
}
