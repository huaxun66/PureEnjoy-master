package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.album.AlbumSongInfo;

import java.util.List;

public class MusicAlbumDetailAdapter extends BaseQuickAdapter<AlbumSongInfo, BaseViewHolder> {
    private Context mContext;

    public MusicAlbumDetailAdapter(Context context, int layoutResId, List<AlbumSongInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumSongInfo info) {
        helper.setText(R.id.index, String.valueOf(helper.getLayoutPosition() + 1))
                .setText(R.id.name, info.getTitle())
                .setText(R.id.author, info.getAuthor() + " - " + info.getAlbum_title());
        helper.setGone(R.id.play, info.getHas_mv_mobile() != 0);
    }

}
