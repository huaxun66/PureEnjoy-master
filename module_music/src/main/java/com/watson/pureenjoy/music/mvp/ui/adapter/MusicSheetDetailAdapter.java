package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.sheet.SheetDetailInfo;

import java.util.List;

public class MusicSheetDetailAdapter extends BaseQuickAdapter<SheetDetailInfo, BaseViewHolder> {
    private Context mContext;

    public MusicSheetDetailAdapter(Context context, int layoutResId, List<SheetDetailInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SheetDetailInfo info) {
        helper.setText(R.id.index, String.valueOf(helper.getLayoutPosition() + 1))
                .setText(R.id.name, info.getTitle())
                .setText(R.id.author, info.getAuthor() + " - " + info.getAlbum_title());
    }

}
