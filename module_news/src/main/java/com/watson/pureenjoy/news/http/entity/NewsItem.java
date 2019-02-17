package com.watson.pureenjoy.news.http.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.watson.pureenjoy.news.app.NewsConstants;

import java.util.List;
import java.util.Objects;

@Entity(tableName = "news")
public class NewsItem implements MultiItemEntity {

    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;

    @ColumnInfo(name = "type_id")
    private String typeId;
    @Ignore
    private String template;
    @Ignore
    private String skipID;
    @Ignore
    private String lmodify;
    @ColumnInfo(name = "post_id")
    private String postid;
    @ColumnInfo(name = "source")
    private String source;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "m_time")
    private String mtime;
    @Ignore
    private int hasImg;
    @Ignore
    private String topic_background;
    @Ignore
    private String digest;
    @ColumnInfo(name = "photo_set_id")
    private String photosetID;
    @Ignore
    private String boardid;
    @Ignore
    private String alias;
    @Ignore
    private int hasAD;
    @ColumnInfo(name = "img_src")
    private String imgsrc;
    @Ignore
    private String ptime;
    @Ignore
    private String daynum;
    @Ignore
    private int hasHead;
    @Ignore
    private int order;
    @Ignore
    private int votecount;
    @Ignore
    private boolean hasCover;
    @Ignore
    private String docid;
    @Ignore
    private String tname;
    @Ignore
    private int priority;
    @Ignore
    private String ename;
    @Ignore
    private int replyCount;
    @Ignore
    private int imgsum;
    @Ignore
    private boolean hasIcon;
    @ColumnInfo(name = "skip_type")
    private String skipType;
    @Ignore
    private String cid;
    @Ignore
    private String url_3w;
    @ColumnInfo(name = "url")
    private String url;
    @Ignore
    private String ltitle;
    @Ignore
    private String subtitle;
    @Ignore
    private String articleType;
    @Ignore
    private String TAG;
    @Ignore
    private String TAGS;
    @Ignore
    private List<ImgextraEntity> imgextra;
    @Ignore
    private List<AdsEntity> ads;
    @ColumnInfo(name = "special_id")
    private String specialID;
    @ColumnInfo(name = "current_time")
    private Long currentTime;

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSpecialID() {
        return specialID;
    }

    public void setSpecialID(String specialID) {
        this.specialID = specialID;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSkipID() {
        return skipID;
    }

    public void setSkipID(String skipID) {
        this.skipID = skipID;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public int getHasImg() {
        return hasImg;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public String getTopic_background() {
        return topic_background;
    }

    public void setTopic_background(String topic_background) {
        this.topic_background = topic_background;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getPhotosetID() {
        return photosetID;
    }

    public void setPhotosetID(String photosetID) {
        this.photosetID = photosetID;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getHasAD() {
        return hasAD;
    }

    public void setHasAD(int hasAD) {
        this.hasAD = hasAD;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getDaynum() {
        return daynum;
    }

    public void setDaynum(String daynum) {
        this.daynum = daynum;
    }

    public int getHasHead() {
        return hasHead;
    }

    public void setHasHead(int hasHead) {
        this.hasHead = hasHead;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getVotecount() {
        return votecount;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public boolean isHasCover() {
        return hasCover;
    }

    public void setHasCover(boolean hasCover) {
        this.hasCover = hasCover;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getImgsum() {
        return imgsum;
    }

    public void setImgsum(int imgsum) {
        this.imgsum = imgsum;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUrl_3w() {
        return url_3w;
    }

    public void setUrl_3w(String url_3w) {
        this.url_3w = url_3w;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLtitle() {
        return ltitle;
    }

    public void setLtitle(String ltitle) {
        this.ltitle = ltitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public String getTAGS() {
        return TAGS;
    }

    public void setTAGS(String TAGS) {
        this.TAGS = TAGS;
    }

    public List<ImgextraEntity> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<ImgextraEntity> imgextra) {
        this.imgextra = imgextra;
    }

    public List<AdsEntity> getAds() {
        return ads;
    }

    public void setAds(List<AdsEntity> ads) {
        this.ads = ads;
    }


    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_PHOTO = 1;
    public static final int TYPE_BANNER = 2;

    @Override
    public int getItemType() {
        if (getHasAD() == 1 && getAds() != null && getAds().size() > 0) {
            return TYPE_BANNER;
        }
        return NewsConstants.PHOTO_SET.equals(getSkipType()) ? TYPE_PHOTO : TYPE_NORMAL;
    }

    public static class ImgextraEntity {
        private String imgsrc;

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }
    }

    public static class AdsEntity {
        private String subtitle;
        private String skipType;
        private String skipID;
        private String tag;
        private String title;
        private String imgsrc;
        private String url;

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getSkipType() {
            return skipType;
        }

        public void setSkipType(String skipType) {
            this.skipType = skipType;
        }

        public String getSkipID() {
            return skipID;
        }

        public void setSkipID(String skipID) {
            this.skipID = skipID;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsItem)) return false;
        NewsItem newsItem = (NewsItem) o;
        return Objects.equals(getPostid(), newsItem.getPostid()) &&
                Objects.equals(getPhotosetID(), newsItem.getPhotosetID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostid(), getPhotosetID());
    }
}
