package com.watson.pureenjoy.music.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.MusicRefreshEvent;
import com.watson.pureenjoy.music.http.entity.local.LocalSingerInfo;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicLocalSingerAdapter;
import com.watson.pureenjoy.music.utils.MusicUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseEvent;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.KEY_OTHER;
import static com.watson.pureenjoy.music.app.MusicConstants.KEY_TYPE;
import static com.watson.pureenjoy.music.app.MusicConstants.LIST_SINGER;

@Route(path = RouterHub.MUSIC_LOCAL_SINGER_FRAGMENT)
public class MusicLocalSingerFragment extends MusicBaseFragment {
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    private DBManager dbManager;
    private MusicLocalSingerAdapter mAdapter;
    private List<LocalSingerInfo> singerInfoList = new ArrayList<>();

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.music_fragment_local_singer, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        dbManager = DBManager.getInstance(getContext());
        singerInfoList = MusicUtil.groupBySinger((ArrayList) dbManager.getMusicList(MusicConstants.LIST_ALL_MUSIC));
        mAdapter = new MusicLocalSingerAdapter(getContext(), R.layout.music_local_singer_item, singerInfoList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initListener();
    }

    @Override
    public void setData(@Nullable Object data) {
    }


    private void initListener() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> ARouter.getInstance().build(RouterHub.MUSIC_LOCAL_SONG_LIST_ACTIVITY)
                .withInt(KEY_TYPE, LIST_SINGER)
                .withString(KEY_OTHER, singerInfoList.get(position).getName())
                .navigation());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event instanceof MusicRefreshEvent) {
            singerInfoList = MusicUtil.groupBySinger((ArrayList) dbManager.getMusicList(MusicConstants.LIST_ALL_MUSIC));
            mAdapter.setNewData(singerInfoList);
        }
    }
}
