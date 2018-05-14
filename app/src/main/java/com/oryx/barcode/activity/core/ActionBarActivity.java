package com.oryx.barcode.activity.core;

import android.content.Intent;
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
import com.oryx.barcode.context.StaticServer;
import com.oryx.barcode.helper.ServiceHelper;
import com.oryx.barcode.model.ProductVO;
import com.oryx.barcode.service.AuthorizationService;

public abstract class ActionBarActivity extends AbstractActivity{


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_product) {
            ProductVO productVO = new ProductVO();
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_new_markrt) {
            Intent intent = new Intent(this, MarketDetailsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_find_product) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_find_market) {
            Intent intent = new Intent(this, MarketListActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_logout) {
            if (StaticServer.currentUser != null && StaticServer.currentUser.getId() != null) {
                ServiceHelper.authorizationService.disConnect(this, StaticServer.host, StaticServer.currentUser.getId());
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
            return true;
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_gps_trucker) {
            Intent intent = new Intent(this, TruckerViewActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
