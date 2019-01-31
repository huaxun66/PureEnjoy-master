package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.album.AlbumInfo;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

public class MusicAlbumAdapter extends BaseQuickAdapter<AlbumInfo, BaseViewHolder> {
    private Context mContext;
    private ImageLoader mImageLoader;

    public MusicAlbumAdapter(Context context, int layoutResId, List<AlbumInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumInfo info) {
        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .url(info.getPic_radio())
                        .imageView(helper.getView(R.id.img))
                        .build());
        helper.setText(R.id.album_name, info.getTitle())
                .setText(R.id.artist_name, info.getAuthor());
    }

}
