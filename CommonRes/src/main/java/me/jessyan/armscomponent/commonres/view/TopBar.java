package me.jessyan.armscomponent.commonres.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonsdk.utils.DisplayUtil;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

/**
 * 顶上标题栏控件
 */

public class TopBar extends RelativeLayout {
    private static final String TAG = "TopBar";

    private Context mContext;
    private RelativeLayout menu;
    //左侧图片布局
    private ImageView ivLeft;
    //左侧第二图片布局
    private ImageView ivLeftSecond;
    //右侧图片布局
    private ImageView ivRight;
    //右侧第二图片布局
    private ImageView ivRightSecond;
    //右侧第三图片布局
    private ImageView ivRightThird;
    //中间标题布局
    private TextView tvTitle;
    //左侧文字布局
    private TextView tvLeft;
    //右侧文字布局
    private TextView tvRight;

    //中间标题
    private String mTitle;
    //左侧文字
    private String mLeftText;
    //右侧文字
    private String mRightText;
    //标题文字颜色
    private int mTitleColor = -1; //颜色没有负值，初始化设置为-1，使用默认颜色
    //左侧文字颜色
    private int mLeftTextColor = -1; //颜色没有负值，初始化设置为-1，使用默认颜色
    //右侧文字颜色
    private int mRightTextColor = -1; //颜色没有负值，初始化设置为-1，使用默认颜色
    //背景颜色
    private int mBackgroundColor = -1; //颜色没有负值，初始化设置为-1，使用默认颜色
    //左侧图标资源
    private Drawable mLeftImage;
    //左侧第二图标资源
    private Drawable mLeftSecondImage;
    //右侧图标资源
    private Drawable mRightImage;
    //右侧第二图标资源
    private Drawable mRightSecondImage;
    //右侧第三图标资源
    private Drawable mRightThirdImage;

    //是否显示左侧图片
    private boolean isShowLeftImage;
    //是否左侧第二图片
    private boolean isShowLeftSecondImage;
    //是否显示右侧第二图片
    private boolean isShowRightSecondImage;
    //是否显示右侧第三图片
    private boolean isShowRightThirdImage;
    //显示显示底部的分割线,默认为显示
    private boolean isShowBottomLine = true;
    //标题是否加粗
    private boolean isTitleBold;

    private View viewLine;

