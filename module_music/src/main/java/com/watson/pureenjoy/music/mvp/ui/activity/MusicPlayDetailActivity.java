package com.watson.pureenjoy.music.mvp.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.EventBusManager;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.MusicRefreshEvent;
import com.watson.pureenjoy.music.mvp.service.MusicPlayerService;
import com.watson.pureenjoy.music.mvp.ui.view.BackgroundAnimationRelativeLayout;
import com.watson.pureenjoy.music.mvp.ui.view.DiscView;
import com.watson.pureenjoy.music.mvp.ui.view.MusicPlayListWindow;
import com.watson.pureenjoy.music.mvp.ui.view.PlayerSeekBar;
import com.watson.pureenjoy.music.mvp.ui.view.lrc.LrcView;
import com.watson.pureenjoy.music.utils.ImageUtil;
import com.watson.pureenjoy.music.utils.MusicUtil;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.SharedPreferenceUtil;

@Route(path = RouterHub.MUSIC_PLAY_DETAIL_ACTIVITY)
public class MusicPlayDetailActivity extends MusicBaseActivity {
    @BindView(R2.id.root_layout)
    BackgroundAnimationRelativeLayout mRootLayout;
    @BindView(R2.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R2.id.disc_view)
    DiscView mDisc;
    @BindView(R2.id.lrc_view_container)
    RelativeLayout mLrcViewContainer;
    @BindView(R2.id.lrc_view)
    LrcView mLrcView;
    @BindView(R2.id.target_lrc)
    TextView mTryGetLrc;
    @BindView(R2.id.music_tool)
    LinearLayout mMusicTool;
    @BindView(R2.id.playing_mode)
    ImageView mPlayingMode;
    @BindView(R2.id.playing_play)
    ImageView mControl;
    @BindView(R2.id.playing_next)
    ImageView mNext;
    @BindView(R2.id.playing_pre)
    ImageView mPre;
    @BindView(R2.id.playing_playlist)
    ImageView mPlaylist;
    @BindView(R2.id.playing_more)
    ImageView mMore;
    @BindView(R2.id.playing_comment)
    ImageView mComment;
    @BindView(R2.id.playing_fav)
    ImageView mFav;
    @BindView(R2.id.playing_down)
    ImageView mDown;
    @BindView(R2.id.music_duration_played)
    TextView mTimePlayed;
    @BindView(R2.id.music_duration)
    TextView mDuration;
    @BindView(R2.id.play_seek)
    PlayerSeekBar mProgress;
    @BindView(R2.id.volume_seek)
    SeekBar mVolumeSeek;
    ActionBar ab;

    private MusicPlayerService mService;
    private DBManager dbManager;

