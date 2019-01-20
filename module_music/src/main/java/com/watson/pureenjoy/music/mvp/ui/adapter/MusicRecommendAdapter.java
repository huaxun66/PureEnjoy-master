package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.viewpagerindicator.CirclePageIndicator;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.RecommendItem;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendAlbumInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendFocus;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendFocusInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendListInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendRadioInfo;

import java.util.ArrayList;
import java.util.Calendar;

import me.jessyan.armscomponent.commonres.adapter.ImageViewPagerAdapter;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

public class MusicRecommendAdapter extends BaseMultiItemQuickAdapter<RecommendItem, BaseViewHolder> {
    private Context mContext;
    private ImageLoader mImageLoader;

    public MusicRecommendAdapter(Context context, ArrayList<RecommendItem> datas) {
        super(datas);
        this.mContext = context;
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();
        addMultiItemType();
    }

    private void addMultiItemType() {
        addItemType(RecommendItem.TYPE_BANNER, R.layout.music_recommend_banner_item);
        addItemType(RecommendItem.TYPE_HEADER, R.layout.music_recommend_header_item);
        addItemType(RecommendItem.TYPE_RECOMMEND_SONG, R.layout.music_recommend_song_item);
        addItemType(RecommendItem.TYPE_NEW_ALBUM, R.layout.music_new_album_item);
        addItemType(RecommendItem.TYPE_ANCHOR_RADIO, R.layout.music_recommend_radio_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendItem item) {
        switch (helper.getItemViewType()) {
            case RecommendItem.TYPE_BANNER:
                convertBannerContent(helper, item);
                break;
            case RecommendItem.TYPE_HEADER:
                convertHeaderContent(helper, item);
                break;
            case RecommendItem.TYPE_RECOMMEND_SONG:
                convertRecommendListContent(helper, item);
                break;
            case RecommendItem.TYPE_NEW_ALBUM:
                convertRecommendAlbumContent(helper, item);
                break;
            case RecommendItem.TYPE_ANCHOR_RADIO:
                convertRecommendRadioContent(helper, item);
                break;
            default:
                break;
        }
    }

    private void convertBannerContent(BaseViewHolder helper, RecommendItem item) {
        RecommendFocus mRecommendFocus = item.getRecommendFocus();
        ViewPager mViewPager = helper.getView(R.id.view_pager);
        CirclePageIndicator mIndicator = helper.getView(R.id.indicator);
        ImageViewPagerAdapter mBannerAdapter = new ImageViewPagerAdapter(mContext);
        mViewPager.setAdapter(mBannerAdapter);
        mIndicator.setViewPager(mViewPager);
        //轮播图数据
        ArrayList<String> urls = new ArrayList<>();
        for (RecommendFocusInfo info : mRecommendFocus.getResult()) {
            urls.add(info.getRandpic());
        }
        mBannerAdapter.setData(urls);
        //日期
        int date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        helper.setText(R.id.tv_recommend, String.valueOf(date));
        helper.addOnClickListener(R.id.iv_fm);
        helper.addOnClickListener(R.id.tv_recommend);
        helper.addOnClickListener(R.id.iv_songSheet);
        helper.addOnClickListener(R.id.iv_rank);
    }

    private void convertHeaderContent(BaseViewHolder helper, RecommendItem item) {
        helper.setText(R.id.tv_title, item.getTitle());
    }

    private void convertRecommendListContent(BaseViewHolder helper, RecommendItem item) {
        RecommendListInfo mRecommendListInfo = item.getRecommendListInfo();
        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .url(mRecommendListInfo.getPic())
                        .imageView(helper.getView(R.id.img))
                        .build());
        int count = Integer.parseInt(mRecommendListInfo.getListenum());
        String countText = count > 10000 ? count / 10000 + "万" : count + "";
        helper.setText(R.id.tv_title, mRecommendListInfo.getTitle())
              .setText(R.id.count, countText);
    }

    private void convertRecommendAlbumContent(BaseViewHolder helper, RecommendItem item) {
        RecommendAlbumInfo mRecommendAlbumInfo = item.getRecommendAlbumInfo();
        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .url(mRecommendAlbumInfo.getPic())
                        .imageView(helper.getView(R.id.img))
                        .build());
        helper.setText(R.id.album_name, mRecommendAlbumInfo.getTitle())
              .setText(R.id.artist_name, mRecommendAlbumInfo.getAuthor());
    }

    private void convertRecommendRadioContent(BaseViewHolder helper, RecommendItem item) {
        RecommendRadioInfo mRecommendRadioInfo = item.getRecommendRadioInfo();
        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .url(mRecommendRadioInfo.getPic())
                        .imageView(helper.getView(R.id.img))
                        .build());
        helper.setText(R.id.tv_des, mRecommendRadioInfo.getDesc())
              .setText(R.id.tv_title, mRecommendRadioInfo.getTitle());
    }

}
