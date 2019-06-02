package com.watson.pureenjoy.music.http.entity.local;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class LocalFolderInfo implements Parcelable {

    public static String KEY_FOLDER_NAME = "folder_name";
    public static String KEY_FOLDER_PATH = "folder_path";
    public static final String KEY_FOLDER_SORT = "folder_sort";
    public static final String KEY_FOLDER_FILE_COUNT = "file_count";

    public String folder_name;
    public String folder_path;
    public String folder_sort;
    public int folder_count;

    // 用来创建自定义的Parcelable的对象
    public static Creator<LocalFolderInfo> CREATOR = new Creator<LocalFolderInfo>() {

        @Override
        public LocalFolderInfo createFromParcel(Parcel source) {
            LocalFolderInfo info = new LocalFolderInfo();
            Bundle bundle = source.readBundle();
            info.folder_name = bundle.getString(KEY_FOLDER_NAME);
            info.folder_path = bundle.getString(KEY_FOLDER_PATH);
            info.folder_sort = bundle.getString(KEY_FOLDER_SORT);
            info.folder_count = bundle.getInt(KEY_FOLDER_FILE_COUNT);
            return info;
        }

        @Override
        public LocalFolderInfo[] newArray(int size) {
            return new LocalFolderInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FOLDER_NAME, folder_name);
        bundle.putString(KEY_FOLDER_PATH, folder_path);
        bundle.putString(KEY_FOLDER_SORT, folder_sort);
        bundle.putInt(KEY_FOLDER_FILE_COUNT, folder_count);
        dest.writeBundle(bundle);
    }

}

