package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.local.LocalSingerInfo;

import java.util.List;

public class MusicLocalSingerAdapter extends BaseQuickAdapter<LocalSingerInfo, BaseViewHolder> {
    private Context mContext;


    public MusicLocalSingerAdapter(Context context, int layoutResId, List<LocalSingerInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalSingerInfo info) {
        helper.setText(R.id.singer_name, info.getName())
                .setText(R.id.singer_song_num, mContext.getString(R.string.music_local_song_num, info.getCount()));
    }


}
