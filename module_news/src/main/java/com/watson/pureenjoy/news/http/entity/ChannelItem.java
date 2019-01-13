package com.watson.pureenjoy.news.http.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class ChannelItem implements Parcelable {
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_CONTENT = 2;

    private String name;
    private String typeId;
    //类型:1-标题 2-普通栏目(默认)
    private int type = 2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return (typeId == null) ? 0 : typeId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof ChannelItem))
            return false;

        ChannelItem other = (ChannelItem) obj;
        if (!typeId.equals(other.typeId))
            return false;

        return true;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.typeId);
    }

    public ChannelItem() {
    }

    protected ChannelItem(Parcel in) {
        this.name = in.readString();
        this.typeId = in.readString();
    }

    public static final Creator<ChannelItem> CREATOR = new Creator<ChannelItem>() {
        @Override
        public ChannelItem createFromParcel(Parcel source) {
            return new ChannelItem(source);
        }

        @Override
        public ChannelItem[] newArray(int size) {
            return new ChannelItem[size];
        }
    };
}
