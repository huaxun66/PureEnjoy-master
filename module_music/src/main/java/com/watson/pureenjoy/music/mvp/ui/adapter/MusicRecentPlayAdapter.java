package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.utils.SharedPreferenceUtil;

public class MusicRecentPlayAdapter extends BaseQuickAdapter<LocalMusicInfo, BaseViewHolder> {
    private Context mContext;
    private List<LocalMusicInfo> musicInfoList;


    public MusicRecentPlayAdapter(Context context, int layoutResId, List<LocalMusicInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
        this.musicInfoList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalMusicInfo info) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.local_index, String.valueOf(position + 1))
                .setText(R.id.local_music_name, info.getName())
                .setText(R.id.local_music_singer, info.getSinger());

        int currentMusicId = (int) SharedPreferenceUtil.getData(MusicConstants.KEY_ID, -1);
        helper.setGone(R.id.play_icon, currentMusicId == info.getId());

        helper.getView(R.id.local_music_item_ll).setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onContentClick(position);
        });

        helper.getView(R.id.swipe_delete_menu_btn).setOnClickListener(v -> {
            ((SwipeMenuLayout) (helper.getView(R.id.swipe_layout))).smoothClose();
            if (onItemClickListener != null)
                onItemClickListener.onDeleteMenuClick(position);
        });
    }

    public void updateMusicInfoList(List<LocalMusicInfo> musicInfoList) {
        this.musicInfoList.clear();
        this.musicInfoList.addAll(musicInfoList);
        setNewData(this.musicInfoList);
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDeleteMenuClick(int position);
        void onContentClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
