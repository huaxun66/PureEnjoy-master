package com.watson.pureenjoy.music.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.sheet.SheetTagCategoryItem;
import com.watson.pureenjoy.music.http.entity.sheet.SheetTagItem;

import java.util.List;

public class MusicSheetTagCategoryAdapter extends BaseMultiItemQuickAdapter<SheetTagCategoryItem, BaseViewHolder> {
    private String selectedTag;

    public MusicSheetTagCategoryAdapter(List<SheetTagCategoryItem> datas, String selectedTag) {
        super(datas);
        this.selectedTag = selectedTag;
        addMultiItemType();
    }

    private void addMultiItemType() {
        addItemType(SheetTagCategoryItem.TYPE_TAG_CATEGORY_ITEM, R.layout.music_tag_category_item);
        addItemType(SheetTagCategoryItem.TYPE_TAG_ITEM, R.layout.music_tag_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SheetTagCategoryItem item) {
        switch (helper.getItemViewType()) {
            case SheetTagItem.TYPE_TAG_ALL:
                convertTagCategoryContent(helper, item);
                break;
            case SheetTagItem.TYPE_TAG_CATEGORY:
                convertTagItemContent(helper, item);
                break;
            default:
                break;
        }
    }


    private void convertTagCategoryContent(BaseViewHolder helper, SheetTagCategoryItem item) {
        helper.setText(R.id.category_name, item.getName());
        helper.setImageResource(R.id.category_img, item.getIcon());

    }

    private void convertTagItemContent(BaseViewHolder helper, SheetTagCategoryItem item) {
        helper.setText(R.id.tag_name, item.getName());
        if (selectedTag.equals(item.getName())) {
            helper.getView(R.id.root).setSelected(true);
        }
    }

}
