package com.riningan.sberbankte.presentation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.riningan.sberbankte.R;
import com.riningan.sberbankte.app.App;
import com.riningan.sberbankte.domain.model.Currency;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainView, AdapterView.OnItemSelectedListener {

    private MainPresenter mPresenter = null;

    private Button btn = null;
    private Spinner spnInput = null;
    private Spinner spnOutput = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(((App) getApplication()).getInteractor());
        setContentView(R.layout.activity_main);
        mPresenter.attachView(this);
        final EditText etMain = findViewById(R.id.etMain);
        spnInput = findViewById(R.id.spnMainInput);
        spnInput.setOnItemSelectedListener(this);
        spnOutput = findViewById(R.id.spnMainOutput);
        spnOutput.setOnItemSelectedListener(this);
        btn = findViewById(R.id.btnMain);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.convert(etMain.getText().toString(), (Currency) spnInput.getSelectedItem(), (Currency) spnOutput.getSelectedItem());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.viewResumed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void setCurrencies(List<Currency> currencies) {
        CurrenciesAdapter inputAdapter = new CurrenciesAdapter(getApplicationContext(), currencies);
        spnInput.setAdapter(inputAdapter);
        CurrenciesAdapter outputAdapter = new CurrenciesAdapter(getApplicationContext(), currencies);
        spnOutput.setAdapter(outputAdapter);
        btn.setEnabled(true);
    }

    @Override
    public void showLoadCurrenciesError(String message) {
        Toast.makeText(getApplicationContext(), getString(R.string.main_error_load_currencies, message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showConvertError(String message) {
        Toast.makeText(getApplicationContext(), getString(R.string.main_error_convert, message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showConvertMessage(float value) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.main_converted, Float.toString(value)))
                .create()
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
