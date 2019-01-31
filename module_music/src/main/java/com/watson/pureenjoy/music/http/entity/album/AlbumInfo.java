package com.watson.pureenjoy.music.http.entity.album;

import android.os.Parcel;
import android.os.Parcelable;

import com.watson.pureenjoy.music.http.entity.artist.ArtistInfo;

import java.util.List;

public class AlbumInfo implements Parcelable {

    private String album_id;
    private String title;
    private String publishcompany;
    private String country;
    private String songs_total;
    private String pic_small;
    private String pic_big;
    private String pic_radio;
    private String artist_id;
    private String all_artist_id;
    private String author;
    private String publishtime;
    private String resource_type_ext;
    private String price;
    private String is_recommend_mis;
    private String is_first_publish;
    private String is_exclusive;
    private String all_artist_ting_uid;
    private String hot;
    private String styles;
    private String info;
    private String is_new;

    //详情页请求携带的字段
    private String prodcompany;
    private String language;
    private String style_id;
    private String artist_ting_uid;
    private String gender;
    private String area;
    private int favorites_num;
    private int recommend_num;
    private int collect_num;
    private int share_num;
    private int comment_num;
    private String pic_s500;
    private String pic_s1000;
    private String ai_presale_flag;
    private String biaoshi;
    private String file_duration;
    private String buy_url;
    private String avatar_small;
    private List<ArtistInfo> artist_list;
    private int my_num;
    private int song_sale;


    public String getAlbum_id() {
        return album_id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishcompany() {
        return publishcompany;
    }

    public String getProdcompany() {
        return prodcompany;
    }

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public String getSongs_total() {
        return songs_total;
    }

    public String getInfo() {
        return info;
    }

    public String getStyles() {
        return styles;
    }

    public String getStyle_id() {
        return style_id;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public String getArtist_ting_uid() {
        return artist_ting_uid;
    }

    public String getAll_artist_ting_uid() {
        return all_artist_ting_uid;
    }

    public String getGender() {
        return gender;
    }

    public String getArea() {
        return area;
    }

    public String getPic_small() {
        return pic_small;
    }

    public String getPic_big() {
        return pic_big;
    }

    public String getHot() {
        return hot;
    }

    public int getFavorites_num() {
        return favorites_num;
    }

    public int getRecommend_num() {
        return recommend_num;
    }

    public int getCollect_num() {
        return collect_num;
    }

    public int getShare_num() {
        return share_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public String getArtist_id() {
        return artist_id;
    }

    public String getAll_artist_id() {
        return all_artist_id;
    }

    public String getPic_radio() {
        return pic_radio;
    }

    public String getPic_s500() {
        return pic_s500;
    }

    public String getPic_s1000() {
        return pic_s1000;
    }

    public String getAi_presale_flag() {
        return ai_presale_flag;
    }

    public String getResource_type_ext() {
        return resource_type_ext;
    }

    public String getBiaoshi() {
        return biaoshi;
    }

    public String getFile_duration() {
        return file_duration;
    }

    public String getBuy_url() {
        return buy_url;
    }

    public String getAvatar_small() {
        return avatar_small;
    }

    public List<ArtistInfo> getArtist_list() {
        return artist_list;
    }

    public String getPrice() {
        return price;
    }

    public int getMy_num() {
        return my_num;
    }

    public int getSong_sale() {
        return song_sale;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setPic_small(String pic_small) {
        this.pic_small = pic_small;
    }

    public void setPic_big(String pic_big) {
        this.pic_big = pic_big;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.album_id);
        dest.writeString(this.title);
        dest.writeString(this.info);
        dest.writeString(this.pic_small);
        dest.writeString(this.pic_big);
    }

    public AlbumInfo() {
    }

    protected AlbumInfo(Parcel in) {
        this.album_id = in.readString();
        this.title = in.readString();
        this.info = in.readString();
        this.pic_small = in.readString();
        this.pic_big = in.readString();
    }

    public static final Creator<AlbumInfo> CREATOR = new Creator<AlbumInfo>() {
        @Override
        public AlbumInfo createFromParcel(Parcel source) {
            return new AlbumInfo(source);
        }

        @Override
        public AlbumInfo[] newArray(int size) {
            return new AlbumInfo[size];
        }
    };
}
