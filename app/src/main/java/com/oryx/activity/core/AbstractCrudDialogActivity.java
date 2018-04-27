package com.oryx.activity.core;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import com.oryx.R;
import com.oryx.activity.bu.ProductDetailsActivity;
import com.oryx.context.IServer;
import com.oryx.model.EntityVO;
import com.oryx.service.ProductService;
import com.oryx.utils.GuiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AbstractCrudDialogActivity<E extends EntityVO> extends NoActionBarActivity{

    @BindView(R.id.saveBtn)
    Button _saveBtn;
    @BindView(R.id.deleteBtn)
    Button _deleteBtn;
    @BindView(R.id.clearBtn)
    Button _clearBtn;
    @BindView(R.id.cancelBtn)
    Button _cancelBtn;

    private E bean;

    public AbstractCrudDialogActivity() {
        super();
        Object object = getIntent().getExtras().get("BEAN");
        if(object != null) {
            this.bean = (E) object;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        _saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    final ProgressDialog progressDialog = new ProgressDialog(AbstractCrudDialogActivity.this,
                            R.style.AppTheme_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Saving...");
                    progressDialog.show();
                    GuiUtils.showWorker(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    progressDialog.dismiss();
                                }
                            }, 3000);

                    Runnable addProduct = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                commit(bean);
                                save(bean);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    addProduct.run();
                }
        });

        _deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete(bean);
            }
        });

        _clearBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clear();
            }
        });

        _cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancel();
                finish();
            }
        });
    }

    abstract protected int getLayoutResID();

    abstract protected void open(E bean);
    abstract protected void commit(E bean);
    abstract protected void save(E bean);
    abstract protected void delete(E bean);

    protected void cancel(){
        finish();
    }

    abstract protected void clear();
}
