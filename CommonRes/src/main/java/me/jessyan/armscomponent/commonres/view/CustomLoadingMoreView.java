package me.jessyan.armscomponent.commonres.view;

import com.chad.library.adapter.base.loadmore.LoadMoreView;

import me.jessyan.armscomponent.commonres.R;

public class CustomLoadingMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.public_loading_more_view;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_end_view;
    }
}
