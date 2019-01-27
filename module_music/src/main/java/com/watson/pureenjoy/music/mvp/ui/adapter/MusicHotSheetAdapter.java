package com.watson.pureenjoy.music.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;

import java.util.Arrays;
import java.util.List;

import me.jessyan.armscomponent.commonres.view.flowlayout.FlowLayout;
import me.jessyan.armscomponent.commonres.view.flowlayout.TagAdapter;
import me.jessyan.armscomponent.commonres.view.flowlayout.TagFlowLayout;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

public class MusicHotSheetAdapter extends BaseQuickAdapter<SheetInfo, BaseViewHolder> {
    private Context mContext;
    private ImageLoader mImageLoader;

    public MusicHotSheetAdapter(Context context,int layoutResId,List<SheetInfo> data) {
        super(layoutResId,data);
        this.mContext = context;
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, SheetInfo info) {
        mImageLoader.loadImage(mContext,
                CommonImageConfigImpl
                        .builder()
                        .url(info.getPic())
                        .errorPic(R.drawable.music_sheet_bg)
                        .fallback(R.drawable.music_sheet_bg)
                        .imageView(helper.getView(R.id.img))
                        .build());
        int count = Integer.parseInt(info.getListenum());
        String countText = count > 10000 ? count / 10000 + "万" : count + "";
        helper.setText(R.id.title, info.getTitle())
              .setText(R.id.count, countText);
        if (!StringUtil.isEmpty(info.getTag())) {
            String[] array = info.getTag().split(",");
            ((TagFlowLayout)helper.getView(R.id.tfl_tag)).setAdapter(new TagAdapter<String>(Arrays.asList(array)) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {
                    TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.music_sheet_tag_item, null);
                    textView.setText(o);
                    return textView;
                }
            });
        }

    }

}
