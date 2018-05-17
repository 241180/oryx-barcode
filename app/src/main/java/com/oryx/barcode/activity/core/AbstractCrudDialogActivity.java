package com.oryx.barcode.activity.core;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import com.oryx.barcode.R;
import com.oryx.barcode.model.EntityVO;
import com.oryx.barcode.helper.GuiHelper;
import com.oryx.barcode.model.ProductVO;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);

        this.bean = open(this.bean);

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
                    GuiHelper.showWorker(
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
                clear(v);
            }
        });

        _cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancel(v);
            }
        });
    }

    abstract protected int getLayoutResID();

    abstract protected E open(E bean);
    abstract protected void commit(final E bean);
    abstract protected void save(final E bean);
    abstract protected void delete(final E bean);

    protected void cancel(View v){
        finish();
    }
    abstract protected void clear(View v);

    public E getBean() {
        return bean;
    }

    public void setBean(E bean) {
        this.bean = bean;
    }
}
