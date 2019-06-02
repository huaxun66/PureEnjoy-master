package com.watson.pureenjoy.music.mvp.ui.view;

import android.app.Activity;
import android.content.Context;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;
import com.watson.pureenjoy.music.http.entity.local.MusicSheetInfo;
import com.watson.pureenjoy.music.utils.MusicUtil;

import java.io.File;

import me.jessyan.armscomponent.commonsdk.utils.SharedPreferenceUtil;

public class MusicPopMenuWindow extends PopupWindow {
    private static final String TAG = MusicPopMenuWindow.class.getName();
    private Activity activity;
    private TextView nameTv;
    private LinearLayout addLl;
    private LinearLayout loveLl;
    private ImageView loveIv;
    private LinearLayout deleteLl;
    private LinearLayout cancelLl;
    private LocalMusicInfo musicInfo;
    private MusicSheetInfo musicSheetInfo;
    private int from;
    private View parentView;
    private boolean isSongLoved;

    public MusicPopMenuWindow(Activity activity, LocalMusicInfo musicInfo, View parentView, int from) {
        super(activity);
        this.activity = activity;
        this.musicInfo = musicInfo;
        this.parentView = parentView;
        this.from = from;
        initView();
    }

    public MusicPopMenuWindow(Activity activity, LocalMusicInfo musicInfo, View parentView, int from, MusicSheetInfo musicSheetInfo) {
        super(activity);
        this.activity = activity;
        this.musicInfo = musicInfo;
        this.parentView = parentView;
        this.from = from;
        this.musicSheetInfo = musicSheetInfo;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(activity).inflate(R.layout.music_pop_window_menu, null);
        nameTv = view.findViewById(R.id.pop_win_name_tv);
        addLl = view.findViewById(R.id.pop_win_add_rl);
        loveLl = view.findViewById(R.id.pop_win_love_ll);
        deleteLl = view.findViewById(R.id.pop_win_delete_ll);
        cancelLl = view.findViewById(R.id.pop_win_cancel_ll);
        loveIv = view.findViewById(R.id.pop_win_love_iv);
        nameTv.setText(activity.getString(R.string.music_song) + ":" + musicInfo.getName());
        isSongLoved = MusicUtil.isSongMyLove(activity, musicInfo.getId());
        loveIv.setSelected(isSongLoved);
        // 设置视图
        setContentView(view);
        // 设置弹出窗体的宽和高,不设置显示不出来
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体可点击
        setFocusable(true);
        // 设置外部可点击
        setOutsideTouchable(true);
        // 设置弹出窗体的背景
        setBackgroundDrawable(activity.getResources().getDrawable(R.color.public_white));
        // 设置弹出窗体显示时的动画，从底部向上弹出
        setAnimationStyle(R.style.pop_window_animation);

        addLl.setOnClickListener(v -> {
            dismiss();
            MusicAddSheetWindow musicAddSheetWindow = new MusicAddSheetWindow(activity, musicInfo);
            musicAddSheetWindow.showAtLocation(parentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            //当弹出PopupWindow时，背景变半透明
            params.alpha = 0.7f;
            activity.getWindow().setAttributes(params);
            //设置PopupWindow关闭监听，当PopupWindow关闭，背景恢复1f
            musicAddSheetWindow.setOnDismissListener(() -> {
                WindowManager.LayoutParams params1 = activity.getWindow().getAttributes();
                params1.alpha = 1f;
                activity.getWindow().setAttributes(params1);
            });
        });

        loveLl.setOnClickListener(v -> {
            MusicUtil.setSongLoveStatus(activity, musicInfo.getId(), !isSongLoved);
            dismiss();
            View customView = LayoutInflater.from(activity).inflate(R.layout.music_love_toast_layout, null);
            ImageView image = customView.findViewById(R.id.toast_icon);
            TextView text = customView.findViewById(R.id.toast_text);
            image.setSelected(!isSongLoved);
            text.setText(isSongLoved ? activity.getString(R.string.music_cancel_song_love_success) : activity.getString(R.string.music_set_song_love_success));
            Toast toast = new Toast(activity);
            toast.setView(customView);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });

        deleteLl.setOnClickListener(v -> {
            deleteOperate(musicInfo, activity);
            dismiss();
        });

        cancelLl.setOnClickListener(v -> dismiss());
    }

    public void deleteOperate(LocalMusicInfo musicInfo, final Context context) {
        int curId = musicInfo.getId();
        int musicId = (int) SharedPreferenceUtil.getData(MusicConstants.KEY_ID, -1);
        DBManager dbManager = DBManager.getInstance(context);
        String path = dbManager.getMusicPath(curId);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.music_dialog_delete_file, null);
        CheckBox deleteFile = view.findViewById(R.id.dialog_delete_cb);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton(activity.getString(R.string.music_delete), (dialog1, which) -> {
                    //同时删除文件
                    if (deleteFile.isChecked()) {
                        //删除的是当前播放的音乐
                        File file = new File(path);
                        if (file.exists()) {
                            deleteMediaDB(file, context);
                            boolean ret = file.delete();
                            Log.e(TAG, "onClick: ret = " + ret);
                            dbManager.deleteMusic(curId);
                        }
                        if (curId == musicId) {
//                    Intent intent = new Intent(MusicPlayerService.PLAYER_MANAGER_ACTION);
//                    intent.putExtra(Constant.COMMAND, Constant.COMMAND_STOP);
//                    context.sendBroadcast(intent);
                            SharedPreferenceUtil.putData(MusicConstants.KEY_ID, dbManager.getFirstId(MusicConstants.LIST_ALLMUSIC));
                        }
                    } else {
                        //从列表移除
                        if (from == MusicConstants.ACTIVITY_MYLIST) {
                            dbManager.removeMusicFromPlaylist(curId, musicSheetInfo.getId());
                        } else {
                            dbManager.removeMusic(curId, from);
                        }

                        if (curId == musicId) {
                            //移除的是当前播放的音乐
//                    Intent intent = new Intent(MusicPlayerService.PLAYER_MANAGER_ACTION);
//                    intent.putExtra(Constant.COMMAND, Constant.COMMAND_STOP);
//                    context.sendBroadcast(intent);
                        }
                    }
                    if (onDeleteUpdateListener != null) {
                        onDeleteUpdateListener.onDeleteUpdate();
                    }
                    dialog1.dismiss();
                })
                .setNegativeButton(activity.getString(R.string.str_cancel), (dialog12, which) -> dialog12.dismiss());
        builder.show();
    }

    public static void deleteMediaDB(File file, Context context) {
        String filePath = file.getPath();
//        if(filePath.endsWith(".mp3")){
        int res = context.getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Audio.Media.DATA + "= \"" + filePath + "\"",
                null);
        Log.i(TAG, "deleteMediaDB: res = " + res);
//        }
    }

    private OnDeleteUpdateListener onDeleteUpdateListener;

    public void setOnDeleteUpdateListener(OnDeleteUpdateListener onDeleteUpdateListener) {
        this.onDeleteUpdateListener = onDeleteUpdateListener;
    }

    public interface OnDeleteUpdateListener {
        void onDeleteUpdate();
    }
}
