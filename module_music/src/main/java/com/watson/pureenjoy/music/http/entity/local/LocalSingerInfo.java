package com.watson.pureenjoy.music.http.entity.local;

import android.os.Parcel;
import android.os.Parcelable;

public class LocalSingerInfo implements Parcelable {

    private String name;
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

    @Override
    public int hashCode() {
        String code = name + count;
        return code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        LocalSingerInfo info = (LocalSingerInfo) (obj);
        return info.getName().equals(name) && info.getCount() == count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.count);
    }

    public LocalSingerInfo(String name, int count) {
        this.name = name;
        this.count = count;
    }

    protected LocalSingerInfo(Parcel in) {
        this.name = in.readString();
        this.count = in.readInt();
    }

    public static final Parcelable.Creator<LocalSingerInfo> CREATOR = new Parcelable.Creator<LocalSingerInfo>() {
        @Override
        public LocalSingerInfo createFromParcel(Parcel source) {
            return new LocalSingerInfo(source);
        }

        @Override
        public LocalSingerInfo[] newArray(int size) {
            return new LocalSingerInfo[size];
        }
    };
}


