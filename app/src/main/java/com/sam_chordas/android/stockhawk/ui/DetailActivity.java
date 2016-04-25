package com.sam_chordas.android.stockhawk.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.adapter.CustomSpinnerAdapter;
import com.sam_chordas.android.stockhawk.model.DateHigh;
import com.sam_chordas.android.stockhawk.model.DateHighMain;
import com.sam_chordas.android.stockhawk.model.Quote;
import com.sam_chordas.android.stockhawk.model.QuoteInfo;
import com.sam_chordas.android.stockhawk.service.QuoteService;
import com.sam_chordas.android.stockhawk.ui.Fragment.LineChartFragment;
import com.sam_chordas.android.stockhawk.rest.Utils;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity implements Callback<QuoteInfo> {

    public static String symbol;
    private ProgressBar pb;
    DateHighMain dhm = new DateHighMain();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        symbol = getIntent().getExtras().getString("symbol");
        pb = (ProgressBar)findViewById(R.id.progressBar);
        getSupportActionBar().setTitle(symbol.toUpperCase());
    }

    private void callRetrofitFetch(String symbol, String startDate, String endDate) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://query.yahooapis.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        QuoteService stackOverflowAPI = retrofit.create(QuoteService.class);
        String q = "select * from yahoo.finance.historicaldata where symbol = \""+symbol+"\" and startDate = \""+endDate+"\" and endDate = \""+startDate+"\"";
        String diagnostics = "true";
        String env = "store://datatables.org/alltableswithkeys";
        String format = "json";
        Call<QuoteInfo> call = stackOverflowAPI.getObjectWithNestedArraysAndObject(q,diagnostics,env,format);

        //asynchronous call
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<QuoteInfo> call, Response<QuoteInfo> response) {
        pb.setVisibility(View.GONE);
        if(response.isSuccessful()){
            QuoteInfo quoteInfo = response.body();
            ArrayList<Quote> quoteArray = quoteInfo.query.results.quote;
            dhm.getDatehigh().clear();
            for (int i=0;i<quoteArray.size();i++) {
                DateHigh dh = new DateHigh();
                dh.setQuoteDate(quoteArray.get(i).quote_date);
                dh.setQuoteHighValue(quoteArray.get(i).high);
                dhm.getDatehigh().add(dh);
            }

            Fragment fragment = LineChartFragment.newInstance(dhm);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_fg, fragment)
                    .commit();
        }
    }

    @Override
    public void onFailure(Call<QuoteInfo> call, Throwable t) {
        Toast.makeText(DetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail_activity, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayList<String> list = new ArrayList<String>();
        list.add(getString(R.string.one_week));
        list.add(getString(R.string.one_month));
        list.add(getString(R.string.three_month));
        list.add(getString(R.string.six_month));
        list.add(getString(R.string.one_year));
        CustomSpinnerAdapter spinAdapter = new CustomSpinnerAdapter(
                getApplicationContext(), list);
        spinner.setAdapter(spinAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String startDate = Utils.getFormattedDate(System.currentTimeMillis());
                Date date = new Date();
                switch (item) {
                    case "1W":
                        callRetrofitFetch(symbol, startDate, Utils.getSelectedDate(date, Utils.SelectedDate.Week));
                        break;
                    case "1M":
                        callRetrofitFetch(symbol, startDate, Utils.getSelectedDate(date, Utils.SelectedDate.Months_1));
                        break;
                    case "3M":
                        callRetrofitFetch(symbol, startDate, Utils.getSelectedDate(date, Utils.SelectedDate.Months_3));
                        break;
                    case "6M":
                        callRetrofitFetch(symbol, startDate, Utils.getSelectedDate(date, Utils.SelectedDate.Months_6));
                        break;
                    case "1Y":
                        callRetrofitFetch(symbol, startDate, Utils.getSelectedDate(date, Utils.SelectedDate.Year));
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        return true;
    }
}
