package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;
import com.watson.pureenjoy.music.http.entity.sheet.SheetItem;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

public class MusicSongSheetAdapter extends BaseMultiItemQuickAdapter<SheetItem, BaseViewHolder> {
    private Context mContext;
    private ImageLoader mImageLoader;

    public MusicSongSheetAdapter(Context context, List<SheetItem> datas) {
        super(datas);
        this.mContext = context;
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();
        addMultiItemType();
    }

    private void addMultiItemType() {
        addItemType(SheetItem.TYPE_HOT_BANNER, R.layout.music_sheet_hot_item);
        addItemType(SheetItem.TYPE_HEADER, R.layout.music_sheet_header_item);
        addItemType(SheetItem.TYPE_SHEET_ITEM, R.layout.music_sheet_song_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SheetItem item) {
        switch (helper.getItemViewType()) {
            case SheetItem.TYPE_HOT_BANNER:
                convertHotContent(helper, item);
                break;
            case SheetItem.TYPE_SHEET_ITEM:
                convertSheetContent(helper, item);
                break;
            default:
                break;
        }
    }

    private void convertHotContent(BaseViewHolder helper, SheetItem item) {
        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .imageRadius(20)
                        .url(item.getImgUrl())
                        .imageView(helper.getView(R.id.img))
                        .build());
    }


    private void convertSheetContent(BaseViewHolder helper, SheetItem item) {
        SheetInfo mSheetInfo = item.getmSheetInfo();
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


}
