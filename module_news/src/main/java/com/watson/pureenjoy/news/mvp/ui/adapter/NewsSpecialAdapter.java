package com.watson.pureenjoy.news.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.labelview.LabelView;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.app.NewsConstants;
import com.watson.pureenjoy.news.http.entity.NewsSpecial;
import com.watson.pureenjoy.news.http.entity.NewsSpecialItem;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

public class NewsSpecialAdapter extends BaseMultiItemQuickAdapter<NewsSpecialItem, BaseViewHolder> {

    private Context context;
    private List<NewsSpecialItem> datas;

    public NewsSpecialAdapter(Context context, List<NewsSpecialItem> datas) {
        super(datas);
        this.context = context;
        this.datas = datas;
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
//            case NewsSpecialItem.TYPE_VIDEO:
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
        RequestOptions options = new RequestOptions().fallback(R.drawable.news_image_bg).error(R.drawable.news_image_bg);
        Glide.with(context)
                .load(entity.getImgsrc())
                .apply(options)
                .into((ImageView) helper.getView(R.id.iv_item_news_list_display));
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
                Glide.with(context).load(entity.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
                Glide.with(context).load(entity.getImgextra().get(0).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
                Glide.with(context).load(entity.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
            } else if (entity.getImgextra().size() == 2) {
                Glide.with(context).load(entity.getImgextra().get(0).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
                Glide.with(context).load(entity.getImgextra().get(1).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
                Glide.with(context).load(entity.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
            } else if (entity.getImgextra().size() >= 3) {
                Glide.with(context).load(entity.getImgextra().get(0).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
                Glide.with(context).load(entity.getImgextra().get(1).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
                Glide.with(context).load(entity.getImgextra().get(2).getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
            }
        } else {
            Glide.with(context).load(entity.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
            Glide.with(context).load(entity.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
            Glide.with(context).load(entity.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
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

