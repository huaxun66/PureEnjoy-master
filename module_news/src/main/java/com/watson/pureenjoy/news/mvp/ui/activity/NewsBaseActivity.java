package com.watson.pureenjoy.news.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;

import es.dmoral.toasty.Toasty;
import me.jessyan.armscomponent.commonres.dialog.ProgressDialog;

/**
 * Created by jess on 8/5/16 13:13
 * contact with jess.yan.effort@gmail.com
 */
public abstract class NewsBaseActivity<P extends IPresenter> extends BaseActivity<P> implements IView {
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mProgressDialog = new ProgressDialog(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showMessage(@NonNull String message) {
        Toasty.info(this, message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.dismiss();
    }
}
