package com.watson.pureenjoy.music.mvp.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;
import com.watson.pureenjoy.music.utils.MusicUtil;

import me.jessyan.armscomponent.commonsdk.utils.SharedPreferenceUtil;

import static com.watson.pureenjoy.music.app.MusicConstants.KEY_CURRENT;

public class MusicPlayerService extends Service {
    private final String TAG = "MusicPlayerService";
    private MediaPlayer mediaPlayer; // 媒体播放器对象
    private DBManager dbManager;
    private MusicPlayBinder mBinder = new MusicPlayBinder();
    private int currentMusicId;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        dbManager = DBManager.getInstance(this);
        //设置播放完成监听器
        mediaPlayer.setOnCompletionListener(mp -> MusicUtil.playNextMusic(this));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int command = intent.getIntExtra(MusicConstants.COMMAND, -1);
        Log.d(TAG, "command="+command);
        switch (command) {
            case MusicConstants.COMMAND_PLAY:
                play();
                break;
            case MusicConstants.COMMAND_PAUSE:
                pause();
                break;
            case MusicConstants.COMMAND_RESUME:
                resume();
                break;
            case MusicConstants.COMMAND_STOP:
                stop();
                break;
            case MusicConstants.COMMAND_PROGRESS:
                int currentTime = intent.getIntExtra(KEY_CURRENT, 0);
                seek(currentTime);
                break;
        }
        return START_STICKY;
    }

    /**
     * 播放音乐
     */
    private void play() {
        try {
            int musicId = (int) SharedPreferenceUtil.getData(MusicConstants.KEY_ID, -1);
            //播放新歌曲
            if (currentMusicId != musicId) {
                currentMusicId = musicId;
                LocalMusicInfo musicInfo = dbManager.getMusicById(musicId);
                mediaPlayer.reset(); // 把各项参数恢复到初始状态
                mediaPlayer.setDataSource(musicInfo.getPath());
                mediaPlayer.prepare(); //进行缓冲
                mediaPlayer.setOnPreparedListener(null);
                mediaPlayer.start(); // 开始播放
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停音乐
     */
    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * 重新开始音乐
     */
    private void resume() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    /**
     * 停止音乐
     */
    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * 改变音乐进度
     */
    private void seek(int currentTime) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(currentTime);
        }
    }


    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public int getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    public class MusicPlayBinder extends Binder {
        public MusicPlayerService getService() {
            return MusicPlayerService.this;
        }
    }

}

