package com.tatv.tffcallmanager2.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.tatv.tffcallmanager2.R;
import com.tatv.tffcallmanager2.databinding.ActivityStatisticsBinding;
import com.tatv.tffcallmanager2.utils.CallLogUtils;
import com.tatv.tffcallmanager2.utils.Utils;


public class StatisticsActivity extends AppCompatActivity {

    ActivityStatisticsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statistics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initValues();
    }

    private void initValues(){
        String number = getIntent().getStringExtra("number");
        String name = getIntent().getStringExtra("name");
        if(number == null){
            finish();
            return;
        }

        CallLogUtils callLogUtils = CallLogUtils.getInstance(getApplicationContext());
        long data[] = callLogUtils.getAllCallState(number);
        binding.textViewCallCountTotal.setText(data[0]+" calls");
        binding.textViewCallDurationsTotal.setText(Utils.formatSeconds(data[1]));

        data = callLogUtils.getIncomingCallState(number);
        binding.textViewCallCountIncoming.setText(data[0]+" calls");
        binding.textViewCallDurationsIncoming.setText(Utils.formatSeconds(data[1]));

        data = callLogUtils.getOutgoingCallState(number);
        binding.textViewCallCountOutgoing.setText(data[0]+" calls");
        binding.textViewCallDurationsOutgoing.setText(Utils.formatSeconds(data[1]));

        int count = callLogUtils.getMissedCallState(number);
        binding.textViewCallCountMissed.setText(count+" calls");
        binding.textViewCallDurationsMissed.setText(Utils.formatSeconds(0));

        binding.textViewNumber.setText(number);
        binding.textViewName.setText(TextUtils.isEmpty(name) ? number : name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
