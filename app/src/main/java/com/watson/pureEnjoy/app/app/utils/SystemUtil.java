package com.watson.pureenjoy.app.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

public class SystemUtil {
    /**
     * 有新的版本
     */
    public static final int HAS_NEW_VERSION = 100000;
    /**
     * 沒有新版本
     */
    public static final int NOT_HAS_NEW_VERSION = 0;

    /**
     * 应用语言
     **/
    public static final String MOA_LANGUAGE = "language"; //键
    public static final String MOA_LANGUAGE_AUTO = "0";//系统默认
    public static final String MOA_LANGUAGE_CHINESE = "1";//简体中文
    public static final String MOA_LANGUAGE_ENGLISH = "2";//英文

    /**
     * 字体大小
     **/
    public static final String MOA_FONT_SIZE = "FontSize"; //字体大小键
    public static final String MOA_FONT_SIZE_SMALL = "0";//小
    public static final String MOA_FONT_SIZE_NORMAIL = "1";//标准
    public static final String MOA_FONT_SIZE_BIG = "2";//大
    public static final String MOA_FONT_SIZE_VBIG = "3";//超大
    public static final String MOA_FONT_SIZE_SBIG = "4";//特大
    public static final int FONT_SIZE_SMALL_SP = 13; //小-13sp
    public static final int FONT_SIZE_NORMAIL_SP = 15; //标准-15sp
    public static final int FONT_SIZE_BIG_SP = 17; //大-17sp
    public static final int FONT_SIZE_VBIG_SP = 19; //超大-19sp
    public static final int FONT_SIZE_SBIG_SP = 21; //特大-21sp

    public static String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String APP_DIR = "/PureEnjoy/";
    public static final String PORTRAIT_DIR = "/.image/";
    public static String TEMP_DIR = SystemUtil.APP_DIR + ".sendPic/";
    public static String AUDIO_DIR = SystemUtil.APP_DIR + ".sendAudio/";
    public static final String PHOTO_DIR = "/.photo/"; // 存储IM好友头像
    public static final String GROUP_LOGO_DIR = "/pic/"; // 存储IM群组头像
    public static final String QRCODE_DIR = "/.photo/qrcode/"; // 存储二维码图片
    public static final String LOGO_DIR = "/logo/"; // 存储IM好友logo头像
    public static final String GROUP_DEFAULT_LOGO = "default_group.jpg"; // 群组成员单个默认logo头像名
    public static final String MEMBER_DEFAULT_LOGO = "default_member.jpg"; // 群组成员单个默认logo头像名
    public static final String GROUP_OF_ME_LOGO = "default_me.jpg"; // 群组有4个成员时默认logo头像像名
    public static String CHATBG_DIR = SystemUtil.APP_DIR + ".chatBgPic/"; // 存储聊天背景图片

    public static String LARGE_TXT_DIR = SystemUtil.APP_DIR + ".txt/"; // 大消息对应的文件存储路径

    // 临时文件夹
    public static String CACHE_PATH = "";

    // 版本升级文件夹
    public static String UPDATE_PATH = "";

    // 日志文件
    public static final String LogOcxFileName = "IMSClientLog.txt";
    public static final String LogHttpFileName = "HttpLog.txt";
    public static final String LogSipFileName = "SipLog.txt";
    public static final String LogMsrpFileName = "MsrpLog.txt";

    public static final long INTERVAL_TIME = 60 * 1000 * 5;

    /**
     * 用户头像
     */
    public static String PIC_NAME = "";
    public static String PIC_PATH = "";

    /*** 裁剪图片的宽和高 **/
    public static final int PIC_WIDTH = 76;
    public static final int PIC_HEIGHT = 76;

    /**
     * 修改最大头像长度为64K
     **/
    public static int MAX_PIC_LENGTH = 65536;// 12288;//32768;

    public static String DATE_PATTERN = "";
    public static String YESTODAY = "";
    public static final String LETTER_INDEX = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";

    public static String MONTH_FORMAT;
    public static String DAY_FORMAT;

    public static final String BIND_PHONE_FILE = "bindphone";

    /**
     * 聊天类型，群组
     */
    public static final int TYPE_IMPUBGROUP = 0;
    /**
     * 聊天类型，个人会话
     */
    public static final int TYPE_IMUSER = 1;
    /**
     * 聊天类型，聊天室2
     */
    public static final int TYPE_CHATROOM = 2;
    /**
     * 系统提示信息
     */
    public static final int TYPE_SYSTEM = 3;
    /**
     * 文件信息
     */
    public static final int TYPE_FILE = 4;
    /**
     * 公众号信息
     */
    public static final int TYPE_PUB_ACC = 5;

    // 国资委版本：聊天的好友最多250个
    public static final int MAX_CHAT_NUMBER = 250;
    public static final int MAX_COUNT_NUMBER = 300;
    // 发送图片-最多选择5张
    public static final int MAX_SELECTED_IMG = 5;
    // 发送图片-最多选择3张意见反馈
    public static final int MAX_SELECTED_IMG_FEEDBACK = 3;

    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input 字符串
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input)) {
            return true;
        }

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断给定字符串是否空白串。 若输入字符串为null或空字符串，返回true
     *
     * @param input 字符串
     * @return boolean
     */
    public static boolean isNullOrEmpty(String input) {
        if (input == null || input.trim().length() == 0) {
            return true;
        }

        return false;
    }


    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context ctx) {
        //添加简单权限验证，无权限直接返回null，作为临时处理方案
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
            if (tm != null) {
                return tm.getDeviceId();
            }
        }
        return "";
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;
    }


    /**
     * 打电话
     */
    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
