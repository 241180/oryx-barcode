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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oryx.R;
import com.oryx.activity.core.AbstractActivity;
import com.oryx.activity.bu.ProductActivity;
import com.oryx.activity.login.LoginActivity;
import com.oryx.model.ProductVO;
import com.oryx.service.ProductService;
import com.oryx.utils.HttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OryxBarcodeReader extends AbstractActivity {
    /**
     * Called when the activity is first created.
     */

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    public static String barCode;
    public static String format;
    public static String description;
    public static byte[] rawBytes;
    public static String host;

    @BindView(R.id.barCodeField)
    TextView _barCodeField;
    @BindView(R.id.formatField)
    TextView _formatField;
    @BindView(R.id.descriptionField)
    TextView _descriptionField;
    @BindView(R.id.hostField)
    EditText _hostField;

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_item1) {
            return true;
        } else if (id == R.id.action_new_product) {
            Intent intent = new Intent(this, ProductActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void scanBar(View v) {
        this.host = _hostField.getText().toString();

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ProductService.getProduct(host, "123456", "TST", _descriptionField);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();*/


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ProductVO productVO = new ProductVO();
                    productVO.setProductCode("444444");
                    productVO.setDescription("createProduct");
                    ProductService.createProduct(host, productVO);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        /*try {
            Intent intent = new Intent(ACTION_SCAN);
            //intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(OryxBarcodeReader.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }*/
    }

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

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ProductService.getProduct(host, barCode, format, _descriptionField);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }
    }
}