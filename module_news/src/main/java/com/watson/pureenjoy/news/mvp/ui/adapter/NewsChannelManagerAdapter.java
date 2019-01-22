package com.watson.pureenjoy.news.mvp.ui.adapter;

import android.app.Service;
import android.os.Vibrator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.http.entity.ChannelItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.watson.pureenjoy.news.app.NewsConstants.RECOMMEND_TYPE_ID;

public class NewsChannelManagerAdapter extends BaseMultiItemQuickAdapter<ChannelItem, BaseViewHolder> {
    private List<ChannelItem> datas;
    private boolean isEdit;

    public NewsChannelManagerAdapter(List<ChannelItem> datas, boolean isEdit) {
        super(datas);
        this.datas = datas;
        this.isEdit = isEdit;
        addMultiItemType();
    }

    public void setEdit(Boolean flag) {
        isEdit = flag;
    }

    private void addMultiItemType() {
        addItemType(ChannelItem.TYPE_TITLE, R.layout.news_recommend_title);
        addItemType(ChannelItem.TYPE_CONTENT, R.layout.news_channel_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelItem item) {
        switch (helper.getItemViewType()) {
            case ChannelItem.TYPE_TITLE:
                //Do Nothing
                break;
            case ChannelItem.TYPE_CONTENT:
                convertContent(helper, item);
                break;
            default:
                break;
        }
    }

    private void convertContent(BaseViewHolder helper, ChannelItem item) {
        helper.setText(R.id.tv_name, item.getName());
        if (helper.getAdapterPosition() > getRecommendTitlePosition()) {
            helper.setGone(R.id.img_icon, false);
            helper.setBackgroundRes(R.id.tv_name, R.drawable.news_shape_round_white_bg);
        } else {
            if (isEdit && helper.getAdapterPosition() != 0) {
                helper.setGone(R.id.img_icon, true);
                helper.setBackgroundRes(R.id.tv_name, R.drawable.news_shape_round_grey_bg);
            } else {
                helper.setGone(R.id.img_icon, false);
                helper.setBackgroundRes(R.id.tv_name, R.drawable.news_shape_round_white_bg);
            }
        }
    }

    public int getRecommendTitlePosition() {
        ChannelItem item = new ChannelItem();
        item.setType(1);
        item.setTypeId(RECOMMEND_TYPE_ID);
        return getData().indexOf(item);
    }

    public ArrayList<ChannelItem> getSelectedChannels() {
        ArrayList<ChannelItem> list = new ArrayList<>();
        for (int i = 0; i < getRecommendTitlePosition(); i++) {
            list.add(getData().get(i));
        }
        return list;
    }


    public ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (toPosition == 0) {
                return false;
            }
            if (fromPosition < toPosition) {
                if (toPosition > getRecommendTitlePosition()) {
                    toPosition = getRecommendTitlePosition() + 1;
                }
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mData, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mData, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        }

        //当选中item的时候（拖拽开始的时候）调用
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            //震动70毫秒
            Vibrator vib = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
            vib.vibrate(70);
            if (listener != null) {
                listener.onSelectedChanged();
            }
        }

        //当手指松开的时候（拖拽完成的时候）调用
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            notifyDataSetChanged();
            if (listener != null) {
                listener.clearView();
            }
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }
    });

    public interface ItemOnSelectListener {
        void onSelectedChanged();

        void clearView();
    }

    private ItemOnSelectListener listener;

    public void setItemOnSelectListener(ItemOnSelectListener listener) {
        this.listener = listener;
    }

}
