package com.watson.pureenjoy.music.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.watson.pureenjoy.music.R;

import me.jessyan.armscomponent.commonsdk.utils.DisplayUtil;
import me.jessyan.armscomponent.commonsdk.utils.FastBlurUtil;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

public class ImageUtil {

    public static Drawable getForegroundDrawable(Context context, String albumThumbs) {
        /*得到屏幕的宽高比，以便按比例切割图片一部分*/
        final float widthHeightSize = (float) (DisplayUtil.getScreenWidth(context) * 1.0 / DisplayUtil.getScreenHeight(context) * 1.0);
        Bitmap bitmap;
        if (StringUtil.isEmpty(albumThumbs)) {
            bitmap = getForegroundBitmap(context, R.drawable.music_sheet_bg);
        } else {
            bitmap = getForegroundBitmap(context, albumThumbs);
        }

        int cropBitmapWidth = (int) (widthHeightSize * bitmap.getHeight());
        int cropBitmapWidthX = (int) ((bitmap.getWidth() - cropBitmapWidth) / 2.0);

        /*切割部分图片*/
        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, cropBitmapWidthX, 0, cropBitmapWidth,
                bitmap.getHeight());
        /*缩小图片*/
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(cropBitmap, bitmap.getWidth() / 50, bitmap
                .getHeight() / 50, false);
        /*模糊化*/
        final Bitmap blurBitmap = FastBlurUtil.doBlur(scaleBitmap, 8, true);

        final Drawable foregroundDrawable = new BitmapDrawable(blurBitmap);
        /*加入灰色遮罩层，避免图片过亮影响其他控件*/
        foregroundDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        return foregroundDrawable;
    }

    private static Bitmap getForegroundBitmap(Context context, String albumThumbs) {
        int screenWidth = DisplayUtil.getScreenWidth(context);
        int screenHeight = DisplayUtil.getScreenHeight(context);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(albumThumbs, options);

        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        if (imageWidth < screenWidth && imageHeight < screenHeight) {
            return BitmapFactory.decodeFile(albumThumbs);
        }

        int sample = 2;
        int sampleX = imageWidth / screenWidth;
        int sampleY = imageHeight / screenHeight;

        if (sampleX > sampleY && sampleY > 1) {
            sample = sampleX;
        } else if (sampleY > sampleX && sampleX > 1) {
            sample = sampleY;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sample;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeFile(albumThumbs, options);
    }

    private static Bitmap getForegroundBitmap(Context context, int musicPicRes) {
        int screenWidth = DisplayUtil.getScreenWidth(context);
        int screenHeight = DisplayUtil.getScreenHeight(context);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(context.getResources(), musicPicRes, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        if (imageWidth < screenWidth && imageHeight < screenHeight) {
            return BitmapFactory.decodeResource(context.getResources(), musicPicRes);
        }

        int sample = 2;
        int sampleX = imageWidth / screenWidth;
        int sampleY = imageHeight / screenHeight;

        if (sampleX > sampleY && sampleY > 1) {
            sample = sampleX;
        } else if (sampleY > sampleX && sampleX > 1) {
            sample = sampleY;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sample;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeResource(context.getResources(), musicPicRes, options);
    }
}
