package com.watson.pureenjoy.news.mvp.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.app.NewsConstants;
import com.watson.pureenjoy.news.http.entity.NewsItem;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.watson.pureenjoy.news.app.NewsConstants.PHOTO_SET_ID;

public class NewsListAdapter extends BaseMultiItemQuickAdapter<NewsItem, BaseViewHolder> {

    private Context context;
    private List<NewsItem> datas;

    public NewsListAdapter(Context context, List<NewsItem> datas) {
        super(datas);
        this.context = context;
        this.datas = datas;
        addMultiItemType();
    }

    private void addMultiItemType() {
        addItemType(NewsItem.TYPE_PHOTO, R.layout.news_list_photo_item);
        addItemType(NewsItem.TYPE_NORMAL,R.layout.news_list_normal_item);
        addItemType(NewsItem.TYPE_BANNER, R.layout.news_list_banner_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsItem item) {
        switch (helper.getItemViewType()) {
            case NewsItem.TYPE_NORMAL:
                convertNormalContent(helper, item);
                break;
            case NewsItem.TYPE_PHOTO:
                convertPhotoContent(helper, item);
                break;
            case NewsItem.TYPE_BANNER:
                convertBannerContent(helper, item);
                break;
            default:
                break;
        }
    }

    private void convertNormalContent(BaseViewHolder helper, NewsItem item) {
        helper.setText(R.id.tv_item_news_list_title, item.getTitle())
                .setText(R.id.tv_item_news_list_time, StringUtil.getFormatDateString(context, item.getMtime()))
                .setText(R.id.tv_item_news_list_from, item.getSource());
        RequestOptions options = new RequestOptions().fallback(R.drawable.news_image_bg).error(R.drawable.news_image_bg);
        Glide.with(context)
                .load(item.getImgsrc())
                .apply(options)
                .into((ImageView) helper.getView(R.id.iv_item_news_list_display));
        helper.setVisible(R.id.lv_item_news_list_label,false);
        if (NewsConstants.SPECIAL.equals(item.getSkipType())) {
            helper.setVisible(R.id.lv_item_news_list_label,true);
        }
    }

    private void convertPhotoContent(BaseViewHolder helper, NewsItem item) {
        helper.setText(R.id.tv_item_news_list_title, item.getTitle())
                .setText(R.id.tv_item_news_list_time, StringUtil.getFormatDateString(context, item.getMtime()));
        if (item.getImgextra() != null) {
            if (item.getImgextra().size() == 1) {
                Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
                Glide.with(context).load(item.getImgextra().get(0).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
                Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
            } else if (item.getImgextra().size() == 2) {
                Glide.with(context).load(item.getImgextra().get(0).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
                Glide.with(context).load(item.getImgextra().get(1).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
                Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
            } else if (item.getImgextra().size() >= 3) {
                Glide.with(context).load(item.getImgextra().get(0).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
                Glide.with(context).load(item.getImgextra().get(1).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
                Glide.with(context).load(item.getImgextra().get(2).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
            }
        } else {
            Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
            Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
            Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
        }
    }

    private SliderLayout mSlider;
    private void convertBannerContent(BaseViewHolder helper, NewsItem item) {
        SliderLayout mSlider = helper.getView(R.id.slider);
        RelativeLayout singleLayout = helper.getView(R.id.single_layout);
        this.mSlider = mSlider;
        if (item.getAds().size() > 1) {
            mSlider.setVisibility(VISIBLE);
            singleLayout.setVisibility(GONE);
            mSlider.removeAllSliders(); //remove
            for(NewsItem.AdsEntity entity : item.getAds()) {
                TextSliderView textSliderView = new TextSliderView(context);
                // initialize a SliderLayout
                textSliderView
                        .description(entity.getTitle())
                        .image(entity.getImgsrc())
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(slider -> ARouter.getInstance().build(RouterHub.NEWS_PHOTO_SET_ACTIVITY)
                                .withString(PHOTO_SET_ID, (String)slider.getBundle().get(PHOTO_SET_ID))
                                .navigation());
                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString(PHOTO_SET_ID,entity.getSkipID());
                mSlider.addSlider(textSliderView);
            }
            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion); //设置过渡动画
            mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom); //Indicator位置
            mSlider.setCustomAnimation(new DescriptionAnimation()); //底部文字展现的动画
            mSlider.setDuration(5000);
        } else if (item.getAds().size() == 1){
            mSlider.setVisibility(GONE);
            singleLayout.setVisibility(VISIBLE);
            NewsItem.AdsEntity entity = item.getAds().get(0);
            ((TextView)helper.getView(R.id.title)).setText(entity.getTitle());
            Glide.with(context).load(entity.getImgsrc()).into((ImageView) helper.getView(R.id.img));
            singleLayout.setOnClickListener(v -> ARouter.getInstance().build(RouterHub.NEWS_PHOTO_SET_ACTIVITY)
                    .withString(PHOTO_SET_ID, entity.getSkipID())
                    .navigation());
        }
    }


    public void onDetach() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        if (mSlider != null) {
            mSlider.stopAutoCycle();
        }
    }


}

