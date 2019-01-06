package com.watson.pureenjoy.news.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.http.entity.ChannelItem;

import java.util.ArrayList;
import java.util.Collections;

import static com.watson.pureenjoy.news.app.NewsConstants.RECOMMEND_TYPE_ID;

public class NewsChannelManagerAdapter extends RecyclerView.Adapter<NewsChannelManagerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ChannelItem> mData;
    private boolean isEdit;
    public final int TYPE_TITLE = 1;
    public final int TYPE_CONTENT = 2;

    public NewsChannelManagerAdapter(Context mContext, ArrayList<ChannelItem> mData, boolean isEdit) {
        this.mContext = mContext;
        this.mData = mData;
        this.isEdit = isEdit;
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    public ChannelItem getData(int position){
        return mData.get(position);
    }

    public ArrayList<ChannelItem> getData(){
        return mData;
    }

    public void setEdit(Boolean flag){
        isEdit = flag;
    }

    public void setData(ArrayList<ChannelItem> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TITLE: {
                return new ViewHolder(TYPE_TITLE, LayoutInflater.from(mContext).inflate(R.layout.news_recommend_title, null));
            }
            default: {
                return new ViewHolder(TYPE_CONTENT, LayoutInflater.from(mContext).inflate(R.layout.news_channel_item, null));
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ChannelItem item = mData.get(position);
        if (item != null) {
            holder.initDate(item, position);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView img_icon;

        public ViewHolder(int viewType, final View itemView) {
            super(itemView);
            switch (viewType) {
                case TYPE_TITLE: {
                    break;
                }
                default: {
                    tv_name = itemView.findViewById(R.id.tv_name);
                    img_icon = itemView.findViewById(R.id.img_icon);
                    break;
                }
            }
        }

        void initDate(ChannelItem info, int position) {
            if (info.getType() != TYPE_TITLE) {
                tv_name.setText(info.getName());
                if(position > getRecommendTitlePosition()){
                    img_icon.setVisibility(View.GONE);
                    tv_name.setBackground(mContext.getResources().getDrawable(R.drawable.news_shape_round_white_bg));
                } else {
                    if(isEdit && position!=0){
                        img_icon.setVisibility(View.VISIBLE);
                        tv_name.setBackground(mContext.getResources().getDrawable(R.drawable.news_shape_round_grey_bg));
                    }else{
                        img_icon.setVisibility(View.GONE);
                        tv_name.setBackground(mContext.getResources().getDrawable(R.drawable.news_shape_round_white_bg));
                    }
                }
            }
        }
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
                if(toPosition > getRecommendTitlePosition()){
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
//            Vibrator vib = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
//            vib.vibrate(70);
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

    public int getRecommendTitlePosition() {
        ChannelItem item = new ChannelItem();
        item.setType(1);
        item.setTypeId(RECOMMEND_TYPE_ID);
        return mData.indexOf(item);
    }

    public ArrayList<ChannelItem> getSelectedChannels() {
        ArrayList<ChannelItem> list = new ArrayList<>();
        for (int i = 0; i < getRecommendTitlePosition(); i++) {
            list.add(mData.get(i));
        }
        return list;
    }

    public interface ItemOnSelectListener {
        void onSelectedChanged();
        void clearView();
    }

    private ItemOnSelectListener listener;
    public void setItemOnSelectListener(ItemOnSelectListener listener) {
        this.listener = listener;
    }

}
