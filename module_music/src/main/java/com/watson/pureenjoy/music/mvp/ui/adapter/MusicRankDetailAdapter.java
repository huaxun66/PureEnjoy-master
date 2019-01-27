package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.rank.RankSongInfo;

import java.util.List;

public class MusicRankDetailAdapter extends BaseQuickAdapter<RankSongInfo, BaseViewHolder> {
    private Context mContext;

    public MusicRankDetailAdapter(Context context, int layoutResId, List<RankSongInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RankSongInfo info) {
        int position = helper.getLayoutPosition() + 1;
        String positionString = position < 10 ? "0"+position : ""+position;
        helper.setText(R.id.index, positionString)
                .setText(R.id.rank, info.getRank_change())
                .setText(R.id.name, info.getTitle())
                .setText(R.id.author, info.getAuthor() + " - " + info.getAlbum_title());
        if (position == 1 || position == 2 || position == 3) {
            helper.setTextColor(R.id.index, mContext.getResources().getColor(R.color.music_theme));
        } else {
            helper.setTextColor(R.id.index, mContext.getResources().getColor(R.color.public_custom_color_text_sub));
        }
    }

}
