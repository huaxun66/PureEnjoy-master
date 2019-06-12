package com.watson.pureenjoy.music.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.MusicRefreshEvent;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicLocalSongAdapter;
import com.watson.pureenjoy.music.mvp.ui.view.MusicPopMenuWindow;
import com.watson.pureenjoy.music.mvp.ui.view.SlideBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseEvent;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.KEY_TITLE;
import static com.watson.pureenjoy.music.app.MusicConstants.KEY_TYPE;
import static com.watson.pureenjoy.music.app.MusicConstants.LIST_ALBUM;
import static com.watson.pureenjoy.music.app.MusicConstants.LIST_FOLDER;
import static com.watson.pureenjoy.music.app.MusicConstants.LIST_SINGER;

@Route(path = RouterHub.MUSIC_LOCAL_SONG_FRAGMENT)
public class MusicLocalSongFragment extends MusicBaseFragment {
    @BindView(R2.id.play)
    ImageView mPlayAllBtn;
    @BindView(R2.id.num)
    TextView mNumTv;
    @BindView(R2.id.manager)
    TextView mManagerBtn;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R2.id.slider_bar)
    SlideBar mSlideBar;

    private DBManager dbManager;
    private MusicLocalSongAdapter mAdapter;
    private List<LocalMusicInfo> musicInfoList = new ArrayList<>();
    private View view;

    private int type;
    private String title;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.music_fragment_local_song, null);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getArguments().getInt(KEY_TYPE, -1);
        title = getArguments().getString(KEY_TITLE);
        dbManager = DBManager.getInstance(getContext());
        musicInfoList = getData();
        mAdapter = new MusicLocalSongAdapter(getContext(), R.layout.music_local_song_item, musicInfoList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNumTv.setText(getString(R.string.music_song_num, musicInfoList.size()));
        initListener();
    }

    public List<LocalMusicInfo> getData() {
        List<LocalMusicInfo> musicInfoList;
        switch (type) {
            case LIST_SINGER:
                musicInfoList = dbManager.getMusicListBySinger(title);
                break;
            case LIST_ALBUM:
                musicInfoList = dbManager.getMusicListByAlbum(title);
                break;
            case LIST_FOLDER:
                musicInfoList = dbManager.getMusicListByFolder(title);
                break;
            default:
                musicInfoList = dbManager.getAllMusicList();
        }
        Collections.sort(musicInfoList);
        return musicInfoList;
    }

    @Override
    public void setData(@Nullable Object data) {
    }

    private void initListener() {
        mPlayAllBtn.setOnClickListener(v -> showMessage("播放"));
        mManagerBtn.setOnClickListener(v -> showMessage("管理"));
        mAdapter.setOnItemClickListener(new MusicLocalSongAdapter.OnItemClickListener() {
            @Override
            public void onMoreMenuClick(int position) {
                LocalMusicInfo musicInfo = musicInfoList.get(position);
                showPopFromBottom(musicInfo);
            }

            @Override
            public void onDeleteMenuClick(int position) {
                showMessage("onDeleteMenuClick");
            }

            @Override
            public void onContentClick(int position) {
                showMessage("onContentClick");
            }
        });
        mSlideBar.setOnListener(letter -> {
            //该字母首次出现的位置
            int position = mAdapter.getPositionForSection(letter.charAt(0));
            if(position != -1){
                mRecyclerView.smoothScrollToPosition(position);
            }
        });
    }

    public void showPopFromBottom(LocalMusicInfo musicInfo) {
        MusicPopMenuWindow menuPopupWindow = new MusicPopMenuWindow(getActivity(), musicInfo, view, MusicConstants.ACTIVITY_LOCAL);
        //设置PopupWindow显示位置（从底部弹出）
        menuPopupWindow.showAtLocation(view, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        //当弹出PopupWindow时，背景变半透明
        params.alpha = 0.7f;
        getActivity().getWindow().setAttributes(params);
        //设置PopupWindow关闭监听，当PopupWindow关闭，背景恢复1f
        menuPopupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams params1 = getActivity().getWindow().getAttributes();
            params1.alpha = 1f;
            getActivity().getWindow().setAttributes(params1);
        });
    }

    public void updateView(){
        musicInfoList = getData();
        mAdapter.updateMusicInfoList(musicInfoList);
        mNumTv.setText(getString(R.string.music_song_num, musicInfoList.size()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event instanceof MusicRefreshEvent) {
            updateView();
        }
    }

}
