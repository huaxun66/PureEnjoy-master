package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.http.entity.sheet.SheetTagCategoryItem;
import com.watson.pureenjoy.music.http.entity.sheet.SheetTagInfo;
import com.watson.pureenjoy.music.http.entity.sheet.SheetTagItem;
import com.watson.pureenjoy.music.http.entity.sheet.TagInfo;

import java.util.ArrayList;
import java.util.List;

public class MusicSheetTagAdapter extends BaseMultiItemQuickAdapter<SheetTagItem, BaseViewHolder> {
    private Context mContext;
    private String selectedTag;

    public MusicSheetTagAdapter(Context context, List<SheetTagItem> datas) {
        super(datas);
        this.mContext = context;
        addMultiItemType();
    }

    public void setSelectedTag(String selectedTag) {
        this.selectedTag = selectedTag;
    }

    private void addMultiItemType() {
        addItemType(SheetTagItem.TYPE_TAG_ALL, R.layout.music_tag_all_item);
        addItemType(SheetTagItem.TYPE_TAG_CATEGORY, R.layout.music_tag_other_item);
        addItemType(SheetTagItem.TYPE_TAG_SPLIT, R.layout.music_split_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SheetTagItem item) {
        switch (helper.getItemViewType()) {
            case SheetTagItem.TYPE_TAG_ALL:
                convertTagAllContent(helper, item);
                break;
            case SheetTagItem.TYPE_TAG_CATEGORY:
                convertTagCategoryContent(helper, item);
                break;
            default:
                break;
        }
    }

    private void convertTagAllContent(BaseViewHolder helper, SheetTagItem item) {
        if (selectedTag.equals(MusicConstants.TAG_ALL)) {
            helper.getView(R.id.tag_name).setSelected(true);
        }
        helper.getView(R.id.tag_name).setOnClickListener(v -> {
            if (mOnTagClickListener != null) {
                mOnTagClickListener.onTagClick(MusicConstants.TAG_ALL);
            }
        });
    }

    private void convertTagCategoryContent(BaseViewHolder helper, SheetTagItem item) {
        SheetTagInfo info = item.getInfo();
        List<SheetTagCategoryItem> list = new ArrayList<>();
        if (info.getTitle().equals(MusicConstants.TAG_CATEGORY_RECOMMEND)) {
            list.add(new SheetTagCategoryItem(info.getTitle(), R.drawable.music_tag_category_recommend));
        } else if (info.getTitle().equals(MusicConstants.TAG_CATEGORY_LANGUAGE)) {
            list.add(new SheetTagCategoryItem(info.getTitle(), R.drawable.music_tag_category_language));
        } else if (info.getTitle().equals(MusicConstants.TAG_CATEGORY_STYLE)) {
            list.add(new SheetTagCategoryItem(info.getTitle(), R.drawable.music_tag_category_style));
        } else if (info.getTitle().equals(MusicConstants.TAG_CATEGORY_EMOTION)) {
            list.add(new SheetTagCategoryItem(info.getTitle(), R.drawable.music_tag_category_emotion));
        } else if (info.getTitle().equals(MusicConstants.TAG_CATEGORY_SCENCE)) {
            list.add(new SheetTagCategoryItem(info.getTitle(), R.drawable.music_tag_category_sence));
        } else if (info.getTitle().equals(MusicConstants.TAG_CATEGORY_THEME)) {
            list.add(new SheetTagCategoryItem(info.getTitle(), R.drawable.music_tag_category_theme));
        }

        for(TagInfo tagInfo : info.getTags()) {
            list.add(new SheetTagCategoryItem(tagInfo.getTag()));
        }
        for (int i = 0; i < getEmptyItemNum(info); i++) {
            list.add(new SheetTagCategoryItem("")); //用空的item填充
        }
        MusicSheetTagCategoryAdapter mAdapter = new MusicSheetTagCategoryAdapter(list, selectedTag);
        RecyclerView mRecyclerView = helper.getView(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (adapter.getItemViewType(position) == SheetTagCategoryItem.TYPE_TAG_ITEM && mOnTagClickListener != null) {
                String Tag = ((SheetTagCategoryItem)adapter.getData().get(position)).getName();
                mOnTagClickListener.onTagClick(Tag);
            }
        });
    }


    private int getEmptyItemNum(SheetTagInfo info) {
        return info.getNum() < 6 ? 6 - info.getNum() : ((info.getNum() + 2) % 4 == 0 ? 0 : 4 - (info.getNum() + 2) % 4);
    }

    private OnTagClickListener mOnTagClickListener;
    public interface OnTagClickListener {
        void onTagClick(String tag);
    }

    public void setOnTagClickListener(OnTagClickListener mOnTagClickListener) {
        this.mOnTagClickListener = mOnTagClickListener;
    }

}
