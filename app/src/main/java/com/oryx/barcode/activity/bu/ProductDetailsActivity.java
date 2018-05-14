package com.oryx.barcode.activity.bu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.oryx.barcode.R;
import com.oryx.barcode.activity.core.AbstractCrudDialogActivity;
import com.oryx.barcode.context.StaticServer;
import com.oryx.barcode.model.ProductVO;
import com.oryx.barcode.service.ProductService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends AbstractCrudDialogActivity<ProductVO> {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private static final String TAG = "ProductDetailsActivity";

    @BindView(R.id.codeField)
    EditText _codeField;
    @BindView(R.id.nameField)
    EditText _nameField;
    @BindView(R.id.descriptionField)
    EditText _descriptionField;
    @BindView(R.id.brandField)
    EditText _brandField;
    @BindView(R.id.categoryField)
    Spinner _categoryField;

    String xformat = null;

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
        ButterKnife.bind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        _codeField.setText(intent.getStringExtra("SCAN_RESULT"));
        xformat = intent.getStringExtra("SCAN_RESULT_FORMAT");
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void scanBar(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(ProductDetailsActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_product_dtails;
    }

    @Override
    protected void open(ProductVO bean) {

    }

    @Override
    protected void commit(ProductVO bean) {
        ProductVO productVO = new ProductVO();
        productVO.setCode(_codeField.getText().toString());
        productVO.setName(_nameField.getText().toString());
        productVO.setDescription(_descriptionField.getText().toString());
    }

    @Override
    protected void save(ProductVO bean) {
        ProductService.create(StaticServer.host, xformat != null ? xformat : "ETA", bean);
    }

    @Override
    protected void delete(ProductVO bean) {

    }

    @Override
    protected void clear() {

    }
}