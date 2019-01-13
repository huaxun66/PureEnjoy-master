package com.watson.pureenjoy.music.http.entity.recommend;

/**
 * Created by wm on 2016/7/28.
 */
public class RecommendAlbumInfo {


    /**
     * desc : 祁隆
     * pic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_1469672285aa1239d0e00543d967a138198f6ab19c.jpg
     * type_id : 268081987
     * type : 2
     * title : 惦记
     * tip_type : 3
     * author : 祁隆
     */

    private String desc;
    private String pic;
    private String type_id;
    private int type;
    private String title;
    private int tip_type;
    private String author;
    private String widepic;
    private String program_periods;
    private String program_title;

    public String getDesc() {
        return desc;
    }

    public String getPic() {
        return pic;
    }

    public String getType_id() {
        return type_id;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getTip_type() {
        return tip_type;
    }

    public String getAuthor() {
        return author;
    }

    public String getWidepic() {
        return widepic;
    }

    public String getProgram_periods() {
        return program_periods;
    }

    public String getProgram_title() {
        return program_title;
    }
}
