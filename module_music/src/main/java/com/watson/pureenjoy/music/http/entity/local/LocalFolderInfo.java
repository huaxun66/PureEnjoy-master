package com.watson.pureenjoy.music.http.entity.local;

import android.os.Parcel;
import android.os.Parcelable;

public class LocalFolderInfo implements Parcelable {

    private String name;
    private String path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        String code = name + path + count ;
        return code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        LocalFolderInfo info = (LocalFolderInfo) (obj);
        return info.getName().equals(name) && info.getPath().equals(path) && info.getCount() == count ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeInt(this.count);
    }

    public LocalFolderInfo() {
    }

    protected LocalFolderInfo(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.count = in.readInt();
    }

    public static final Parcelable.Creator<LocalFolderInfo> CREATOR = new Parcelable.Creator<LocalFolderInfo>() {
        @Override
        public LocalFolderInfo createFromParcel(Parcel source) {
            return new LocalFolderInfo(source);
        }

        @Override
        public LocalFolderInfo[] newArray(int size) {
            return new LocalFolderInfo[size];
        }
    };
}
