package com.watson.pureenjoy.music.mvp.ui.activity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.promeg.pinyinhelper.Pinyin;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.EventBusManager;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.MusicRefreshEvent;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;
import com.watson.pureenjoy.music.mvp.ui.view.ScanView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.SharedPreferenceUtil;

@Route(path = RouterHub.MUSIC_SCAN_ACTIVITY)
public class MusicScanActivity extends MusicBaseActivity {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;
    @BindView(R2.id.start_scan_btn)
    Button scanBtn;
    @BindView(R2.id.scan_progress)
    TextView scanProgressTv;
    @BindView(R2.id.scan_path)
    TextView scanPathTv;
    @BindView(R2.id.scan_count)
    TextView scanCountTv;
    @BindView(R2.id.scan_filter_cb)
    CheckBox filterCb;
    @BindView(R2.id.scan_view)
    ScanView scanView;

    private DBManager dbManager;
    private boolean scanning = false;
    private int curMusicId;
    private String curMusicPath;
    private List<LocalMusicInfo> musicInfoList;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MusicConstants.SCAN_NO_MUSIC:
                    showMessage(getString(R.string.music_local_music_empty));
                    scanComplete();
                    break;
                case MusicConstants.SCAN_ERROR:
                    showMessage(getString(R.string.music_database_error));
                    scanComplete();
                    break;
                case MusicConstants.SCAN_COMPLETE:
                    initCurPlaying();
                    scanComplete();
                    break;
                case MusicConstants.SCAN_UPDATE:
                    int updateProgress = msg.getData().getInt("progress");
                    String path = msg.getData().getString("scanPath");
                    scanCountTv.setText(getString(R.string.music_scan_complete, updateProgress));
                    scanPathTv.setText(path);
                    break;
            }
        }
    };


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_scan;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        dbManager = DBManager.getInstance(this);
        mTopBar.setTitleColor(Color.WHITE);
        initListener();
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
        scanBtn.setOnClickListener(v -> {
            if (!scanning) {
                scanPathTv.setVisibility(View.VISIBLE);
                scanning = true;
                startScanLocalMusic();
                scanView.start();
                scanBtn.setText(getString(R.string.music_scan_stop));
            } else {
                scanPathTv.setVisibility(View.GONE);
                scanning = false;
                scanView.stop();
                scanCountTv.setText("");
                scanBtn.setText(getString(R.string.music_scan_start));
            }
        });
    }

    public void startScanLocalMusic() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String[] musicInfoArray = new String[]{
                            MediaStore.Audio.Media.TITLE,               //歌曲名称
                            MediaStore.Audio.Media.ARTIST,              //歌曲歌手
                            MediaStore.Audio.Media.ALBUM,               //歌曲的专辑名
                            MediaStore.Audio.Media.DURATION,            //歌曲时长
                            MediaStore.Audio.Media.DATA};               //歌曲文件的全路径
                    Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            musicInfoArray, null, null, null);
                    if (cursor!= null && cursor.getCount() != 0) {
                        musicInfoList = new ArrayList<>();
                        int progress = 0;
                        while (cursor.moveToNext()) {
                            if (!scanning){
                                return;
                            }
                            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));
                            String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
                            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));
                            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
                            String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));

                            if (filterCb.isChecked() && duration != null && Long.valueOf(duration) < 1000 * 60){
                                continue;
                            }

                            File file = new File(path);
                            String parentPath = file.getParentFile().getPath();

                            name = replaceUnknown(name);
                            singer = replaceUnknown(singer);
                            album = replaceUnknown(album);
                            path = replaceUnknown(path);

                            LocalMusicInfo musicInfo = new LocalMusicInfo();
                            musicInfo.setName(name);
                            musicInfo.setSinger(singer);
                            musicInfo.setAlbum(album);
                            musicInfo.setPath(path);
                            musicInfo.setParentPath(parentPath);
                            musicInfo.setFirstLetter(Pinyin.toPinyin(name.charAt(0)).toUpperCase().charAt(0)+"");
                            musicInfoList.add(musicInfo);
                            //扫描更新
                            Message msg = new Message();
                            msg.what = MusicConstants.SCAN_UPDATE;
                            Bundle data = new Bundle();
                            data.putInt("progress", ++progress);
                            data.putString("scanPath", path);
                            msg.setData(data);
                            handler.sendMessage(msg);  //更新UI界面
                            try {
                                sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        //扫描完成获取一下当前播放音乐及路径
                        curMusicId = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_ID, -1);
                        curMusicPath = dbManager.getMusicPath(curMusicId);

                        // 根据a-z进行排序源数据
                        Collections.sort(musicInfoList);
                        dbManager.updateAllMusic(musicInfoList);

                        //扫描完成
                        Message msg = new Message();
                        msg.what = MusicConstants.SCAN_COMPLETE;
                        handler.sendMessage(msg);  //更新UI界面
                    } else {
                        Message msg = new Message();
                        msg.what = MusicConstants.SCAN_NO_MUSIC;
                        handler.sendMessage(msg);  //更新UI界面
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    //扫描出错
                    Message msg = new Message();
                    msg.what = MusicConstants.SCAN_ERROR;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public static String replaceUnknown(String oldStr){
        try {
            if (oldStr != null){
                if (oldStr.equals("<unknown>")){
                    oldStr = oldStr.replaceAll("<unknown>", "未知");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return oldStr;
    }

    //初始化当前播放音乐，有可能当前正在播放音乐已经被过滤掉了
    private void initCurPlaying() {
        try {
            boolean contain = false;
            int id = 1;
            if (musicInfoList != null){
                for (LocalMusicInfo info : musicInfoList){
                    Log.d(TAG, "initCurPlaying: info.getPath() = "+info.getPath());
                    Log.d(TAG, "initCurPlaying: curMusicPath = "+ curMusicPath);
                    if (info.getPath().equals(curMusicPath)){
                        contain = true;
                        Log.d(TAG, "initCurPlaying: musicInfoList.indexOf(info) = "+musicInfoList.indexOf(info));
                        id = musicInfoList.indexOf(info) + 1;
                    }

                }
            }
            if (contain) {
                Log.d(TAG, "initCurPlaying: contains");
                Log.d(TAG, "initCurPlaying: id = "+id);
                SharedPreferenceUtil.putData(MusicConstants.KEY_ID, id);
            } else {
                Log.d(TAG, "initCurPlaying: !!!contains");
//                Intent intent = new Intent(MusicPlayerService.PLAYER_MANAGER_ACTION);
//                intent.putExtra(MusicConstants.COMMAND, MusicConstants.COMMAND_STOP);
//                sendBroadcast(intent);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void scanComplete(){
        scanBtn.setText(getString(R.string.music_finish));
        scanning = false;
        scanBtn.setOnClickListener(v -> {
            if (!scanning){
                EventBusManager.getInstance().post(new MusicRefreshEvent());
                finish();
            }
        });
        scanView.stop();
    }

}
