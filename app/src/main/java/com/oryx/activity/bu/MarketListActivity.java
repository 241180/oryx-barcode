package com.oryx.activity.bu;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.oryx.activity.core.AbstractListActivity;

public class MarketListActivity extends AbstractListActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_market_list);
        //ButterKnife.bind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }


}