package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.integration.EventBusManager;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.SheetRefreshEvent;
import com.watson.pureenjoy.music.http.entity.local.LocalSheetInfo;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.KEY_TITLE;
import static com.watson.pureenjoy.music.app.MusicConstants.KEY_TYPE;
import static com.watson.pureenjoy.music.app.MusicConstants.LIST_CREATE_SHEET;

public class MusicCreatedSheetAdapter extends BaseQuickAdapter<LocalSheetInfo, BaseViewHolder> {
    private Context mContext;

    public MusicCreatedSheetAdapter(Context context, int layoutResId, List<LocalSheetInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalSheetInfo info) {
        helper.setText(R.id.sheet_name_tv, info.getName())
                .setText(R.id.sheet_music_count_tv, mContext.getString(R.string.music_sheet_song_num, info.getCount()));
        helper.getView(R.id.sheet_swipe_delete_menu_btn).setOnClickListener(v -> {
            DBManager.getInstance(mContext).deleteSheet(String.valueOf(info.getId()));
            EventBusManager.getInstance().post(new SheetRefreshEvent());
        });
        helper.getView(R.id.sheet_content_ll).setOnClickListener(v -> ARouter.getInstance().build(RouterHub.MUSIC_LOCAL_SONG_LIST_ACTIVITY)
                .withInt(KEY_TYPE, LIST_CREATE_SHEET)
                .withString(KEY_TITLE, String.valueOf(info.getId()))
                .navigation());
    }
}
