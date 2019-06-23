package com.watson.pureenjoy.music.mvp.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.integration.EventBusManager;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.SheetRefreshEvent;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;
import com.watson.pureenjoy.music.http.entity.local.LocalSheetInfo;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class MusicAddSheetWindow extends PopupWindow {
    private Activity activity;
    private LocalMusicInfo musicInfo;
    private LinearLayout addLl;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private DBManager dbManager;

    public MusicAddSheetWindow(Activity activity, LocalMusicInfo musicInfo) {
        super(activity);
        this.activity = activity;
        this.musicInfo = musicInfo;
        dbManager = DBManager.getInstance(activity);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(activity).inflate(R.layout.music_pop_window_add_sheet, null);
        // 设置视图
        setContentView(view);
        // 设置弹出窗体的宽和高,不设置显示不出来
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int height = (int) (size.y * 0.6);
        setHeight(height);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        setFocusable(true);
        // 设置外部可点击
        setOutsideTouchable(true);
        // 设置弹出窗体的背景
        setBackgroundDrawable(activity.getResources().getDrawable(R.color.public_white));
        // 设置弹出窗体显示时的动画，从底部向上弹出
        setAnimationStyle(R.style.pop_window_animation);

        recyclerView = view.findViewById(R.id.pop_add_sheet_rv);
        adapter = new Adapter(activity, R.layout.music_created_sheet_item, dbManager.getMyCreateSheet());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        addLl = view.findViewById(R.id.pop_add_sheet_ll);
        addLl.setOnClickListener(v -> {
            //添加歌单
            View view1 = LayoutInflater.from(activity).inflate(R.layout.music_dialog_create_sheet, null);
            EditText playlistEt = view1.findViewById(R.id.dialog_sheet_name_et);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setView(view1)
                    .setPositiveButton(activity.getResources().getString(R.string.str_sure), (dialog, which) -> {
                        String name = playlistEt.getText().toString();
                        dbManager.createSheet(name);
                        dialog.dismiss();
                        adapter.setNewData(dbManager.getMyCreateSheet());
                        EventBusManager.getInstance().post(new SheetRefreshEvent());
                    })
                    .setNegativeButton(activity.getResources().getString(R.string.str_cancel), (dialog, which) -> dialog.dismiss());
            builder.show();
        });
    }

    private class Adapter extends BaseQuickAdapter<LocalSheetInfo, BaseViewHolder> {
        private Context mContext;

        public Adapter(Context context, int layoutResId, List<LocalSheetInfo> data) {
            super(layoutResId, data);
            this.mContext = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, LocalSheetInfo info) {
            helper.setText(R.id.sheet_name_tv, info.getName())
                    .setText(R.id.sheet_music_count_tv, mContext.getString(R.string.music_sheet_song_num, info.getCount()));
            ((SwipeMenuLayout) helper.getView(R.id.sheet_content_swipe_view)).setSwipeEnable(false);
            helper.getView(R.id.sheet_content_ll).setOnClickListener(v -> {
                if (dbManager.isExistInSheet(info.getId(), musicInfo.getId())) {
                    Toasty.error(mContext, mContext.getString(R.string.music_sheet_contains_song)).show();
                } else {
                    dbManager.addToSheet(info.getId(), musicInfo.getId());
                    Toasty.info(mContext, mContext.getString(R.string.music_add_sheet_song_success)).show();
                    EventBusManager.getInstance().post(new SheetRefreshEvent());
                }
                dismiss();
            });
        }
    }
}

