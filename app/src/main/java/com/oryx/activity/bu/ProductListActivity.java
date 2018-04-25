package com.oryx.activity.bu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.oryx.R;
import com.oryx.activity.core.AbstractActivity;
import com.oryx.context.IServer;
import com.oryx.model.ProductVO;
import com.oryx.service.ProductService;
import com.oryx.utils.GuiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends ListActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    private static final String TAG = "ProductListActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        String[] itemname ={
                "Safari",
                "Camera",
                "Global",
                "FireFox",
                "UC Browser",
                "Android Folder",
                "VLC Player",
                "Cold War"
        };

        this.setListAdapter(new ArrayAdapter<>(
                this, R.layout.activity_product_list,
                R.id.Itemname,itemname));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(this, "Position : " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }
}