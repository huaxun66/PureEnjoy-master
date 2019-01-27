package com.watson.pureenjoy.music.http.entity.rank;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RankInfo implements Parcelable {
    private String name;
    private int type;
    private int count;
    private String comment;
    private String web_url;
    private String pic_s192;
    private String pic_s444;
    private String pic_s260;
    private String pic_s210;
    private String color;
    private String bg_color;
    private String bg_pic;
    private List<RankSongInfo> content;

    //详情页请求携带的字段
    private String billboard_type;
    private String billboard_no;
    private String update_date;
    private String billboard_songnum;
    private int havemore;
    private String pic_s640;

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public String getComment() {
        return comment;
    }

    public String getWeb_url() {
        return web_url;
    }

    public String getPic_s192() {
        return pic_s192;
    }

    public String getPic_s444() {
        return pic_s444;
    }

    public String getPic_s260() {
        return pic_s260;
    }

    public String getPic_s210() {
        return pic_s210;
    }

    public String getColor() {
        return color;
    }

    public String getBg_color() {
        return bg_color;
    }

    public String getBg_pic() {
        return bg_pic;
    }

    public List<RankSongInfo> getContent() {
        return content;
    }

    public String getBillboard_type() {
        return billboard_type;
    }

    public String getBillboard_no() {
        return billboard_no;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public String getBillboard_songnum() {
        return billboard_songnum;
    }

    public int getHavemore() {
        return havemore;
    }

    public String getPic_s640() {
        return pic_s640;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.type);
        dest.writeInt(this.count);
        dest.writeString(this.comment);
        dest.writeString(this.web_url);
        dest.writeString(this.pic_s192);
        dest.writeString(this.pic_s444);
        dest.writeString(this.pic_s260);
        dest.writeString(this.pic_s210);
        dest.writeString(this.color);
        dest.writeString(this.bg_color);
        dest.writeString(this.bg_pic);
    }

    public RankInfo() {
    }

    protected RankInfo(Parcel in) {
        this.name = in.readString();
        this.type = in.readInt();
        this.count = in.readInt();
        this.comment = in.readString();
        this.web_url = in.readString();
        this.pic_s192 = in.readString();
        this.pic_s444 = in.readString();
        this.pic_s260 = in.readString();
        this.pic_s210 = in.readString();
        this.color = in.readString();
        this.bg_color = in.readString();
        this.bg_pic = in.readString();
    }

    public static final Creator<RankInfo> CREATOR = new Creator<RankInfo>() {
        @Override
        public RankInfo createFromParcel(Parcel source) {
            return new RankInfo(source);
        }

        @Override
        public RankInfo[] newArray(int size) {
            return new RankInfo[size];
        }
    };
}
