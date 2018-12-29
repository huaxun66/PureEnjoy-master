package com.watson.pureenjoy.news.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.app.NewsConstants;
import com.watson.pureenjoy.news.http.entity.NewsItem;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

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
        addItemType(NewsItem.TYPE_NORMAL,R.layout.news_list_normal_item);
        addItemType(NewsItem.TYPE_PHOTO, R.layout.news_list_photo_item);
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
            default:
                break;
        }
    }

    private void convertNormalContent(BaseViewHolder helper, NewsItem item) {
        helper.setText(R.id.tv_item_news_list_title, item.getTitle())
                .setText(R.id.tv_item_news_list_time, StringUtil.getFormatDateString(context, item.getMtime()))
                .setText(R.id.tv_item_news_list_from, item.getSource());
        Glide.with(context)
                .load(item.getImgsrc())
                .into((ImageView) helper.getView(R.id.iv_item_news_list_display));
        helper.setVisible(R.id.lv_item_news_list_label,false);
        if (NewsConstants.SPECIAL_TITLE.equals(item.getSkipType())) {
            helper.setVisible(R.id.lv_item_news_list_label,true);
        }
    }

    private void convertPhotoContent(BaseViewHolder helper, NewsItem item) {
        helper.setText(R.id.tv_item_news_list_title, item.getTitle())
                .setText(R.id.tv_item_news_list_time, StringUtil.getFormatDateString(context, item.getMtime()));
        if (item.getImgextra() != null) {
            if (item.getImgextra().size() == 1) {
                Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
                Glide.with(context).load(item.getImgextra().get(0)).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
                Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
            } else if (item.getImgextra().size() == 2) {
                Glide.with(context).load(item.getImgextra().get(0)).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
                Glide.with(context).load(item.getImgextra().get(1)).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
                Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
            } else if (item.getImgextra().size() >= 3) {
                Glide.with(context).load(item.getImgextra().get(0)).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
                Glide.with(context).load(item.getImgextra().get(1)).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
                Glide.with(context).load(item.getImgextra().get(2)).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
            }
        } else {
            Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_one));
            Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_two));
            Glide.with(context).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.iv_item_news_list_photo_three));
        }
    }


}