    private int maxHeight = DisplayUtil.dip2px(getContext(), 50); //最大高度
    private int minHeight = maxHeight / 3; //最小高度


    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(attrs, defStyleAttr);
        initView();
        initListener();
    }

    private void initAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.TopBar, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TopBar_title) {
                mTitle = a.getString(attr);
            } else if (attr == R.styleable.TopBar_leftText) {
                mLeftText = a.getString(attr);
            } else if (attr == R.styleable.TopBar_rightText) {
                mRightText = a.getString(attr);
            } else if (attr == R.styleable.TopBar_isShowLeftImage) {
                isShowLeftImage = a.getBoolean(attr, false);
            } else if (attr == R.styleable.TopBar_isShowLeftSecondImage) {
                isShowLeftSecondImage = a.getBoolean(attr, false);
            } else if (attr == R.styleable.TopBar_isShowRightSecondImage) {
                isShowRightSecondImage = a.getBoolean(attr, false);
            } else if (attr == R.styleable.TopBar_isShowRightThirdImage) {
                isShowRightThirdImage = a.getBoolean(attr, false);
            } else if (attr == R.styleable.TopBar_leftImage) {
                mLeftImage = a.getDrawable(attr);
            } else if (attr == R.styleable.TopBar_leftSecondImage) {
                mLeftSecondImage = a.getDrawable(attr);
            } else if (attr == R.styleable.TopBar_rightImage) {
                mRightImage = a.getDrawable(attr);
            } else if (attr == R.styleable.TopBar_rightSecondImage) {
                mRightSecondImage = a.getDrawable(attr);
            } else if (attr == R.styleable.TopBar_rightThirdImage) {
                mRightThirdImage = a.getDrawable(attr);
            } else if (attr == R.styleable.TopBar_leftTextColor) {
                mLeftTextColor = a.getColor(attr, ActivityCompat.getColor(mContext, R.color.public_tab_text_color_selected));
            } else if (attr == R.styleable.TopBar_rightTextColor) {
                mRightTextColor = a.getColor(attr, ActivityCompat.getColor(mContext, R.color.public_tab_text_color_selected));
            } else if (attr == R.styleable.TopBar_isShowBottomLine) {
                isShowBottomLine = a.getBoolean(attr, true);
            } else if (attr == R.styleable.TopBar_isTitleBold) {
                isTitleBold = a.getBoolean(attr, false);
            } else if (attr == R.styleable.TopBar_titleColor) {
                mTitleColor = a.getColor(attr, ActivityCompat.getColor(mContext, R.color.public_custom_color_text_main));
            } else if (attr == R.styleable.TopBar_backgroundColor) {
                mBackgroundColor = a.getColor(attr, ActivityCompat.getColor(mContext, R.color.public_white));
            }
        }
        a.recycle();
    }

    private void initView() {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout mMainContainer = (RelativeLayout) mInflater.inflate(R.layout.public_topbar, null);

        menu = mMainContainer.findViewById(R.id.menu);
        ivLeft = mMainContainer.findViewById(R.id.iv_left);
        ivLeftSecond = mMainContainer.findViewById(R.id.iv_left_second);
        tvLeft = mMainContainer.findViewById(R.id.tv_left);
        tvTitle = mMainContainer.findViewById(R.id.tv_title);
        tvRight = mMainContainer.findViewById(R.id.tv_right);
        ivRight = mMainContainer.findViewById(R.id.iv_right);
        ivRightSecond = mMainContainer.findViewById(R.id.iv_right_second);
        ivRightThird = mMainContainer.findViewById(R.id.iv_right_third);
        viewLine = mMainContainer.findViewById(R.id.line_view_topbar);

        this.addView(mMainContainer);

        if (!StringUtil.isEmpty(mTitle)) {
            setTitleText(mTitle);
        }
        if (isTitleBold) {
            tvTitle.getPaint().setFakeBoldText(true);//加粗
        }
        if (!StringUtil.isEmpty(mLeftText)) {
            setLeftText(mLeftText);
        }
        if (!StringUtil.isEmpty(mRightText)) {
            setRightText(mRightText);
        }

        if (mBackgroundColor != -1) {
            setBackgroundColor(mBackgroundColor);
        }

        if (mTitleColor != -1) {
            setTitleColor(mTitleColor);
        }

        if (mLeftTextColor != -1) {
            setLeftTextColor(mLeftTextColor);
        }

        if (mRightTextColor != -1) {
            setRightTextColor(mRightTextColor);
        }

        setLeftImage(mLeftImage);

        setLeftSecondImage(mLeftSecondImage);

        setRightImage(mRightImage);

        setRightSecondImage(mRightSecondImage);

        setRightThirdImage(mRightThirdImage);

        ivLeft.setVisibility((isShowLeftImage || mLeftImage != null) ? VISIBLE : GONE);
        ivLeftSecond.setVisibility((isShowLeftSecondImage) ? VISIBLE : GONE);
        ivRightSecond.setVisibility((isShowRightSecondImage) ? VISIBLE : GONE);
        ivRightThird.setVisibility((isShowRightThirdImage) ? VISIBLE : GONE);
        viewLine.setVisibility(isShowBottomLine ? VISIBLE : GONE);
    }

    private void initListener() {
        menu.setOnClickListener(v -> {
            ValueAnimator animator = ValueAnimator.ofInt(getHeight(), maxHeight);
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(300 * (maxHeight - getHeight()) / maxHeight).start();
            animator.addUpdateListener(animation -> {
                int dy = getHeight() - (int) animation.getAnimatedValue();
                setScrollY(dy * 5); //5倍速率
            });
        });
    }

    /**
     * ----------------设置背景--------------------
     */

    public void setBackgroundColor(int color) {
        menu.setBackgroundColor(color);
        ivLeft.setBackground(null);
        ivLeftSecond.setBackground(null);
        tvLeft.setBackground(null);
        ivRight.setBackground(null);
        ivRightSecond.setBackground(null);
        ivRightThird.setBackground(null);
        tvRight.setBackground(null);
    }

    public void setBackgroundBitmap(Bitmap bitmap) {
        menu.setBackground(new BitmapDrawable(getResources(), bitmap));
        ivLeft.setBackground(null);
        ivLeftSecond.setBackground(null);
        tvLeft.setBackground(null);
        ivRight.setBackground(null);
        ivRightSecond.setBackground(null);
        ivRightThird.setBackground(null);
        tvRight.setBackground(null);
    }


    /**
     * ----------------设置标题--------------------
     */
    public void setTitleText(String title) {
        mTitle = title;
        tvTitle.setText(mTitle);
    }

    public void setTitleTextSize(float size) {
        tvTitle.setTextSize(size);
    }

    public void setTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    /**
     * -----------------设置左边文字---------------------
     */
    public void setLeftText(String text) {
        tvLeft.setText(text);
        tvLeft.setVisibility(View.VISIBLE);
    }

    public TextView getLeftTextView() {
        return tvLeft;
    }

    public void setLeftTextVisible(boolean isVisible) {
        tvLeft.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void setLeftTextColor(int color) {
        tvLeft.setTextColor(color);
    }

    /**
     * ------------------设置右边文字---------------------
     */
    public void setRightText(String text) {
        tvRight.setText(text);
        tvRight.setVisibility(View.VISIBLE);
    }

    public void setRightTextViewEnable(boolean enable) {
        tvRight.setEnabled(enable);
        tvRight.setVisibility(View.VISIBLE);
    }

    public void setRightTextColor(int color) {
        tvRight.setTextColor(color);
    }

    public TextView getRightTextView() {
        return tvRight;
    }

    public void setRightDrawable(int res) {
        Drawable drawable = getResources().getDrawable(res);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvRight.setCompoundDrawables(null, null, drawable, null);
        tvRight.setCompoundDrawablePadding(DisplayUtil.dip2px(mContext, 3));
    }

    public void setRightTextVisiable(boolean isVisible) {
        tvRight.setVisibility(isVisible ? VISIBLE : GONE);
    }

    /**
     * ---------------设置左边图片-----------------
     */
    public void setLeftImage(int resId) {
        ivLeft.setImageResource(resId);
        ivLeft.setVisibility(VISIBLE);
    }

    public void setLeftImage(Drawable drawable) {
        if (drawable != null) {
            ivLeft.setImageDrawable(drawable);
            ivLeft.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftImage(Bitmap bitmap) {
        ivLeft.setImageBitmap(bitmap);
        ivLeft.setVisibility(View.VISIBLE);
    }

    public void setLeftImageVisiable(boolean isVisible) {
        ivLeft.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void setLeftImageSize(int widthPx, int heightPx) {
        if (ivLeft != null) {
            Log.d(TAG, "setLeftImageSize() called with: widthPx = [" + widthPx + "], heightPx = [" + heightPx + "]");
            ViewGroup.LayoutParams layoutParams = ivLeft.getLayoutParams();
            layoutParams.height = widthPx;
            layoutParams.width = heightPx;
            ivLeft.setLayoutParams(layoutParams);
        }
    }

    public void setLeftImagePadding(int left, int top, int right, int bottom) {
        if (ivLeft != null) {
            Log.d(TAG, "setLeftImagePadding() called with: left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
            ivLeft.setPadding(left, top, right, bottom);
        }
    }

    public ImageView getLeftImageView() {
        return ivLeft;
    }

    /**
     * ---------------------设置左边第二图片----------------------
     */
    public void setLeftSecondImage(Drawable drawable) {
        if (drawable != null) {
            ivLeftSecond.setImageDrawable(drawable);
            ivLeftSecond.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftSecondImage(int resId) {
        ivLeftSecond.setImageResource(resId);
        ivLeftSecond.setVisibility(View.VISIBLE);
    }

    public void setLeftSecondImageVisible(boolean isShow) {
        ivLeftSecond.setVisibility(isShow ? VISIBLE : GONE);
    }

    /**
     * ------------------设置右边图片------------------------
     */
    public void setRightImage(int resId) {
        ivRight.setImageResource(resId);
        ivRight.setVisibility(View.VISIBLE);
    }

    public void setRightImage(Drawable drawable) {
        if (drawable != null) {
            ivRight.setImageDrawable(drawable);
            ivRight.setVisibility(View.VISIBLE);
        }
    }

    public void setRightImage(Bitmap bmp) {
        ivRight.setImageBitmap(bmp);
        ivRight.setVisibility(View.VISIBLE);
    }

    public void setRightImageVisible(boolean isShow) {
        ivRight.setVisibility(isShow ? VISIBLE : GONE);
    }

    public ImageView getRightImageView() {
        return ivRight;
    }


    /**
     * ---------------------设置右边第二图片----------------------
     */
    public void setRightSecondImage(Drawable drawable) {
        if (drawable != null) {
            ivRightSecond.setImageDrawable(drawable);
            ivRightSecond.setVisibility(View.VISIBLE);
        }
    }

    public void setRightSecondImage(int resId) {
        ivRightSecond.setImageResource(resId);
        ivRightSecond.setVisibility(View.VISIBLE);
    }

    public void setRightSecondImageVisible(boolean isShow) {
        ivRightSecond.setVisibility(isShow ? VISIBLE : GONE);
    }

    /**
     * ----------------------设置右边第三图片-------------------------
     */
    public void setRightThirdImage(Drawable drawable) {
        if (drawable != null) {
            ivRightThird.setImageDrawable(drawable);
            ivRightThird.setVisibility(View.VISIBLE);
        }
    }

    public void setRightThirdImage(int resId) {
        ivRightThird.setImageResource(resId);
        ivRightThird.setVisibility(View.VISIBLE);
    }

    public void setRightThirdImageVisible(boolean isShow) {
        ivRightThird.setVisibility(isShow ? VISIBLE : GONE);
    }

    public boolean isRightThirdImageVisible() {
        return ivRightThird.getVisibility() == VISIBLE;
    }


    /**
     * ---------------------设置按钮点击监听事件-----------------------
     */
    public void setLeftImageClickListener(OnClickListener listener) {
        ivLeft.setOnClickListener(listener);
    }

    public void setLeftSecondImageClickListener(OnClickListener listener) {
        ivLeftSecond.setOnClickListener(listener);
    }

    public void setLeftTextClickListener(OnClickListener listener) {
        tvLeft.setOnClickListener(listener);
    }

    public void setRightTextClickListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    public void setRightImageClickListener(OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }

    public void setRightImageLongClickListener(OnLongClickListener listener) {
        ivRight.setOnLongClickListener(listener);
    }

    public void setRightSecondImageClickListener(OnClickListener listener) {
        ivRightSecond.setOnClickListener(listener);
    }

    public void setRightThirdImageClickListener(OnClickListener listener) {
        ivRightThird.setOnClickListener(listener);
    }

    /**
     * --------------------------设置图片透明度--------------------------------
     *
     * @param alpha
     */
    public void setImageAlpha(float alpha) {
        if (ivLeft.getVisibility() == VISIBLE || ivLeft.getAlpha() <= 0.1) {
            ivLeft.setAlpha(alpha);
            if (alpha <= 0.1) {
                ivLeft.setVisibility(GONE);
            } else {
                ivLeft.setVisibility(VISIBLE);
            }
        }
        if (ivRight.getVisibility() == VISIBLE || ivRight.getAlpha() <= 0.1) {
            ivRight.setAlpha(alpha);
            if (alpha <= 0.1) {
                ivRight.setVisibility(GONE);
            } else {
                ivRight.setVisibility(VISIBLE);
            }
        }
        if (ivRightSecond.getVisibility() == VISIBLE || ivRightSecond.getAlpha() <= 0.1) {
            ivRightSecond.setAlpha(alpha);
            if (alpha <= 0.1) {
                ivRightSecond.setVisibility(GONE);
            } else {
                ivRightSecond.setVisibility(VISIBLE);
            }
        }
        if (ivRightThird.getVisibility() == VISIBLE || ivRightThird.getAlpha() <= 0.1) {
            ivRightThird.setAlpha(alpha);
            if (alpha <= 0.1) {
                ivRightThird.setVisibility(GONE);
            } else {
                ivRightThird.setVisibility(VISIBLE);
            }
        }
    }


    public void setScrollY(int dy) {
        //topBar随webView滚动改变高度，高度范围[minHeight, maxHeight]
        int currentHeight = getHeight();
        if ((currentHeight == maxHeight && dy < 0) || (currentHeight == minHeight && dy > 0)) {
            return;
        }
        currentHeight -= dy / 5; //变化速率降低到1/5
        if (currentHeight <= minHeight) {
            currentHeight = minHeight;
        } else if (currentHeight >= maxHeight) {
            currentHeight = maxHeight;
        }
        RelativeLayout.LayoutParams pra = (RelativeLayout.LayoutParams) menu.getLayoutParams();
        pra.height = currentHeight;
        menu.setLayoutParams(pra);
        //图标随topBar高度[2*minHeight, maxHeight]实现透明度[0.0, 1.0]
        float alpha = ((float) currentHeight - (float) minHeight * 2) / ((float) maxHeight - (float) minHeight * 2);
        setImageAlpha(alpha);
        //字体大小随topBar高度[minHeight, maxHeight]实现大小[10sp, 16sp]
        float size = ((float) currentHeight - (float) minHeight) * 6 / ((float) maxHeight - (float) minHeight) + 10;
        setTitleTextSize(size);
    }

}

