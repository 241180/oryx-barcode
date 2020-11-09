package com.oryx.barcode.activity.bu;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.oryx.barcode.activity.core.AbstractListActivity;
import com.oryx.barcode.adapters.ProductListAdapter;

public class ProductListActivity extends AbstractListActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    static final String[] MOBILE_OS =
            new String[]{"Android", "iOS", "WindowsMobile", "Blackberry"};
    private static final String TAG = "ProductListActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ProductListAdapter(this, MOBILE_OS));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

    }
}