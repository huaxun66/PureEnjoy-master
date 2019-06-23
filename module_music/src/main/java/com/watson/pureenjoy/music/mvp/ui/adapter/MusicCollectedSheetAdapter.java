package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

public class MusicCollectedSheetAdapter extends BaseQuickAdapter<SheetInfo, BaseViewHolder> {
    private Context mContext;
    private ImageLoader mImageLoader;
    private List<SheetInfo> data;

    public MusicCollectedSheetAdapter(Context context, int layoutResId, List<SheetInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
        this.data = data;
        this.mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, SheetInfo info) {
        helper.setText(R.id.sheet_name_tv, info.getTitle())
                .setText(R.id.sheet_music_count_tv, mContext.getString(R.string.music_sheet_song_num, Integer.parseInt(info.getSongCount())));
        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .url(info.getPic())
                        .fallback(R.drawable.music_my_sheet)
                        .errorPic(R.drawable.music_my_sheet)
                        .imageRadius(20)
                        .imageView(helper.getView(R.id.sheet_cover_iv))
                        .build());
        helper.getView(R.id.swipe_delete_menu_btn).setOnClickListener(v -> {
            DBManager.getInstance(mContext).deleteSheet(String.valueOf(info.getId()));
            data.remove(info);
            notifyDataSetChanged();
        });
    }
}
