package com.javacodegeeks.OryxBarcodeReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OryxBarcodeReader extends Activity {
	/** Called when the activity is first created. */

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    public static String contents;
	public static String format;
	public static byte[] rawBytes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
	    if(contents != null) {
            TextView contentTextView = (TextView) findViewById(R.id.content);
            contentTextView.setText(contents);
        }

        if(format != null) {
            TextView formatTextView = (TextView) findViewById(R.id.format);
            formatTextView.setText(format);
        }

        super.onResume();
    }

	public void scanBar(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			//intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {
			showDialog(OryxBarcodeReader.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
		}
	}

	public void scanQR(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			//intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {
			showDialog(OryxBarcodeReader.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				contents = intent.getStringExtra("SCAN_RESULT");
				format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				rawBytes = intent.getByteArrayExtra("SCAN_RESULT_BYTES");

				TextView contentTextView = (TextView) findViewById(R.id.content);
				contentTextView.setText(contents);

				TextView formatTextView = (TextView) findViewById(R.id.format);
				formatTextView.setText(format);

				/*int intentOrientation = intent.getIntExtra("SCAN_RESULT_ORIENTATION", Integer.MIN_VALUE);
				Integer orientation = intentOrientation == Integer.MIN_VALUE ? null : intentOrientation;
				String errorCorrectionLevel = intent.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");*/

				Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}
}