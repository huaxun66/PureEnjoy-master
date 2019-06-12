package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.local.LocalFolderInfo;

import java.util.List;

public class MusicLocalFolderAdapter extends BaseQuickAdapter<LocalFolderInfo, BaseViewHolder> {
    private Context mContext;


    public MusicLocalFolderAdapter(Context context, int layoutResId, List<LocalFolderInfo> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalFolderInfo info) {
        helper.setText(R.id.folder_name, info.getName())
                .setText(R.id.folder_path, info.getPath());
    }


}
