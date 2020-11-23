package com.tatv.tffcallmanager2.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tatv.tffcallmanager2.R;
import com.tatv.tffcallmanager2.databinding.ActivityMainBinding;
import com.tatv.tffcallmanager2.fragment.AllCallLogFragment;
import com.tatv.tffcallmanager2.fragment.IncomingCallsFragment;
import com.tatv.tffcallmanager2.fragment.MissedCallsFragment;
import com.tatv.tffcallmanager2.fragment.OutgoingCallsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CallLogViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initComponents();
    }

    private void initComponents(){
        setSupportActionBar(binding.toolbar);
        if(getRuntimePermission())
            setUpViewPager();
    }

    private void setUpViewPager(){
        binding.tabs.setupWithViewPager(binding.contentView.viewpager);
        adapter = new CallLogViewPagerAdapter(getSupportFragmentManager());
        AllCallLogFragment fragment1 = new AllCallLogFragment();
        MissedCallsFragment mFragment = new MissedCallsFragment();
        IncomingCallsFragment iFragment = new IncomingCallsFragment();
        OutgoingCallsFragment oFragment = new OutgoingCallsFragment();
        adapter.addFragment("All Calls",fragment1);
        adapter.addFragment("Outgoing", oFragment);
        adapter.addFragment("Incoming", iFragment);
        adapter.addFragment("Missed", mFragment);
        binding.contentView.viewpager.setAdapter(adapter);
    }

    class CallLogViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public CallLogViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(String title, Fragment fragment){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private boolean getRuntimePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALL_LOG},123);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpViewPager();
            }else{
                finish();
            }
        }
    }
}