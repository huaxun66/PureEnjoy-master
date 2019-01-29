package com.watson.pureenjoy.app.app.utils.crash;

import android.os.Environment;

import com.jess.arms.utils.LogUtils;
import com.watson.pureenjoy.app.app.utils.SystemUtil;

import java.io.File;
import java.util.Calendar;

public class CrashUtils {

    public static final File CRASH_LOG_SD_CARD_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ SystemUtil.APP_DIR, "crash");

    public static boolean hasSDCardCrashFile() {
        if (!checkSDCardCrashDir()) {
            return false;
        }
        try {
            File[] crashLogs = CRASH_LOG_SD_CARD_DIR.listFiles();
            return crashLogs != null && crashLogs.length > 0;
        } catch (Exception e) {
            LogUtils.debugInfo("CrashUtils", "delete crash log error." + e.getMessage());
            return false;
        }
    }

    public static File[] getAllSDCardCrashLog() {
        if (!checkSDCardCrashDir()) {
            return null;
        }

        return CRASH_LOG_SD_CARD_DIR.listFiles();
    }

    private static boolean checkSDCardCrashDir() {
        return CRASH_LOG_SD_CARD_DIR.exists() && CRASH_LOG_SD_CARD_DIR.isDirectory();
    }

    public static File createFileIfNotExist(String rootDirectory, String fileName) {

        File file = new File(rootDirectory, fileName);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLogTime() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer bf = new StringBuffer();
        bf.append(getLogDate());
        bf.append("");
        bf.append(calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : calendar.get(Calendar.HOUR_OF_DAY));
        bf.append(calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE));
        bf.append(calendar.get(Calendar.SECOND) < 10 ? "0" + calendar.get(Calendar.SECOND) : calendar.get(Calendar.SECOND));
        return bf.toString();
    }

    public static String getLogDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        StringBuffer bf = new StringBuffer();
        bf.append(calendar.get(Calendar.YEAR));
        bf.append(calendar.get(Calendar.MONTH) < 9 ? "0" + (calendar.get(Calendar.MONTH) + 1) : (calendar.get(Calendar.MONTH) + 1));
        bf.append(calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : calendar.get(Calendar.DAY_OF_MONTH));
        return bf.toString();
    }
}
