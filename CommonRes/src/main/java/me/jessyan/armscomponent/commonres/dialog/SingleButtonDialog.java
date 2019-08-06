package me.jessyan.armscomponent.commonres.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.jessyan.armscomponent.commonres.R;


/**
 * 单按钮/可隐藏按钮/可隐藏标题Dialog
 */

public class SingleButtonDialog extends Dialog {
    private Context mContext;
    /*** 所有Layout */
    private RelativeLayout mDialogLayout;
    /*** 标题 */
    private TextView mDialogTitle;
    /*** 内容 */
    private TextView mDialogContent;
    /*** 按钮 */
    private TextView mDialogButton;

    private View mLine;

    public SingleButtonDialog(Context context) {
        super(context, R.style.public_dialog);
        this.mContext = context;
        setContentView(R.layout.public_dialog_single_button);
        initView(true, true);
    }

    public SingleButtonDialog(Context context, boolean isShowButton, boolean isShowTitle) {
        super(context, R.style.public_dialog);
        this.mContext = context;
        setContentView(R.layout.public_dialog_single_button);
        initView(isShowButton, isShowTitle);
    }

    private void initView(boolean isShowButton, boolean isShowTitle) {
        mDialogLayout = findViewById(R.id.single_dialog_layout);
        mDialogTitle = findViewById(R.id.single_dialog_title);
        mDialogContent = findViewById(R.id.single_dialog_content);
        mDialogButton = findViewById(R.id.single_dialog_button);
        mLine = findViewById(R.id.view_btn_top_line);
        mDialogTitle.setVisibility(isShowTitle ? View.VISIBLE : View.GONE);
        mDialogButton.setVisibility(isShowButton ? View.VISIBLE : View.GONE);
    }

    public interface DialogButtonClickListener {
        void onClick(View view);
    }

    public interface DialogContentClickListener {
        void onClick(View view);
    }

    /*** 设置弹窗单个按钮点击事件 */
    public void setDialogButtonClickListener(final DialogButtonClickListener clickListener) {
        mDialogButton.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(v);
                dismiss();
            }
        });
    }

    /*** 设置弹窗内容部分点击事件 */
    public void setDialogContentClickListener(final DialogContentClickListener clickListener) {
        mDialogContent.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(v);
                dismiss();
            }
        });
    }

    /*** 设置弹窗标题文字 */
    public void setDialogTitleText(String str) {
        mDialogTitle.setText(str);
    }

    /*** 设置弹窗标题文字大小 */
    public void setDialogTitleTextSize(float size) {
        mDialogTitle.setTextSize(size);
    }

    /*** 设置弹窗内容消息文字 */
    public void setDialogContentText(String str) {
        mDialogContent.setText(str);
    }

    /*** 设置弹窗内容消息文字大小 */
    public void setDialogContentTextSize(float size) {
        mDialogContent.setTextSize(size);
    }

    /*** 设置弹窗单个按钮文字 */
    public void setDialogButtonText(String str) {
        mDialogButton.setText(str);
    }

    /*** 设置弹窗单个按钮文字大小 */
    public void setDialogButtonTextSize(float size) {
        mDialogButton.setTextSize(size);
    }

    public void setDialogContentGravity(int gravity){
        mDialogContent.setGravity(gravity);
    }

    public void setDialogContentTextColor(@ColorInt int color){
        mDialogContent.setTextColor(color);
    }

    public void setDialogTitleColor(@ColorInt int color){
        mDialogTitle.setTextColor(color);
    }

    public void setDialogContentPadding(int left, int top, int right, int bottom){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int leftPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,left,displayMetrics);
        int topPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,top, displayMetrics);
        int rightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,right, displayMetrics);
        int bottomPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,bottom, displayMetrics);
        mDialogContent.setPadding(leftPx, topPx, rightPx, bottomPx);
    }
    public void setDialogLayoutWidthHeight(int width, int height){
        ViewGroup.LayoutParams layoutParams = mDialogLayout.getLayoutParams();
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, mContext.getResources().getDisplayMetrics());
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, mContext.getResources().getDisplayMetrics());
        mDialogLayout.setLayoutParams(layoutParams);
    }

    /**
     * 底部按钮与内容部分的分割线
     * @param visibility
     */
    public void setButtonContentDividerVisibility(int visibility){
        mLine.setVisibility(visibility);
    }

    /**
     * 设置按钮文本颜色
     * @param color
     */
    public void setDialogButtonTextColor(@ColorInt int color){
        mDialogButton.setTextColor(color);
    }
}
