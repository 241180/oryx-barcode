package com.oryx.activity.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oryx.R;
import com.oryx.activity.core.AbstractActivity;
import com.oryx.activity.bu.ProductDetailsActivity;
import com.oryx.activity.login.LoginActivity;
import com.oryx.context.IServer;
import com.oryx.model.ProductVO;
import com.oryx.service.AuthService;
import com.oryx.service.ProductService;
import com.oryx.utils.GuiUtils;

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

     public void scanBar(View v) {
        IServer.host = _hostField.getText().toString();

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


        Runnable createProduct = new Runnable() {
            @Override
            public void run() {
                try {
                    ProductVO productVO = new ProductVO();
                    productVO.setCode("444444");
                    productVO.setDescription("createProduct");
                    ProductService.createProduct(IServer.host, "ETA", productVO);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        createProduct.run();


        /*try {
            Intent intent = new Intent(ACTION_SCAN);
            //intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
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
}