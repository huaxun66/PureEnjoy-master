package com.watson.pureenjoy.music.mvp.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.jessyan.armscomponent.commonsdk.utils.DisplayUtil;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

/**
 * Created by AchillesL on 2016/11/15.
 */

public class DiscView extends RelativeLayout {

    /*ViewPager切换时间*/
    public static final int VIEWPAGER_SCROLL_TIME = 500;

    /*手柄起始角度*/
    public static final float ROTATION_INIT_NEEDLE = -30;

    /*截图屏幕宽高*/
    private static final float BASE_SCREEN_WIDTH = (float) 1080.0;
    private static final float BASE_SCREEN_HEIGHT = (float) 1920.0;

    /*唱针宽高、距离等比例*/
    public static final float SCALE_NEEDLE_WIDTH = (float) (276.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_MARGIN_LEFT = (float) (500.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_PIVOT_X = (float) (43.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_PIVOT_Y = (float) (43.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_HEIGHT = (float) (413.0 / BASE_SCREEN_HEIGHT);
    public static final float SCALE_NEEDLE_MARGIN_TOP = (float) (43.0 / BASE_SCREEN_HEIGHT);

    /*唱盘比例*/
    public static final float SCALE_DISC_SIZE = (float) (813.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_DISC_MARGIN_TOP = (float) (190.0 / BASE_SCREEN_HEIGHT);

    /*专辑图片比例*/
    public static final float SCALE_MUSIC_PIC_SIZE = (float) (533.0 / BASE_SCREEN_WIDTH);

    private ImageView mIvNeedle;
    private ViewPager mVpContain;
    private ViewPagerAdapter mViewPagerAdapter;
    private ObjectAnimator mNeedleAnimator;

    private List<View> mDiscLayouts = new ArrayList<>();
    private List<LocalMusicInfo> mMusicList = new ArrayList<>();
    private List<ObjectAnimator> mDiscAnimators = new ArrayList<>();

    /*标记ViewPager是否处于偏移的状态*/
    private boolean mViewPagerIsOffset = false;

    /*标记唱针复位后，是否需要重新偏移到唱片处*/
    private boolean mIsNeed2StartPlayAnimator = false;
    private MusicStatus musicStatus = MusicStatus.STOP;

    public static final int DURATION_NEEDLE_ANIMATOR = 500;
    private NeedleAnimatorStatus needleAnimatorStatus = NeedleAnimatorStatus.IN_FAR_END;

    private IPlayInfo mIPlayInfo;

    private int mScreenWidth, mScreenHeight;

    /*唱针当前所处的状态*/
    private enum NeedleAnimatorStatus {
        /*移动时：从唱盘往远处移动*/
        TO_FAR_END,
        /*移动时：从远处往唱盘移动*/
        TO_NEAR_END,
        /*静止时：离开唱盘*/
        IN_FAR_END,
        /*静止时：贴近唱盘*/
        IN_NEAR_END
    }

    /*音乐当前的状态：只有播放、暂停、停止三种*/
    public enum MusicStatus {
        PLAY, PAUSE, STOP
    }

    /*DiscView需要触发的音乐切换状态：播放、暂停、上/下一首、停止*/
    public enum MusicChangedStatus {
        PLAY, PAUSE, NEXT, PREV, STOP
    }

    public interface IPlayInfo {
        /*用于更新标题栏变化*/
        void onMusicInfoChanged(String musicName, String musicSinger);
        /*用于更新背景图片*/
        void onMusicPicChanged(String albumThumbs);
        /*用于更新音乐播放状态*/
        void onMusicChanged(MusicChangedStatus musicChangedStatus);
    }

    public DiscView(Context context) {
        this(context, null);
    }

    public DiscView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = DisplayUtil.getScreenWidth(context);
        mScreenHeight = DisplayUtil.getScreenHeight(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initDiscBackground();
        initViewPager();
        initNeedle();
        initObjectAnimator();
    }

    private void initDiscBackground() {
        ImageView mDiscBackground = findViewById(R.id.disc_bg);
        mDiscBackground.setImageDrawable(getDiscBackgroundDrawable());

        int marginTop = (int) (SCALE_DISC_MARGIN_TOP * mScreenHeight);
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mDiscBackground.getLayoutParams();
        layoutParams.setMargins(0, marginTop, 0, 0);

        mDiscBackground.setLayoutParams(layoutParams);
    }

    private void initViewPager() {
        mViewPagerAdapter = new ViewPagerAdapter();
        mVpContain = findViewById(R.id.view_pager);
        mVpContain.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //通过反射改变viewpager动画时间
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            MyScroller mScroller = new MyScroller(mVpContain.getContext().getApplicationContext(), new LinearInterpolator());
            mField.set(mVpContain, mScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mVpContain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int lastPositionOffsetPixels = 0;
            int currentItem = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //左滑
                if (lastPositionOffsetPixels > positionOffsetPixels) {
                    if (positionOffset < 0.5) {
                        notifyMusicInfoChanged(position);
                    } else {
                        notifyMusicInfoChanged(mVpContain.getCurrentItem());
                    }
                }
                //右滑
                else if (lastPositionOffsetPixels < positionOffsetPixels) {
                    if (positionOffset > 0.5) {
                        notifyMusicInfoChanged(position + 1);
                    } else {
                        notifyMusicInfoChanged(position);
                    }
                }
                lastPositionOffsetPixels = positionOffsetPixels;
            }

            @Override
            public void onPageSelected(int position) {
                resetOtherDiscAnimation(position);
                notifyMusicPicChanged(position);
                if (Math.abs(position - currentItem) == 1) {
                    if (position > currentItem) {
                        notifyMusicStatusChanged(MusicChangedStatus.NEXT);
                    } else {
                        notifyMusicStatusChanged(MusicChangedStatus.PREV);
                    }
                }
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                doWithAnimatorOnPageScroll(state);
            }
        });
        mVpContain.setAdapter(mViewPagerAdapter);

        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mVpContain.getLayoutParams();
        int marginTop = (int) (SCALE_DISC_MARGIN_TOP * mScreenHeight);
        layoutParams.setMargins(0, marginTop, 0, 0);
        mVpContain.setLayoutParams(layoutParams);
    }

    public class MyScroller extends Scroller {
        private int animTime = VIEWPAGER_SCROLL_TIME;

        public MyScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, animTime);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, animTime);
        }
    }

