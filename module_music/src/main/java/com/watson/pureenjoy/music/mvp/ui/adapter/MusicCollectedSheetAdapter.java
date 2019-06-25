package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.SheetRefreshEvent;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

import static com.watson.pureenjoy.music.app.MusicConstants.SHEET_INFO;

public class MusicCollectedSheetAdapter extends BaseQuickAdapter<SheetInfo, BaseViewHolder> {
    private Context mContext;
    private ImageLoader mImageLoader;

    public MusicCollectedSheetAdapter(Context context, int layoutResId, List<SheetInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
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
        helper.getView(R.id.sheet_swipe_delete_menu_btn).setOnClickListener(v -> {
            DBManager.getInstance(mContext).deleteSheet(String.valueOf(info.getId()));
            EventBusManager.getInstance().post(new SheetRefreshEvent());
        });
        helper.getView(R.id.sheet_content_ll).setOnClickListener(v -> ARouter.getInstance().build(RouterHub.MUSIC_SHEET_DETAIL_ACTIVITY)
                .withParcelable(SHEET_INFO, info)
                .navigation());
    }
}
