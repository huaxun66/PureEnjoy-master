package com.watson.pureenjoy.music.mvp.ui.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.MusicRefreshEvent;
import com.watson.pureenjoy.music.event.SheetRefreshEvent;
import com.watson.pureenjoy.music.http.entity.local.LocalSheetInfo;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicCollectedSheetAdapter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicCreatedSheetAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseEvent;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.KEY_TYPE;
import static com.watson.pureenjoy.music.app.MusicConstants.LIST_MY_LOVE;

@Route(path = RouterHub.MUSIC_MY_MUSIC_FRAGMENT)
public class MusicMyMusicFragment extends MusicBaseFragment {
    @BindView(R2.id.local_music)
    RelativeLayout mLocalMusic;
    @BindView(R2.id.local_num)
    TextView mLocalNum;
    @BindView(R2.id.recent_play)
    RelativeLayout mRecentPlay;
    @BindView(R2.id.recent_num)
    TextView mRecentNum;
    @BindView(R2.id.my_radio)
    RelativeLayout mMyRadio;
    @BindView(R2.id.radio_num)
    TextView mRadioNum;
    @BindView(R2.id.my_collect)
    RelativeLayout mMyCollection;
    @BindView(R2.id.collect_num)
    TextView mCollectNum;
    @BindView(R2.id.operator_img_create)
    ImageView mCreateArrow;
    @BindView(R2.id.operator_title_create)
    TextView mCreateText;
    @BindView(R2.id.operator_more_create)
    ImageView mCreateMore;
    @BindView(R2.id.operator_bar_create)
    RelativeLayout mCreateLayout;
    @BindView(R2.id.recycler_view_create)
    RecyclerView mCreateRecyclerView;
    @BindView(R2.id.operator_img_collect)
    ImageView mCollectArrow;
    @BindView(R2.id.operator_title_collect)
    TextView mCollectText;
    @BindView(R2.id.operator_more_collect)
    ImageView mCollectMore;
    @BindView(R2.id.operator_bar_collect)
    RelativeLayout mCollectLayout;
    @BindView(R2.id.recycler_view_collect)
    RecyclerView mCollectRecyclerView;

    private List<LocalSheetInfo> createSheetList;
    private List<SheetInfo> collectSheetList;

    private MusicCreatedSheetAdapter createAdapter;
    private MusicCollectedSheetAdapter collectAdapter;

    private DBManager dbManager;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.music_fragment_my_music, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        dbManager = DBManager.getInstance(getContext());
        createAdapter = new MusicCreatedSheetAdapter(getContext(), R.layout.music_created_sheet_item, null);
        mCreateRecyclerView.setAdapter(createAdapter);
        mCreateRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        collectAdapter = new MusicCollectedSheetAdapter(getContext(), R.layout.music_collected_sheet_item, null);
        mCollectRecyclerView.setAdapter(collectAdapter);
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setData(null);
        initListener();
    }

    private void initListener() {
        mLocalMusic.setOnClickListener(v -> ARouter.getInstance().build(RouterHub.MUSIC_LOCAL_MUSIC_ACTIVITY).navigation());
        mRecentPlay.setOnClickListener(v -> ARouter.getInstance().build(RouterHub.MUSIC_RECENT_PLAY_LIST_ACTIVITY).navigation());
        mMyRadio.setOnClickListener(v -> {

        });
        mMyCollection.setOnClickListener(v -> ARouter.getInstance().build(RouterHub.MUSIC_LOCAL_SONG_LIST_ACTIVITY)
                .withInt(KEY_TYPE, LIST_MY_LOVE)
                .navigation());
        mCreateLayout.setOnClickListener(v -> {
            if (mCreateRecyclerView.getVisibility() == View.VISIBLE) {
                mCreateRecyclerView.setVisibility(View.GONE);
                ObjectAnimator.ofFloat(mCreateArrow, "rotation", 0.0F, -90.0F).setDuration(300).start();
            } else {
                mCreateRecyclerView.setVisibility(View.VISIBLE);
                ObjectAnimator.ofFloat(mCreateArrow, "rotation", -90.0F, 0F).setDuration(300).start();
            }
        });
        mCreateMore.setOnClickListener(v -> {

        });
        mCollectLayout.setOnClickListener(v -> {
            if (mCollectRecyclerView.getVisibility() == View.VISIBLE) {
                mCollectRecyclerView.setVisibility(View.GONE);
                ObjectAnimator.ofFloat(mCollectArrow, "rotation", 0.0F, -90.0F).setDuration(300).start();
            } else {
                mCollectRecyclerView.setVisibility(View.VISIBLE);
                ObjectAnimator.ofFloat(mCollectArrow, "rotation", -90.0F, 0.0F).setDuration(300).start();
            }
        });
        mCollectMore.setOnClickListener(v -> {

        });
    }

    @Override
    public void setData(@Nullable Object data) {
        mLocalNum.setText(String.valueOf(dbManager.getMusicCount(MusicConstants.LIST_ALL_MUSIC)));
        mRecentNum.setText(String.valueOf(dbManager.getMusicCount(MusicConstants.LIST_RECENT_PLAY)));
        mCollectNum.setText(String.valueOf(dbManager.getMusicCount(MusicConstants.LIST_MY_LOVE)));
        createSheetList = dbManager.getCreateSheetList();
        collectSheetList = dbManager.getCollectSheetList();
        mCreateText.setText(getString(R.string.music_my_create_sheet, createSheetList.size()));
        mCollectText.setText(getString(R.string.music_my_collect_sheet, collectSheetList.size()));
        createAdapter.setNewData(createSheetList);
        collectAdapter.setNewData(collectSheetList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event instanceof MusicRefreshEvent) {
            setData(null);
        } else if (event instanceof SheetRefreshEvent) {
            setData(null);
        }
    }
}
