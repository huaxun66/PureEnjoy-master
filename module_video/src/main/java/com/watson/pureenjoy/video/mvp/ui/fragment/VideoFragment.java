package com.watson.pureenjoy.video.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import me.jessyan.armscomponent.commonres.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import com.watson.pureenjoy.video.R;

@Route(path = RouterHub.VIDEO_HOMEFRAGMENT)
public class VideoFragment extends BaseSupportFragment {

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.video_fragment_home, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }
}
