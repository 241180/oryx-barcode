package com.oryx.activity.core;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.oryx.R;
import com.oryx.activity.bu.MarketDetailsActivity;
import com.oryx.activity.bu.MarketListActivity;
import com.oryx.activity.bu.ProductDetailsActivity;
import com.oryx.activity.bu.ProductListActivity;
import com.oryx.activity.login.LoginActivity;
import com.oryx.activity.map.TruckerViewActivity;
import com.oryx.activity.settings.SettingsActivity;
import com.oryx.context.IServer;
import com.oryx.service.AuthService;

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
