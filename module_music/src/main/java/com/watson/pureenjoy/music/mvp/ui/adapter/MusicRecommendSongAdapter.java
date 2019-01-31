package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.recommendSong.RecommendSongInfo;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

public class MusicRecommendSongAdapter extends BaseQuickAdapter<RecommendSongInfo, BaseViewHolder> {
    private Context mContext;
    private ImageLoader mImageLoader;


    public MusicRecommendSongAdapter(Context context, int layoutResId, List<RecommendSongInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendSongInfo info) {
        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .url(info.getPic_small())
                        .imageRadius(5)
                        .imageView(helper.getView(R.id.img))
                        .build());
        helper.setText(R.id.name, info.getTitle())
              .setText(R.id.author, info.getAuthor() + " - " + info.getAlbum_title());
        helper.setGone(R.id.play, info.getHas_mv_mobile() != 0);
    }

}
