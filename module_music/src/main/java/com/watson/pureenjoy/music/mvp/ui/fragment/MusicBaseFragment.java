package com.watson.pureenjoy.music.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;

import es.dmoral.toasty.Toasty;
import me.jessyan.armscomponent.commonres.dialog.ProgressDialog;

/**
 * Created by jess on 8/5/16 14:11
 * contact with jess.yan.effort@gmail.com
 */
public abstract class MusicBaseFragment<P extends IPresenter> extends BaseFragment<P> implements IView {
    protected ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndicatorName("LineScalePulseOutRapidIndicator");
    }

    @Override
    public void showMessage(@NonNull String message) {
        Toasty.info(getContext(), message, Toast.LENGTH_SHORT, true).show();
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