    private Runnable mUpdateProgress = new Runnable() {
        @Override
        public void run() {
            if (mProgress != null && mService != null) {
                int position = mService.getCurrentPosition();
                int duration = mService.getDuration();
                if (duration > 0 && duration < 627080716) {
                    mProgress.setProgress((1000 * position / duration));
                    mTimePlayed.setText(MusicUtil.getTimeString(position));
                    mDuration.setText(MusicUtil.getTimeString(duration));
                }
                if (mService.isPlaying()) {
                    mProgress.postDelayed(mUpdateProgress, 200);
                } else {
                    mProgress.removeCallbacks(mUpdateProgress);
                }
            }
        }
    };


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = ((MusicPlayerService.MusicPlayBinder)iBinder).getService();
            setPlayStatus();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_play_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.public_nav_back_white);

        mProgress.setIndeterminate(false);
        mProgress.setProgress(1);
        mProgress.setMax(1000);

        dbManager = DBManager.getInstance(this);
        setLoveStatus();
        setPlayMode();
        initListener();
        //设置数据源
        mDisc.setMusicDataList(MusicUtil.getCurrentPlayList(this), MusicUtil.getCurrentPlayInfo(this));
    }

    private void initListener() {
        mToolBar.setNavigationOnClickListener(v -> onBackPressed());
        mFav.setOnClickListener(v -> {
            int musicId = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_ID, -1);
            if (dbManager.isSongMyLove(musicId)) {
                dbManager.setSongLoveStatus(musicId, false);
                mFav.setImageResource(R.drawable.music_play_icon_love_selector);
            } else {
                dbManager.setSongLoveStatus(musicId, true);
                mFav.setImageResource(R.drawable.music_play_icon_loved_selector);
            }
            EventBusManager.getInstance().post(new MusicRefreshEvent());
        });
        mProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress * mService.getDuration() / 1000;
                mLrcView.seekTo(progress, true, fromUser);
                if (fromUser) {
                    MusicUtil.seekMusic(MusicPlayDetailActivity.this, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mPlayingMode.setOnClickListener(v -> {
            int playMode = MusicUtil.updatePlayMode();
            if (playMode == MusicConstants.PLAY_MODE_SEQUENCE) {
                Toasty.info(this, getString(R.string.music_mode_sequence)).show();
            } else if (playMode == MusicConstants.PLAY_MODE_SINGLE_REPEAT) {
                Toasty.info(this, getString(R.string.music_mode_single)).show();
            } else if (playMode == MusicConstants.PLAY_MODE_RANDOM) {
                Toasty.info(this, getString(R.string.music_mode_random)).show();
            }
            setPlayMode();
        });
        mPre.setOnClickListener(v -> mDisc.prev());
        mControl.setOnClickListener(v -> mDisc.playOrPause());
        mNext.setOnClickListener(v -> mDisc.next());
        mPlaylist.setOnClickListener(v -> {
            MusicPlayListWindow menuPopupWindow = new MusicPlayListWindow(MusicPlayDetailActivity.this);
            menuPopupWindow.setOnItemClickListener((info, position) -> {
                MusicUtil.playMusic(this, info, false);
                mDisc.setPosition(position);
            });
            menuPopupWindow.showAtLocation(mRootLayout, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        });
        mDisc.setPlayInfoListener(new DiscView.IPlayInfo() {
            @Override
            public void onMusicInfoChanged(String musicName, String musicSinger) {
                ab.setTitle(musicName);
                ab.setSubtitle(musicSinger);
            }

            @Override
            public void onMusicPicChanged(String albumThumbs) {
                updateMusicPlayBackground(albumThumbs);
            }

            @Override
            public void onMusicChanged(DiscView.MusicChangedStatus musicChangedStatus) {
                switch (musicChangedStatus) {
                    case PLAY:{
                        mControl.setImageResource(R.drawable.music_play_btn_pause_selector);
                        MusicUtil.resumeMusic(MusicPlayDetailActivity.this);
                        mProgress.postDelayed(mUpdateProgress, 200);
                        break;
                    }
                    case PAUSE:{
                        mControl.setImageResource(R.drawable.music_play_btn_play_selector);
                        MusicUtil.pauseMusic(MusicPlayDetailActivity.this);
                        break;
                    }
                    case NEXT:{
                        MusicUtil.playNextMusic(MusicPlayDetailActivity.this);
                        break;
                    }
                    case PREV:{
                        MusicUtil.playPreMusic(MusicPlayDetailActivity.this);
                        break;
                    }
                    case STOP:{
                        MusicUtil.stopMusic(MusicPlayDetailActivity.this);
                        break;
                    }
                }
            }
        });
    }

    private void updateMusicPlayBackground(String albumThumbs) {
        if (mRootLayout.isNeed2UpdateBackground(albumThumbs)) {
            new Thread(() -> {
                Drawable foregroundDrawable = ImageUtil.getForegroundDrawable(this, albumThumbs);
                runOnUiThread(() -> {
                    mRootLayout.setForeground(foregroundDrawable);
                    mRootLayout.beginAnimation();
                });
            }).start();
        }
    }


    private void setPlayMode() {
        int playMode = (int) SharedPreferenceUtil.getData(MusicConstants.KEY_MODE, MusicConstants.PLAY_MODE_SEQUENCE);
        if (playMode == MusicConstants.PLAY_MODE_SEQUENCE) {
            mPlayingMode.setImageResource(R.drawable.music_play_mode_sequence);
        } else if (playMode == MusicConstants.PLAY_MODE_SINGLE_REPEAT) {
            mPlayingMode.setImageResource(R.drawable.music_play_mode_single);
        } else if (playMode == MusicConstants.PLAY_MODE_RANDOM) {
            mPlayingMode.setImageResource(R.drawable.music_play_icon_radom);
        }
    }

    private void setPlayStatus() {
        if (mService.isPlaying() && !mDisc.isPlaying()) {
            mDisc.playOrPause();
        }
    }

    private void setLoveStatus() {
        int musicId = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_ID, -1);
        if (dbManager.isSongMyLove(musicId)) {
            mFav.setImageResource(R.drawable.music_play_icon_loved_selector);
        } else {
            mFav.setImageResource(R.drawable.music_play_icon_love_selector);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Intent it = new Intent(this, MusicPlayerService.class);
        bindService(it, connection, BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        unbindService(connection);
        mProgress.removeCallbacks(mUpdateProgress);
    }
}
