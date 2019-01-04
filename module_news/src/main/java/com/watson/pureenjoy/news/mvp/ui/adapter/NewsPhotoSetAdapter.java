package com.watson.pureenjoy.news.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.watson.pureenjoy.news.R;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class NewsPhotoSetAdapter extends PagerAdapter {
    private Context context;
    private List<String> imageList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public NewsPhotoSetAdapter(Context context) {
        this.context = context;
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
        RequestOptions options = new RequestOptions().fallback(R.drawable.news_image_bg).error(R.drawable.news_image_bg);
        Glide.with(context)
                .load(imageList.get(position))
                .apply(options)
                .into(photoView);
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
