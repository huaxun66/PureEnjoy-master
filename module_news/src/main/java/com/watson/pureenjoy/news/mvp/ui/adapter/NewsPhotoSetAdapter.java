package com.watson.pureenjoy.news.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.news.R;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import uk.co.senab.photoview.PhotoView;

public class NewsPhotoSetAdapter extends PagerAdapter {
    private Context context;
    private List<String> imageList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private ImageLoader mImageLoader;

    public NewsPhotoSetAdapter(Context context) {
        this.context = context;
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();
    }

    public void setImageList(List<String> imageList) {
        if (imageList != null) {
            this.imageList = imageList;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.news_photo_set_item,null);
        PhotoView photoView = view.findViewById(R.id.photo_view);
        photoView.setOnPhotoTapListener((view1, x, y) -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view1, position);
            }
        });
        mImageLoader.loadImage(context,
                CommonImageConfigImpl
                        .builder()
                        .errorPic(R.drawable.news_image_bg)
                        .fallback(R.drawable.news_image_bg)
                        .url(imageList.get(position))
                        .imageView(photoView)
                        .build());
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
