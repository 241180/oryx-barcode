package com.oryx.barcode.activity.core;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.oryx.barcode.R;
import com.oryx.barcode.activity.bu.MarketDetailsActivity;
import com.oryx.barcode.activity.bu.MarketListActivity;
import com.oryx.barcode.activity.bu.ProductDetailsActivity;
import com.oryx.barcode.activity.bu.ProductListActivity;
import com.oryx.barcode.activity.login.LoginActivity;
import com.oryx.barcode.activity.map.TruckerViewActivity;
import com.oryx.barcode.activity.settings.SettingsActivity;
import com.oryx.barcode.context.IServer;
import com.oryx.barcode.service.AuthService;

public abstract class NoActionBarActivity extends AbstractActivity{


    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }
}
