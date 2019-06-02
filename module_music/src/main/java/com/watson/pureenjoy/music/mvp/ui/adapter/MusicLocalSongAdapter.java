package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;
import android.widget.SectionIndexer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;

import java.util.List;

public class MusicLocalSongAdapter extends BaseQuickAdapter<LocalMusicInfo, BaseViewHolder> implements SectionIndexer {
    private Context mContext;
    private List<LocalMusicInfo> musicInfoList;


    public MusicLocalSongAdapter(Context context, int layoutResId, List<LocalMusicInfo> data) {
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

        int section = getSectionForPosition(position);
        int firstPosition = getPositionForSection(section);
        if (firstPosition == position){
            helper.setVisible(R.id.section_header, true);
            helper.setText(R.id.section_header, info.getFirstLetter());
        } else {
            helper.setGone(R.id.section_header, false);
        }

        helper.getView(R.id.local_music_item_ll).setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onContentClick(position);
        });

        helper.getView(R.id.local_music_item_more_menu).setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onMoreMenuClick(position);
        });

        helper.getView(R.id.swipe_delete_menu_btn).setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onDeleteMenuClick(position);
        });
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    /**
     * 根据RecycleView的当前位置获取分类的首字母的char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return musicInfoList.get(position).getFirstLetter().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的item的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            char firstChar = musicInfoList.get(i).getFirstLetter().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    public void updateMusicInfoList(List<LocalMusicInfo> musicInfoList) {
        musicInfoList.clear();
        musicInfoList.addAll(musicInfoList);
        setNewData(musicInfoList);
    }

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onMoreMenuClick(int position);
        void onDeleteMenuClick(int position);
        void onContentClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener ;
    }

}
