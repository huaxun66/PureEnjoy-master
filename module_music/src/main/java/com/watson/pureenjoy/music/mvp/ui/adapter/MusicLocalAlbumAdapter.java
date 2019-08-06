package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.local.LocalAlbumInfo;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

public class MusicLocalAlbumAdapter extends BaseQuickAdapter<LocalAlbumInfo, BaseViewHolder> {
    private Context mContext;


    public MusicLocalAlbumAdapter(Context context, int layoutResId, List<LocalAlbumInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalAlbumInfo info) {
        helper.setText(R.id.album_name, info.getName())
                .setText(R.id.album_song_num, mContext.getString(R.string.music_local_song_num, info.getCount()))
                .setText(R.id.album_singer_name, info.getSinger());
        if (!StringUtil.isEmpty(info.getThumbs())) {
            Bitmap bm = BitmapFactory.decodeFile(info.getThumbs());
            helper.setImageBitmap(R.id.album_img, bm);
        }
    }

}
