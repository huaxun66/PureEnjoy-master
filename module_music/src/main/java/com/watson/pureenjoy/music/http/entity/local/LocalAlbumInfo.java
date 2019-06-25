package com.watson.pureenjoy.music.http.entity.local;

import android.os.Parcel;
import android.os.Parcelable;


public class LocalAlbumInfo implements Parcelable {
    private String name;
    private String singer;
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
    @Override
    public int hashCode() {
        String code = name + singer + count;
        return code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        LocalAlbumInfo info = (LocalAlbumInfo) (obj);
        return info.getName().equals(name) && info.getSinger().equals(singer) && info.getCount() == count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.singer);
        dest.writeInt(this.count);
    }

    public LocalAlbumInfo() {
    }

    protected LocalAlbumInfo(Parcel in) {
        this.name = in.readString();
        this.singer = in.readString();
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