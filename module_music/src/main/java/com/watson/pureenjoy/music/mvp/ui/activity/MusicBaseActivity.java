package com.watson.pureenjoy.music.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.R;

import es.dmoral.toasty.Toasty;
import me.jessyan.armscomponent.commonres.dialog.ProgressDialog;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtil;

/**
 * Created by jess on 8/5/16 13:13
 * contact with jess.yan.effort@gmail.com
 */
public abstract class MusicBaseActivity<P extends IPresenter> extends com.jess.arms.base.BaseActivity<P> implements IView {
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.music_theme), 0);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndicatorName("LineScalePulseOutRapidIndicator");
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
