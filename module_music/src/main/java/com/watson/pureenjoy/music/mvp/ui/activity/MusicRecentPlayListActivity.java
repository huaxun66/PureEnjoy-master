package com.watson.pureenjoy.music.mvp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.EventBusManager;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.MusicRefreshEvent;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicRecentPlayAdapter;
import com.watson.pureenjoy.music.utils.MusicUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseEvent;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.SharedPreferenceUtil;

import static com.watson.pureenjoy.music.app.MusicConstants.LIST_RECENT_PLAY;

@Route(path = RouterHub.MUSIC_RECENT_PLAY_LIST_ACTIVITY)
public class MusicRecentPlayListActivity extends MusicBaseActivity {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;
    @BindView(R2.id.play)
    ImageView mPlayAllBtn;
    @BindView(R2.id.num)
    TextView mNumTv;
    @BindView(R2.id.manager)
    TextView mManagerBtn;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    private DBManager dbManager;
    private MusicRecentPlayAdapter mAdapter;
    private List<LocalMusicInfo> musicInfoList = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_recent_play_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTopBar.setTitleColor(Color.WHITE);
        dbManager = DBManager.getInstance(this);
        musicInfoList = dbManager.getMusicList(LIST_RECENT_PLAY);
        mAdapter = new MusicRecentPlayAdapter(this, R.layout.music_recent_play_item, musicInfoList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNumTv.setText(getString(R.string.music_song_num, musicInfoList.size()));
        initListener();
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
        mPlayAllBtn.setOnClickListener(v -> {
            MusicUtil.playMusic(this, musicInfoList.get(0), LIST_RECENT_PLAY, null);
            ARouter.getInstance().build(RouterHub.MUSIC_PLAY_DETAIL_ACTIVITY).navigation();
        });
        mManagerBtn.setOnClickListener(v -> showMessage("管理"));

        mAdapter.setOnItemClickListener(new MusicRecentPlayAdapter.OnItemClickListener() {
            @Override
            public void onDeleteMenuClick(int position) {
                LocalMusicInfo musicInfo = musicInfoList.get(position);
                dbManager.deleteMusicFromRecentPlayList(musicInfo);
                if ((int)SharedPreferenceUtil.getData(MusicConstants.KEY_ID, -1) == musicInfo.getId()) {
                    MusicUtil.playNextMusic(MusicRecentPlayListActivity.this);
                }
                EventBusManager.getInstance().post(new MusicRefreshEvent());
            }

            @Override
            public void onContentClick(int position) {
                LocalMusicInfo musicInfo = musicInfoList.get(position);
                MusicUtil.playMusic(MusicRecentPlayListActivity.this, musicInfo, false);
                ARouter.getInstance().build(RouterHub.MUSIC_PLAY_DETAIL_ACTIVITY).navigation();
            }
        });
    }


    public void updateView(){
        musicInfoList = dbManager.getMusicList(LIST_RECENT_PLAY);
        mAdapter.updateMusicInfoList(musicInfoList);
        mNumTv.setText(getString(R.string.music_song_num, musicInfoList.size()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event instanceof MusicRefreshEvent) {
            updateView();
        }
    }


}
