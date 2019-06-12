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
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.MusicRefreshEvent;
import com.watson.pureenjoy.music.http.entity.local.LocalFolderInfo;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicLocalFolderAdapter;
import com.watson.pureenjoy.music.utils.MusicUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseEvent;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.KEY_TITLE;
import static com.watson.pureenjoy.music.app.MusicConstants.KEY_TYPE;
import static com.watson.pureenjoy.music.app.MusicConstants.LIST_FOLDER;

@Route(path = RouterHub.MUSIC_LOCAL_FOLDER_FRAGMENT)
public class MusicLocalFolderFragment extends MusicBaseFragment {
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    private DBManager dbManager;
    private MusicLocalFolderAdapter mAdapter;
    private List<LocalFolderInfo> folderInfoList = new ArrayList<>();

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.music_fragment_local_folder, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        dbManager = DBManager.getInstance(getContext());
        folderInfoList = MusicUtil.groupByFolder((ArrayList) dbManager.getAllMusicList());
        mAdapter = new MusicLocalFolderAdapter(getContext(), R.layout.music_local_folder_item, folderInfoList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initListener();
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    private void initListener() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> ARouter.getInstance().build(RouterHub.MUSIC_LOCAL_SONG_LIST_ACTIVITY)
                .withInt(KEY_TYPE, LIST_FOLDER)
                .withString(KEY_TITLE, folderInfoList.get(position).getPath())
                .navigation());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event instanceof MusicRefreshEvent) {
            folderInfoList = MusicUtil.groupByFolder((ArrayList) dbManager.getAllMusicList());
            mAdapter.setNewData(folderInfoList);
        }
    }
}
