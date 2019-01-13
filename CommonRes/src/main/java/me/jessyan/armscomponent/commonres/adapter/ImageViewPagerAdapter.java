package me.jessyan.armscomponent.commonres.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonres.R;

public class ImageViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<String> urls = new ArrayList<>();

    public ImageViewPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<String> urls) {
        this.urls.clear();
        this.urls.addAll(urls);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //做了两件事，第一：将当前视图添加到container中，第二：返回当前View
        View view = mLayoutInflater.inflate(R.layout.public_page_item, container, false);
        Glide.with(mContext)
                .load(urls.get(position))
                .into((ImageView) view.findViewById(R.id.img_item));
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
