package com.watson.pureenjoy.music.mvp.ui.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.integration.EventBusManager;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.MusicRefreshEvent;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;
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
    private View parentView;
    private boolean isSongLoved;
    private DBManager dbManager;

    public MusicPopMenuWindow(Activity activity, LocalMusicInfo musicInfo, View parentView) {
        super(activity);
        this.activity = activity;
        this.musicInfo = musicInfo;
        this.parentView = parentView;
        initView();
    }

    private void initView() {
        dbManager = DBManager.getInstance(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.music_pop_window_menu, null);
        nameTv = view.findViewById(R.id.pop_win_name_tv);
        addLl = view.findViewById(R.id.pop_win_add_rl);
        loveLl = view.findViewById(R.id.pop_win_love_ll);
        deleteLl = view.findViewById(R.id.pop_win_delete_ll);
        cancelLl = view.findViewById(R.id.pop_win_cancel_ll);
        loveIv = view.findViewById(R.id.pop_win_love_iv);
        nameTv.setText(activity.getString(R.string.music_song) + ":" + musicInfo.getName());
        isSongLoved = dbManager.isSongMyLove(musicInfo.getId());
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
            dbManager.setSongLoveStatus(musicInfo.getId(), !isSongLoved);
            EventBusManager.getInstance().post(new MusicRefreshEvent());
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
        String path = dbManager.getMusicPath(curId);

        MusicDeleteDialog mDialog = new MusicDeleteDialog(context);
        mDialog.setDialogButtonClickListener(new MusicDeleteDialog.DialogButtonClickListener() {
            @Override
            public void onDelete(boolean checked) {
                dbManager.deleteMusic(curId);
                //移除的是当前播放的音乐
                if (curId == musicId) {
                    MusicUtil.stopMusic(activity);
                    SharedPreferenceUtil.putData(MusicConstants.KEY_ID, -1);
                }
                //同时删除文件
                if (checked) {
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                        MusicUtil.deleteMediaDB(file, activity);
                    }
                }
                EventBusManager.getInstance().post(new MusicRefreshEvent());
            }

            @Override
            public void onCancel() {
            }

        });
        mDialog.show();
    }


}
