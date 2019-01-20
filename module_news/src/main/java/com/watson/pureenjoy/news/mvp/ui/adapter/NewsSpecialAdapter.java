package com.watson.pureenjoy.news.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.labelview.LabelView;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.http.entity.NewsSpecial;
import com.watson.pureenjoy.news.http.entity.NewsSpecialItem;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

public class NewsSpecialAdapter extends BaseMultiItemQuickAdapter<NewsSpecialItem, BaseViewHolder> {

    private Context context;
    private List<NewsSpecialItem> datas;
    private ImageLoader mImageLoader;

    public NewsSpecialAdapter(Context context, List<NewsSpecialItem> datas) {
        super(datas);
        this.context = context;
        this.datas = datas;
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();
        addMultiItemType();
    }

    private void addMultiItemType() {
        addItemType(NewsSpecialItem.TYPE_HEADER, R.layout.news_list_header_item);
        addItemType(NewsSpecialItem.TYPE_NORMAL,R.layout.news_list_normal_item);
        addItemType(NewsSpecialItem.TYPE_PHOTO_SET, R.layout.news_list_photo_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsSpecialItem item) {
        switch (helper.getItemViewType()) {
            case NewsSpecialItem.TYPE_HEADER:
                convertHeaderContent(helper, item);
                break;
            case NewsSpecialItem.TYPE_NORMAL:
                convertNormalContent(helper, item);
                break;
            case NewsSpecialItem.TYPE_PHOTO_SET:
                convertPhotoContent(helper, item);
                break;
            default:
                break;
        }
    }

    private void convertHeaderContent(BaseViewHolder helper, NewsSpecialItem item) {
        helper.setText(R.id.tv_item_news_special_title, item.getTitle());
    }

    private void convertNormalContent(BaseViewHolder helper, NewsSpecialItem item) {
        NewsSpecial.TopicsEntity.DocsEntity entity = item.getEntity();
        helper.setText(R.id.tv_item_news_list_title, entity.getTitle())
                .setText(R.id.tv_item_news_list_time, StringUtil.getFormatDateString(context, entity.getLmodify()))
                .setText(R.id.tv_item_news_list_from, entity.getSource());
        mImageLoader.loadImage(context,
                CommonImageConfigImpl
                        .builder()
                        .errorPic(R.drawable.news_image_bg)
                        .fallback(R.drawable.news_image_bg)
                        .url(entity.getImgsrc())
                        .imageView(helper.getView(R.id.iv_item_news_list_display))
                        .build());
        helper.setVisible(R.id.lv_item_news_list_label,false);
        if (entity.getVideoinfo()!=null) {
            helper.setVisible(R.id.lv_item_news_list_label,true);
            ((LabelView)helper.getView(R.id.lv_item_news_list_label)).setText(ArmsUtils.getString(context, R.string.news_video));
        }
    }

    private void convertPhotoContent(BaseViewHolder helper, NewsSpecialItem item) {
        NewsSpecial.TopicsEntity.DocsEntity entity = item.getEntity();
        helper.setText(R.id.tv_item_news_list_title, entity.getTitle())
                .setText(R.id.tv_item_news_list_time, StringUtil.getFormatDateString(context, entity.getLmodify()));
        if (entity.getImgextra() != null) {
            if (entity.getImgextra().size() == 1) {
                mImageLoader.loadImage(context,
                        CommonImageConfigImpl
                                .builder()
                                .url(entity.getImgsrc())
                                .imageView(helper.getView(R.id.iv_item_news_list_photo_one))
                                .build());
                mImageLoader.loadImage(context,
                        CommonImageConfigImpl
                                .builder()
                                .url(entity.getImgextra().get(0).getImgsrc())
                                .imageView(helper.getView(R.id.iv_item_news_list_photo_two))
                                .build());
                mImageLoader.loadImage(context,
                        CommonImageConfigImpl
                                .builder()
                                .url(entity.getImgsrc())
                                .imageView(helper.getView(R.id.iv_item_news_list_photo_three))
                                .build());
            } else if (entity.getImgextra().size() == 2) {
                mImageLoader.loadImage(context,
                        CommonImageConfigImpl
                                .builder()
                                .url(entity.getImgextra().get(0).getImgsrc())
                                .imageView(helper.getView(R.id.iv_item_news_list_photo_one))
                                .build());
                mImageLoader.loadImage(context,
                        CommonImageConfigImpl
                                .builder()
                                .url(entity.getImgextra().get(1).getImgsrc())
                                .imageView(helper.getView(R.id.iv_item_news_list_photo_two))
                                .build());
                mImageLoader.loadImage(context,
                        CommonImageConfigImpl
                                .builder()
                                .url(entity.getImgsrc())
                                .imageView(helper.getView(R.id.iv_item_news_list_photo_three))
                                .build());
            } else if (entity.getImgextra().size() >= 3) {
                mImageLoader.loadImage(context,
                        CommonImageConfigImpl
                                .builder()
                                .url(entity.getImgextra().get(0).getImgsrc())
                                .imageView(helper.getView(R.id.iv_item_news_list_photo_one))
                                .build());
                mImageLoader.loadImage(context,
                        CommonImageConfigImpl
                                .builder()
                                .url(entity.getImgextra().get(1).getImgsrc())
                                .imageView(helper.getView(R.id.iv_item_news_list_photo_two))
                                .build());
                mImageLoader.loadImage(context,
                        CommonImageConfigImpl
                                .builder()
                                .url(entity.getImgextra().get(2).getImgsrc())
                                .imageView(helper.getView(R.id.iv_item_news_list_photo_three))
                                .build());
            }
        } else {
            mImageLoader.loadImage(context,
                    CommonImageConfigImpl
                            .builder()
                            .url(entity.getImgsrc())
                            .imageView(helper.getView(R.id.iv_item_news_list_photo_one))
                            .build());
            mImageLoader.loadImage(context,
                    CommonImageConfigImpl
                            .builder()
                            .url(entity.getImgsrc())
                            .imageView(helper.getView(R.id.iv_item_news_list_photo_two))
                            .build());
            mImageLoader.loadImage(context,
                    CommonImageConfigImpl
                            .builder()
                            .url(entity.getImgsrc())
                            .imageView(helper.getView(R.id.iv_item_news_list_photo_three))
                            .build());
        }
    }

    public int getPositionFromTitle(String title) {
        int size = getData().size();
        int result = -1;
        for (int i = 0; i < size; i++) {
            NewsSpecialItem item = getData().get(i);
            if (item.getItemType() == NewsSpecialItem.TYPE_HEADER && item.getTitle().equals(title)) {
                result = i;
            }
        }
        return result;
    }


}