    /**
     * 取消其他页面上的动画，并将图片旋转角度复原
     */
    private void resetOtherDiscAnimation(int position) {
        for (int i = 0; i < mDiscLayouts.size(); i++) {
            if (position == i) continue;
            mDiscAnimators.get(position).cancel();
            ImageView imageView = mDiscLayouts.get(i).findViewById(R.id.iv_disc);
            imageView.setRotation(0);
        }
    }

    private void doWithAnimatorOnPageScroll(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
            case ViewPager.SCROLL_STATE_SETTLING: {
                mViewPagerIsOffset = false;
                if (musicStatus == MusicStatus.PLAY) {
                    playAnimator();
                }
                break;
            }
            case ViewPager.SCROLL_STATE_DRAGGING: {
                mViewPagerIsOffset = true;
                pauseAnimator();
                break;
            }
        }
    }

    private void initNeedle() {
        mIvNeedle = findViewById(R.id.needle);

        int needleWidth = (int) (SCALE_NEEDLE_WIDTH * mScreenWidth);
        int needleHeight = (int) (SCALE_NEEDLE_HEIGHT * mScreenHeight);

        /*设置手柄的外边距为负数，让其隐藏一部分*/
        int marginTop = (int) (SCALE_NEEDLE_MARGIN_TOP * mScreenHeight) * -1;
        int marginLeft = (int) (SCALE_NEEDLE_MARGIN_LEFT * mScreenWidth);

        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.music_play_needle);
        Bitmap bitmap = Bitmap.createScaledBitmap(originBitmap, needleWidth, needleHeight, false);

        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mIvNeedle.getLayoutParams();
        layoutParams.setMargins(marginLeft, marginTop, 0, 0);

        int pivotX = (int) (SCALE_NEEDLE_PIVOT_X * mScreenWidth);
        int pivotY = (int) (SCALE_NEEDLE_PIVOT_Y * mScreenWidth);

        mIvNeedle.setPivotX(pivotX);
        mIvNeedle.setPivotY(pivotY);
        mIvNeedle.setRotation(ROTATION_INIT_NEEDLE);
        mIvNeedle.setImageBitmap(bitmap);
        mIvNeedle.setLayoutParams(layoutParams);
    }

    private void initObjectAnimator() {
        mNeedleAnimator = ObjectAnimator.ofFloat(mIvNeedle, View.ROTATION, ROTATION_INIT_NEEDLE, 0);
        mNeedleAnimator.setDuration(DURATION_NEEDLE_ANIMATOR);
        mNeedleAnimator.setInterpolator(new AccelerateInterpolator());
        mNeedleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                /**
                 * 根据动画开始前NeedleAnimatorStatus的状态，
                 * 即可得出动画进行时NeedleAnimatorStatus的状态
                 * */
                if (needleAnimatorStatus == NeedleAnimatorStatus.IN_FAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.TO_NEAR_END;
                } else if (needleAnimatorStatus == NeedleAnimatorStatus.IN_NEAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.TO_FAR_END;
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (needleAnimatorStatus == NeedleAnimatorStatus.TO_NEAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.IN_NEAR_END;
                    int index = mVpContain.getCurrentItem();
                    playDiscAnimator(index);
                    musicStatus = MusicStatus.PLAY;
                } else if (needleAnimatorStatus == NeedleAnimatorStatus.TO_FAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.IN_FAR_END;
                    if (musicStatus == MusicStatus.STOP) {
                        mIsNeed2StartPlayAnimator = true;
                    }
                }

                if (mIsNeed2StartPlayAnimator) {
                    mIsNeed2StartPlayAnimator = false;
                    /**
                     * 只有在ViewPager不处于偏移状态时，才开始唱盘旋转动画
                     * */
                    if (!mViewPagerIsOffset) {
                        /*延时500ms*/
                        DiscView.this.postDelayed(() -> playAnimator(), 50);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void setPlayInfoListener(IPlayInfo listener) {
        this.mIPlayInfo = listener;
    }

    /*得到唱盘背后半透明的圆形背景*/
    private Drawable getDiscBackgroundDrawable() {
        int discSize = (int) (mScreenWidth * SCALE_DISC_SIZE);
        Bitmap bitmapDisc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.music_play_disc_bg), discSize, discSize, false);
        RoundedBitmapDrawable roundDiscDrawable = RoundedBitmapDrawableFactory.create
                (getResources(), bitmapDisc);
        return roundDiscDrawable;
    }

    /**
     * 得到唱盘图片
     * 唱盘图片由空心圆盘及音乐专辑图片“合成”得到
     */
    private Drawable getDiscDrawable(String albumThumbs) {
        int discSize = (int) (mScreenWidth * SCALE_DISC_SIZE);
        int musicPicSize = (int) (mScreenWidth * SCALE_MUSIC_PIC_SIZE);

        Bitmap bitmapDisc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R
                .drawable.music_play_disc), discSize, discSize, false);
        Bitmap bitmapMusicPic;
        if (StringUtil.isEmpty(albumThumbs)) {
            bitmapMusicPic = getMusicPicBitmap(musicPicSize, R.drawable.music_sheet_bg);
        } else {
            bitmapMusicPic = getMusicPicBitmap(musicPicSize, albumThumbs);
        }
        BitmapDrawable discDrawable = new BitmapDrawable(bitmapDisc);
        RoundedBitmapDrawable roundMusicDrawable = RoundedBitmapDrawableFactory.create
                (getResources(), bitmapMusicPic);

        //抗锯齿
        discDrawable.setAntiAlias(true);
        roundMusicDrawable.setAntiAlias(true);

        Drawable[] drawables = new Drawable[2];
        drawables[0] = roundMusicDrawable;
        drawables[1] = discDrawable;

        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        int musicPicMargin = (int) ((SCALE_DISC_SIZE - SCALE_MUSIC_PIC_SIZE) * mScreenWidth / 2);
        //调整专辑图片的四周边距，让其显示在正中
        layerDrawable.setLayerInset(0, musicPicMargin, musicPicMargin, musicPicMargin,
                musicPicMargin);

        return layerDrawable;
    }

    private Bitmap getMusicPicBitmap(int musicPicSize, String albumThumbs) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(albumThumbs, options);
        int imageWidth = options.outWidth;
        int sample = imageWidth / musicPicSize;
        int dstSample = 1;
        if (sample > dstSample) {
            dstSample = sample;
        }
        options.inJustDecodeBounds = false;
        //设置图片采样率
        options.inSampleSize = dstSample;
        //设置图片解码格式
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(albumThumbs, options), musicPicSize, musicPicSize, true);
    }

    private Bitmap getMusicPicBitmap(int musicPicSize, int musicPicRes) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),musicPicRes,options);
        int imageWidth = options.outWidth;
        int sample = imageWidth / musicPicSize;
        int dstSample = 1;
        if (sample > dstSample) {
            dstSample = sample;
        }
        options.inJustDecodeBounds = false;
        //设置图片采样率
        options.inSampleSize = dstSample;
        //设置图片解码格式
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                musicPicRes, options), musicPicSize, musicPicSize, true);
    }

    public void setMusicDataList(List<LocalMusicInfo> musicDataList, LocalMusicInfo currentMusicInfo) {
        if (musicDataList.isEmpty()) return;

        mDiscLayouts.clear();
        mMusicList.clear();
        mDiscAnimators.clear();
        mMusicList.addAll(musicDataList);

        for (LocalMusicInfo musicData : mMusicList) {
            View discLayout = LayoutInflater.from(getContext()).inflate(R.layout.music_layout_disc, mVpContain, false);

            ImageView disc = discLayout.findViewById(R.id.iv_disc);
            disc.setImageDrawable(getDiscDrawable(musicData.getAlbumThumbs()));

            mDiscAnimators.add(getDiscObjectAnimator(disc));
            mDiscLayouts.add(discLayout);
        }
        mViewPagerAdapter.notifyDataSetChanged();
        mVpContain.setCurrentItem(musicDataList.indexOf(currentMusicInfo), false);

        if (mIPlayInfo != null) {
            mIPlayInfo.onMusicInfoChanged(currentMusicInfo.getName(), currentMusicInfo.getSinger());
            mIPlayInfo.onMusicPicChanged(currentMusicInfo.getAlbumThumbs());
        }
    }

    private ObjectAnimator getDiscObjectAnimator(ImageView disc) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(disc, View.ROTATION, 0, 360);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setDuration(20 * 1000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        return objectAnimator;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /*播放动画*/
    private void playAnimator() {
        /*唱针处于远端时，直接播放动画*/
        if (needleAnimatorStatus == NeedleAnimatorStatus.IN_FAR_END) {
            mNeedleAnimator.start();
        }
        /*唱针处于往远端移动时，设置标记，等动画结束后再播放动画*/
        else if (needleAnimatorStatus == NeedleAnimatorStatus.TO_FAR_END) {
            mIsNeed2StartPlayAnimator = true;
        }
    }

    /*暂停动画*/
    private void pauseAnimator() {
        /*播放时暂停动画*/
        if (needleAnimatorStatus == NeedleAnimatorStatus.IN_NEAR_END) {
            int index = mVpContain.getCurrentItem();
            pauseDiscAnimator(index);
        }
        /*唱针往唱盘移动时暂停动画*/
        else if (needleAnimatorStatus == NeedleAnimatorStatus.TO_NEAR_END) {
            mNeedleAnimator.reverse();
            /**
             * 若动画在没结束时执行reverse方法，则不会执行监听器的onStart方法，此时需要手动设置
             * */
            needleAnimatorStatus = NeedleAnimatorStatus.TO_FAR_END;
        }
        /**
         * 动画可能执行多次，只有音乐处于停止 / 暂停状态时，才执行暂停命令
         * */
        if (musicStatus == MusicStatus.STOP) {
            notifyMusicStatusChanged(MusicChangedStatus.STOP);
        }else if (musicStatus == MusicStatus.PAUSE) {
            notifyMusicStatusChanged(MusicChangedStatus.PAUSE);
        }
    }

    /*播放唱盘动画*/
    private void playDiscAnimator(int index) {
        ObjectAnimator objectAnimator = mDiscAnimators.get(index);
        if (objectAnimator.isPaused()) {
            objectAnimator.resume();
        } else {
            objectAnimator.start();
        }
        /**
         * 唱盘动画可能执行多次，只有不是音乐不在播放状态，在回调执行播放
         * */
        if (musicStatus != MusicStatus.PLAY) {
            notifyMusicStatusChanged(MusicChangedStatus.PLAY);
        }
    }

    /*暂停唱盘动画*/
    private void pauseDiscAnimator(int index) {
        ObjectAnimator objectAnimator = mDiscAnimators.get(index);
        objectAnimator.pause();
        mNeedleAnimator.reverse();
    }

    public void notifyMusicInfoChanged(int position) {
        if (mIPlayInfo != null) {
            LocalMusicInfo musicData = mMusicList.get(position);
            mIPlayInfo.onMusicInfoChanged(musicData.getName(), musicData.getSinger());
        }
    }

    public void notifyMusicPicChanged(int position) {
        if (mIPlayInfo != null) {
            LocalMusicInfo musicData = mMusicList.get(position);
            mIPlayInfo.onMusicPicChanged(musicData.getAlbumThumbs());
        }
    }

    public void notifyMusicStatusChanged(MusicChangedStatus musicChangedStatus) {
        if (mIPlayInfo != null) {
            mIPlayInfo.onMusicChanged(musicChangedStatus);
        }
    }

    private void play() {
        playAnimator();
    }

    private void pause() {
        musicStatus = MusicStatus.PAUSE;
        pauseAnimator();
    }

    public void stop() {
        musicStatus = MusicStatus.STOP;
        pauseAnimator();
    }

    public void playOrPause() {
        if (musicStatus == MusicStatus.PLAY) {
            pause();
        } else {
            play();
        }
    }

    public void next() {
        int currentItem = mVpContain.getCurrentItem();
        if (currentItem == mMusicList.size() - 1) {
            Toasty.info(getContext(), R.string.music_play_last).show();
        } else {
            selectMusicWithButton();
            mVpContain.setCurrentItem(currentItem + 1, true);
        }
    }

    public void prev() {
        int currentItem = mVpContain.getCurrentItem();
        if (currentItem == 0) {
            Toasty.info(getContext(), R.string.music_play_first).show();
        } else {
            selectMusicWithButton();
            mVpContain.setCurrentItem(currentItem - 1, true);
        }
    }

    public void setPosition(int position) {
        mVpContain.setCurrentItem(position, true);
        mNeedleAnimator.reverse();
    }

    public boolean isPlaying() {
        return musicStatus == MusicStatus.PLAY;
    }

    private void selectMusicWithButton() {
        if (musicStatus == MusicStatus.PLAY) {
            mIsNeed2StartPlayAnimator = true;
            pauseAnimator();
        } else if (musicStatus == MusicStatus.PAUSE) {
            play();
        }
    }

    class ViewPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View discLayout = mDiscLayouts.get(position);
            container.addView(discLayout);
            return discLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mDiscLayouts.get(position));
        }

        @Override
        public int getCount() {
            return mDiscLayouts.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
