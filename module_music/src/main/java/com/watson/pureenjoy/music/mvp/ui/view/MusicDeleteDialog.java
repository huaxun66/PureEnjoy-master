package com.watson.pureenjoy.music.mvp.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.watson.pureenjoy.music.R;


public class MusicDeleteDialog extends Dialog {
    private Context mContext;
    private LinearLayout mDialogLayout;
    private CheckBox mCheckBox;
    private TextView mBtnCancel;
    private TextView mBtnDelete;


    public MusicDeleteDialog(Context context) {
        super(context, R.style.public_dialog);
        this.mContext = context;
        setContentView(R.layout.music_dialog_delete_music);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mDialogLayout =  findViewById(R.id.dialog_layout);
        mCheckBox =  findViewById(R.id.dialog_delete_cb);
        mBtnCancel = findViewById(R.id.cancel_btn);
        mBtnDelete = findViewById(R.id.delete_btn);
        ViewGroup.LayoutParams layoutParams = mDialogLayout.getLayoutParams();
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280, mContext.getResources().getDisplayMetrics());
        mDialogLayout.setLayoutParams(layoutParams);
    }

    public interface DialogButtonClickListener {
        void onDelete(boolean checked);
        void onCancel();
    }


    public void setDialogButtonClickListener(final DialogButtonClickListener clickListener) {
        mBtnDelete.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onDelete(mCheckBox.isChecked());
                dismiss();
            }
        });
        mBtnCancel.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onCancel();
                dismiss();
            }
        });
    }

}
