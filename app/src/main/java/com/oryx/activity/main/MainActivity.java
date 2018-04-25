package com.oryx.activity.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oryx.R;
import com.oryx.activity.bu.MarketDetailsActivity;
import com.oryx.activity.bu.MarketListActivity;
import com.oryx.activity.bu.ProductDetailsActivity;
import com.oryx.activity.bu.ProductListActivity;
import com.oryx.activity.core.AbstractActivity;
import com.oryx.activity.login.LoginActivity;
import com.oryx.activity.map.TruckerViewActivity;
import com.oryx.activity.settings.SettingsActivity;
import com.oryx.context.IServer;
import com.oryx.service.AuthService;
import com.oryx.service.ProductService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AbstractActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    public static String barCode;
    public static String format;
    public static String description;
    public static byte[] rawBytes;

    @BindView(R.id.barCodeField)
    TextView _barCodeField;
    @BindView(R.id.formatField)
    TextView _formatField;
    @BindView(R.id.descriptionField)
    TextView _descriptionField;

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        if (barCode != null) {
            _barCodeField.setText(barCode);
        }

        if (format != null) {
            _formatField.setText(format);
        }

        if (description != null) {
            _descriptionField.setText(description);
        }

        super.onResume();
    }

    public void scanBar(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            //intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                barCode = intent.getStringExtra("SCAN_RESULT");
                format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                rawBytes = intent.getByteArrayExtra("SCAN_RESULT_BYTES");

                _barCodeField.setText(barCode);

                _formatField.setText(format);

				/*int intentOrientation = intent.getIntExtra("SCAN_RESULT_ORIENTATION", Integer.MIN_VALUE);
				Integer orientation = intentOrientation == Integer.MIN_VALUE ? null : intentOrientation;
				String errorCorrectionLevel = intent.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");*/

                Toast toast = Toast.makeText(this, "Code:" + barCode + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();

                Runnable getProduct = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ProductService.getProduct(IServer.host, barCode, format, _descriptionField);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                getProduct.run();
            }
        }
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
            Intent intent = new Intent(this, TruckerViewActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}