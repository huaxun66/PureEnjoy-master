package com.watson.pureenjoy.music.http.entity.local;

import android.os.Parcel;
import android.os.Parcelable;


public class LocalAlbumInfo implements Parcelable {
    private String name;
    private String singer;
    private String thumbs;
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getThumbs() {
        return thumbs;
    }

    public void setThumbs(String thumbs) {
        this.thumbs = thumbs;
    }

    @Override
    public int hashCode() {
        String code = name + singer + thumbs + count;
        return code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        LocalAlbumInfo info = (LocalAlbumInfo) (obj);
        return info.getName().equals(name) && info.getSinger().equals(singer) && info.getThumbs().equals(thumbs) && info.getCount() == count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.singer);
        dest.writeString(this.thumbs);
        dest.writeInt(this.count);
    }

    public LocalAlbumInfo(String name, String singer, String thumbs, int count) {
        this.name = name;
        this.singer = singer;
        this.thumbs = thumbs;
        this.count = count;
    }

    protected LocalAlbumInfo(Parcel in) {
        this.name = in.readString();
        this.singer = in.readString();
        this.thumbs = in.readString();
        this.count = in.readInt();
    }

    public static final Parcelable.Creator<LocalAlbumInfo> CREATOR = new Parcelable.Creator<LocalAlbumInfo>() {
        @Override
        public LocalAlbumInfo createFromParcel(Parcel source) {
            return new LocalAlbumInfo(source);
        }

        @Override
        public LocalAlbumInfo[] newArray(int size) {
            return new LocalAlbumInfo[size];
        }
    };
}