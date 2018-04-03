package com.oryx.activity.bu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.oryx.R;
import com.oryx.activity.core.AbstractActivity;

import butterknife.BindView;

public class ProductActivity extends AbstractActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    private static final String TAG = "ProductActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.codeField)
    EditText _codeField;
    @BindView(R.id.nameField)
    EditText _nameField;
    @BindView(R.id.descriptionField)
    EditText _descriptionField;
    @BindView(R.id.brandField)
    EditText _brandField;
    @BindView(R.id.categoryField)
    EditText _categoryField;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
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
            showDialog(ProductActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
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
}