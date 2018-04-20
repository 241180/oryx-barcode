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
import com.oryx.activity.map.MapViewActivity;
import com.oryx.activity.settings.SettingsActivity;
import com.oryx.context.IServer;
import com.oryx.service.AuthService;

public abstract class AbstractActivity extends AppCompatActivity {


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

    protected void savePreferences() {

    }

    protected void loadPreferences() {

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
            if (IServer.currentUser != null && IServer.currentUser.getId() != null) {
                AuthService.disConnect(IServer.host, IServer.currentUser.getId());
            }
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_gps_trucker) {
            Intent intent = new Intent(this, MapViewActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
