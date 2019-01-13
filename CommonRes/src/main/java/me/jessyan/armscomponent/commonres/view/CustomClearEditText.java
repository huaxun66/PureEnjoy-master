package me.jessyan.armscomponent.commonres.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * EditText with a clear button
 */
public class CustomClearEditText extends AppCompatEditText {
    private Drawable mRightDrawable;
    private boolean isHasFocus;
    private OnEditClickListener listener;

    public CustomClearEditText(Context context) {
        super(context);
        init();
    }
    public CustomClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        //getCompoundDrawables:
        //Returns drawables for the left, top, right, and bottom borders.
        Drawable [] drawables=this.getCompoundDrawables();

        //取得right位置的Drawable
        //即我们在布局文件中设置的android:drawableRight
        mRightDrawable=drawables[2];

        //设置焦点变化的监听
        this.setOnFocusChangeListener(new FocusChangeListenerImpl());
        //设置EditText文字变化的监听
        this.addTextChangedListener(new TextWatcherImpl());
        //初始化时让右边clean图标不可见
        setClearDrawableVisible(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                boolean isClean =(event.getX() > (getWidth() - getTotalPaddingRight()))&&
                        (event.getX() < (getWidth() - getPaddingRight()));
                if (isClean) {
                    setText("");
                    if (listener!=null){
                        listener.onClearEditor();
                    }
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    private class FocusChangeListenerImpl implements OnFocusChangeListener{
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isHasFocus=hasFocus;
            if (isHasFocus) {
                boolean isVisible=getText().toString().length()>=1;
                setClearDrawableVisible(isVisible);
            } else {
                setClearDrawableVisible(false);
            }
        }

    }

    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            boolean isVisible=getText().toString().length()>=1;
            setClearDrawableVisible(isVisible);
            /*if(StringUtils.isEmpty(s.toString())){
                setShakeAnimation();
            }*/
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,int count) {

        }

    }


    protected void setClearDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible) {
            rightDrawable = mRightDrawable;
        } else {
            rightDrawable = null;
        }
        //使用代码设置该控件left, top, right, and bottom处的图标
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],
                rightDrawable,getCompoundDrawables()[3]);
    }

    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    public Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public void setOnEditClickListener(OnEditClickListener onEditClickListener){
        this.listener = onEditClickListener;
    }

    public interface OnEditClickListener{

        void onClearEditor();

    }
}
