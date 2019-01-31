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
