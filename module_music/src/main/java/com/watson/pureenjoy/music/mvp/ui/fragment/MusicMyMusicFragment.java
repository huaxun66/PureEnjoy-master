package com.watson.pureenjoy.music.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Route(path = RouterHub.MUSIC_MY_MUSIC_FRAGMENT)
public class MusicMyMusicFragment extends MusicBaseFragment {
    @BindView(R2.id.local_music)
    RelativeLayout mLocalMusic;
    @BindView(R2.id.recent_play)
    RelativeLayout mRecentPlay;
    @BindView(R2.id.my_radio)
    RelativeLayout mMyRadio;
    @BindView(R2.id.my_collect)
    RelativeLayout mMyCollection;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.music_fragment_my_music, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initListener();
    }

    private void initListener() {
        mLocalMusic.setOnClickListener(v -> ARouter.getInstance().build(RouterHub.MUSIC_LOCAL_MUSIC_ACTIVITY).navigation());
        mRecentPlay.setOnClickListener(v -> {

        });
        mMyRadio.setOnClickListener(v -> {

        });
        mMyCollection.setOnClickListener(v -> {

        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }
}
