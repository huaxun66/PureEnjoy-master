package com.watson.pureenjoy.music.http.entity.sheet;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class SheetInfo implements Parcelable {

    private String listid;
    private String listenum;
    private String collectnum;
    private String title;
    private String pic_300;
    private String tag;
    private String desc;
    private String pic_w300;
    private String width;
    private String height;
    private String[] songIds;
    private String pic; //热门歌单使用
    //收藏使用
    private String id;
    private String songCount;

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getListenum() {
        return listenum;
    }

    public void setListenum(String listenum) {
        this.listenum = listenum;
    }

    public String getCollectnum() {
        return collectnum;
    }

    public void setCollectnum(String collectnum) {
        this.collectnum = collectnum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_300() {
        return pic_300;
    }

    public void setPic_300(String pic_300) {
        this.pic_300 = pic_300;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic_w300() {
        return pic_w300;
    }

    public void setPic_w300(String pic_w300) {
        this.pic_w300 = pic_w300;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String[] getSongIds() {
        return songIds;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSongCount() {
        return songCount;
    }

    public void setSongCount(String songCount) {
        this.songCount = songCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SheetInfo sheetInfo = (SheetInfo) o;
        return Objects.equals(id, sheetInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.listid);
        dest.writeString(this.listenum);
        dest.writeString(this.collectnum);
        dest.writeString(this.title);
        dest.writeString(this.pic_300);
        dest.writeString(this.tag);
        dest.writeString(this.desc);
        dest.writeString(this.pic_w300);
        dest.writeString(this.width);
        dest.writeString(this.height);
        dest.writeStringArray(this.songIds);
        dest.writeString(this.pic);
        dest.writeString(this.id);
        dest.writeString(this.songCount);
    }

    public SheetInfo() {
    }

    protected SheetInfo(Parcel in) {
        this.listid = in.readString();
        this.listenum = in.readString();
        this.collectnum = in.readString();
        this.title = in.readString();
        this.pic_300 = in.readString();
        this.tag = in.readString();
        this.desc = in.readString();
        this.pic_w300 = in.readString();
        this.width = in.readString();
        this.height = in.readString();
        this.songIds = in.createStringArray();
        this.pic = in.readString();
        this.id = in.readString();
        this.songCount = in.readString();
    }

    public static final Creator<SheetInfo> CREATOR = new Creator<SheetInfo>() {
        @Override
        public SheetInfo createFromParcel(Parcel source) {
            return new SheetInfo(source);
        }

        @Override
        public SheetInfo[] newArray(int size) {
            return new SheetInfo[size];
        }
    };
}
