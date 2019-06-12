package com.watson.pureenjoy.music.app;

public class MusicConstants {
    public static final String REQUEST_SUCCESS = "22000";

    public static final String HEADER_AGENT = "User-Agent: Mozilla/5.0 (Windows;U;Windows NT 5.1;en-US;rv:0.9.4)";

    public static final String TAG_SELECTED = "SelectedTag";
    public static final String TAG_ALL = "全部歌单";
    public static final String TAG_CHINESE = "华语";
    public static final String TAG_ROCK = "摇滚";
    public static final String TAG_BALLAD = "民谣";

    public static final String TAG_CATEGORY_RECOMMEND = "精选推荐";
    public static final String TAG_CATEGORY_LANGUAGE = "语种";
    public static final String TAG_CATEGORY_STYLE = "风格";
    public static final String TAG_CATEGORY_EMOTION = "情感";
    public static final String TAG_CATEGORY_SCENCE = "场景";
    public static final String TAG_CATEGORY_THEME = "主题";

    public static final String SHEET_INFO = "sheetInfo";
    public static final String RANK_INFO = "rankInfo";
    public static final String ALBUM_INFO = "albumInfo";

    public static final String RANK_HOT = "热歌榜";
    public static final String RANK_NEW = "新歌榜";
    public static final String RANK_NET = "网络歌曲榜";
    public static final String RANK_FILM = "影视金曲榜";
    public static final String RANK_CLASSIC = "经典老歌榜";
    public static final String RANK_EU_UK = "欧美金曲榜";

    public static final String RANK_UPDATE_REALTIME = "实时展现";
    public static final String RANK_UPDATE_EVERYDAY = "每日更新";

    public static final String RANK_REQUEST_FIELD = "song_id,title,author,album_title,pic_big,pic_small,havehigh,all_rate,charge,has_mv_mobile,learn,song_source,korean_bb_song";

    public static final String ANDROID = "android";
    public static final String VERSION = "5.6.5.6";
    public static final String JSON = "json";

    //handle常量
    public static final int SCAN_ERROR = 0;
    public static final int SCAN_COMPLETE = 1;
    public static final int SCAN_UPDATE = 2;
    public static final int SCAN_NO_MUSIC = 3;

    //SharedPreferences key常量
    public static final String KEY_ID = "id";
    public static final String KEY_PATH = "path";
    public static final String KEY_MODE = "mode";
    public static final String KEY_LIST = "list";
    public static final String KEY_LIST_ID = "list_id";
    public static final String KEY_CURRENT = "current";
    public static final String KEY_DURATION = "duration";

    public static final String KEY_TYPE = "type";
    public static final String KEY_TITLE = "title";

    //歌曲列表常量
    public static final int LIST_ALLMUSIC = -1;
    public static final int LIST_MYLOVE = 10000;
    public static final int LIST_LASTPLAY = 10001;
    public static final int LIST_DOWNLOAD = 10002;
    public static final int LIST_MYPLAY = 10003;    //我的歌单列表
    public static final int LIST_PLAYLIST = 10004;	//歌单音乐列表
    public static final int LIST_SINGER = 10005;	//歌手
    public static final int LIST_ALBUM = 10006;	    //专辑
    public static final int LIST_FOLDER = 10007;	//文件夹

    public static final int ACTIVITY_LOCAL = 20; //本地音乐
    public static final int ACTIVITY_RECENTPLAY = 21;//最近播放
    public static final int ACTIVITY_MYLOVE = 22; //我喜爱
    public static final int ACTIVITY_MYLIST = 24;//我的歌单

    //播放模式
    public static final int PLAYMODE_SEQUENCE = -1;
    public static final int PLAYMODE_SINGLE_REPEAT = 1;
    public static final int PLAYMODE_RANDOM = 2;

    public static final String PLAYMODE_SEQUENCE_TEXT = "顺序播放";
    public static final String PLAYMODE_RANDOM_TEXT = "随机播放";
    public static final String PLAYMODE_SINGLE_REPEAT_TEXT = "单曲循环";

    public static final String METHOD_SHEET = "baidu.ting.diy.gedan";
    public static final String METHOD_SHEET_SEARCH = "baidu.ting.diy.search";
    public static final String METHOD_SHEET_TAG = "baidu.ting.diy.gedanCategory";
    public static final String METHOD_HOT_SHEET = "baidu.ting.diy.getHotGeDanAndOfficial";
    public static final String METHOD_SHEET_DETAIL = "baidu.ting.diy.gedanInfo";
    public static final String METHOD_RANK = "baidu.ting.billboard.billCategory";
    public static final String METHOD_RANK_DETAIL = "baidu.ting.billboard.billList";
    public static final String METHOD_ALBUM = "baidu.ting.plaza.getRecommendAlbum";
    public static final String METHOD_ALBUM_DETAIL = "baidu.ting.album.getAlbumInfo";
    public static final String METHOD_RECOMMEND_SONG = "baidu.ting.song.getEditorRecommend";



}
