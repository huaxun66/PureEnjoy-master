package com.watson.pureenjoy.music.mvp.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;
import com.watson.pureenjoy.music.utils.MusicUtil;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.utils.SharedPreferenceUtil;

public class MusicPlayListWindow extends PopupWindow {
    private static final String TAG = MusicPlayListWindow.class.getName();
    private Activity activity;
    private TextView mTitle;
    private RecyclerView mRecyclerView;
    private Adapter adapter;
    private List<LocalMusicInfo> musicInfoList;

    public MusicPlayListWindow(Activity activity) {
        super(activity);
        this.activity = activity;
        musicInfoList = MusicUtil.getCurrentPlayList(activity);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(activity).inflate(R.layout.music_pop_window_play_list, null);
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

        mTitle = view.findViewById(R.id.title);
        mTitle.setText(activity.getString(R.string.music_play_list, musicInfoList.size()));

        mRecyclerView = view.findViewById(R.id.recycler_view);
        adapter = new Adapter(activity, R.layout.music_local_song_item, musicInfoList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    private class Adapter extends BaseQuickAdapter<LocalMusicInfo, BaseViewHolder> {
        private Context mContext;

        public Adapter(Context context, int layoutResId, List<LocalMusicInfo> data) {
            super(layoutResId, data);
            this.mContext = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, LocalMusicInfo info) {
            helper.setText(R.id.local_index, String.valueOf(helper.getLayoutPosition() + 1))
                    .setText(R.id.local_music_name, info.getName())
                    .setText(R.id.local_music_singer, info.getSinger());
            ((SwipeMenuLayout) helper.getView(R.id.swipe_layout)).setSwipeEnable(false);
            helper.setGone(R.id.local_music_item_more_menu, false);
            int currentMusicId = (int) SharedPreferenceUtil.getData(MusicConstants.KEY_ID, -1);
            helper.setGone(R.id.play_icon, currentMusicId == info.getId());

            helper.getView(R.id.local_music_item_ll).setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(info, helper.getLayoutPosition());
                }
                dismiss();
            });

        }
    }


    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onClick(LocalMusicInfo info, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
