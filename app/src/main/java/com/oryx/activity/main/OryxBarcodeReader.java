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

public class OryxBarcodeReader extends AbstractActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    public static String barCode;
    public static String format;
    public static String description;
    public static byte[] rawBytes;

    @BindView(R.id.mapView)
    MapView mMapView;

    private GoogleMap googleMap;

    /*@BindView(R.id.barCodeField)
    TextView _barCodeField;
    @BindView(R.id.formatField)
    TextView _formatField;
    @BindView(R.id.descriptionField)
    TextView _descriptionField;
    @BindView(R.id.hostField)*/

    EditText _hostField;
    @BindView(R.id.configBtn)
    AppCompatButton configBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        initComponenet();
        GuiUtils.configure_gps(this);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    public void requestLocationUpdates(View v) {
        GuiUtils.configure_gps(this);
        super.requestLocationUpdates();
    }

    @Override
    protected void onLocationChange(Location location){
        IServer.location = location;
        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(IServer.location.getLatitude(), IServer.location.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //_descriptionField.setText("GPS: " + location.getLongitude() + " " + location.getLatitude());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        if (barCode != null) {
            //_barCodeField.setText(barCode);
        }

        if (format != null) {
            //_formatField.setText(format);
        }

        if (description != null) {
            //_descriptionField.setText(description);
        }

        super.onResume();
        mMapView.onResume();
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
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            if(IServer.currentUser != null && IServer.currentUser.getId() != null) {
                AuthService.disConnect(IServer.host, IServer.currentUser.getId());
            }
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

                //_barCodeField.setText(barCode);

                //_formatField.setText(format);

				/*int intentOrientation = intent.getIntExtra("SCAN_RESULT_ORIENTATION", Integer.MIN_VALUE);
				Integer orientation = intentOrientation == Integer.MIN_VALUE ? null : intentOrientation;
				String errorCorrectionLevel = intent.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");*/

                Toast toast = Toast.makeText(this, "Code:" + barCode + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();

                Runnable getProduct = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //ProductService.getProduct(IServer.host, barCode, format, _descriptionField);
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
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}