package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.http.entity.rank.RankInfo;
import com.watson.pureenjoy.music.http.entity.rank.RankSongInfo;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

public class MusicRankAdapter extends BaseQuickAdapter<RankInfo, BaseViewHolder> {
    private Context mContext;
    private ImageLoader mImageLoader;

    public MusicRankAdapter(Context context, int layoutResId, List<RankInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, RankInfo info) {
        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .url(info.getPic_s192())
                        .imageRadius(20)
                        .imageView(helper.getView(R.id.img))
                        .build());
        RankSongInfo song1 = info.getContent().get(0);
        RankSongInfo song2 = info.getContent().get(1);
        RankSongInfo song3 = info.getContent().get(2);
        if (song1 != null) {
            helper.setText(R.id.tv_no1, "1." + song1.getTitle() + " - " + song1.getAuthor());
        }
        if (song2 != null) {
            helper.setText(R.id.tv_no2, "2." + song2.getTitle() + " - " + song2.getAuthor());
        }
        if (song3 != null) {
            helper.setText(R.id.tv_no3, "3." + song3.getTitle() + " - " + song3.getAuthor());
        }
        if (!StringUtil.isEmpty(info.getComment())) {
            if (info.getComment().contains(MusicConstants.RANK_UPDATE_REALTIME)) {
                helper.setText(R.id.update, MusicConstants.RANK_UPDATE_REALTIME);
            } else if (info.getComment().contains(MusicConstants.RANK_UPDATE_EVERYDAY)) {
                helper.setText(R.id.update, MusicConstants.RANK_UPDATE_EVERYDAY);
            }
        }
    }


}

