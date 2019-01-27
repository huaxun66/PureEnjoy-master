package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;
import com.watson.pureenjoy.music.http.entity.sheet.SheetItem;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

public class MusicSongSheetAdapter extends BaseMultiItemQuickAdapter<SheetItem, BaseViewHolder> {
    private Context mContext;
    private String selectedTag;
    private ImageLoader mImageLoader;

    public MusicSongSheetAdapter(Context context, List<SheetItem> datas) {
        super(datas);
        this.mContext = context;
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();
        addMultiItemType();
    }

    public void setSelectedTag(String selectedTag) {
        this.selectedTag = selectedTag;
    }

    private void addMultiItemType() {
        addItemType(SheetItem.TYPE_HOT_BANNER, R.layout.music_sheet_hot_header_item);
        addItemType(SheetItem.TYPE_HEADER, R.layout.music_sheet_header_item);
        addItemType(SheetItem.TYPE_SHEET_ITEM, R.layout.music_sheet_song_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SheetItem item) {
        switch (helper.getItemViewType()) {
            case SheetItem.TYPE_HOT_BANNER:
                convertHotContent(helper, item);
                break;
            case SheetItem.TYPE_HEADER:
                convertHeaderContent(helper, item);
                break;
            case SheetItem.TYPE_SHEET_ITEM:
                convertSheetContent(helper, item);
                break;
            default:
                break;
        }
    }

    private void convertHotContent(BaseViewHolder helper, SheetItem item) {
        SheetInfo mSheetInfo = item.getSheetInfo();
        helper.setText(R.id.classic_sheet_title, mSheetInfo.getTitle())
              .setText(R.id.classic_sheet_des, mSheetInfo.getDesc());
        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .blurValue(25)
                        .url(mSheetInfo.getPic_w300())
                        .fallback(R.drawable.music_hot_sheet_bg)
                        .errorPic(R.drawable.music_hot_sheet_bg)
                        .imageView(helper.getView(R.id.background))
                        .build());

        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .imageRadius(20)
                        .url(mSheetInfo.getPic_300())
                        .imageView(helper.getView(R.id.img))
                        .build());
        helper.addOnClickListener(R.id.background);
    }

    private void convertHeaderContent(BaseViewHolder helper, SheetItem item) {
        helper.setText(R.id.select_tag, selectedTag);
        helper.getView(R.id.type_chinese).setOnClickListener(v -> {
            helper.setText(R.id.select_tag, mContext.getString(R.string.music_sheet_type_chinese));
            selectedTag = MusicConstants.TAG_CHINESE;
            if (mOnTagClickListener != null) {
                mOnTagClickListener.onTagClick(MusicConstants.TAG_CHINESE);
            }
        });
        helper.getView(R.id.type_rock).setOnClickListener(v -> {
            helper.setText(R.id.select_tag, mContext.getString(R.string.music_sheet_type_rock));
            selectedTag = MusicConstants.TAG_ROCK;
            if (mOnTagClickListener != null) {
                mOnTagClickListener.onTagClick(MusicConstants.TAG_ROCK);
            }
        });
        helper.getView(R.id.type_ballad).setOnClickListener(v -> {
            helper.setText(R.id.select_tag, mContext.getString(R.string.music_sheet_type_ballad));
            selectedTag = MusicConstants.TAG_BALLAD;
            if (mOnTagClickListener != null) {
                mOnTagClickListener.onTagClick(MusicConstants.TAG_BALLAD);
            }
        });
        helper.addOnClickListener(R.id.tag_rl);
    }


    private void convertSheetContent(BaseViewHolder helper, SheetItem item) {
        SheetInfo mSheetInfo = item.getSheetInfo();
        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .url(mSheetInfo.getPic_300())
                        .imageView(helper.getView(R.id.img))
                        .build());
        int count = Integer.parseInt(mSheetInfo.getListenum());
        String countText = count > 10000 ? count / 10000 + "万" : count + "";
        helper.setText(R.id.tv_title, mSheetInfo.getTitle())
              .setText(R.id.count, countText);
    }


    private OnTagClickListener mOnTagClickListener;
    public interface OnTagClickListener {
        void onTagClick(String tag);
    }

    public void setOnTagClickListener(OnTagClickListener mOnTagClickListener) {
        this.mOnTagClickListener = mOnTagClickListener;
    }

}
