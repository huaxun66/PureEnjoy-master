package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.http.entity.local.LocalSheetInfo;

import java.util.List;

public class MusicCreatedSheetAdapter extends BaseQuickAdapter<LocalSheetInfo, BaseViewHolder> {
    private Context mContext;
    private List<LocalSheetInfo> data;

    public MusicCreatedSheetAdapter(Context context, int layoutResId, List<LocalSheetInfo> data) {
        super(layoutResId, data);
        this.data = data;
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalSheetInfo info) {
        helper.setText(R.id.sheet_name_tv, info.getName())
                .setText(R.id.sheet_music_count_tv, mContext.getString(R.string.music_sheet_song_num, info.getCount()));
        helper.getView(R.id.swipe_delete_menu_btn).setOnClickListener(v -> {
            DBManager.getInstance(mContext).deleteSheet(String.valueOf(info.getId()));
            data.remove(info);
            notifyDataSetChanged();
        });
    }
}
