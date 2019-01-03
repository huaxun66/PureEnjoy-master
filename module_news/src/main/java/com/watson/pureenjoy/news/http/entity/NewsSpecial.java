package com.watson.pureenjoy.news.http.entity;

import java.util.List;

public class NewsSpecial {

    private String skipcontent;
    private String banner;
    private int del;
    private String lmodify;
    private String type;
    private String sid;
    private String sdocid;
    private String sname;
    private Object webviews;
    private String digest;
    private String tag;
    private String imgsrc;
    private String photoset;
    private String pushTime;
    private String ptime;
    private String ec;
    private String shownav;
    private List<?> topicsplus;
    private List<?> headpics;
    private List<TopicsEntity> topics;
    private List<?> topicspatch;
    private List<?> topicslatest;

    public String getSkipcontent() {
        return skipcontent;
    }

    public void setSkipcontent(String skipcontent) {
        this.skipcontent = skipcontent;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
        this.del = del;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSdocid() {
        return sdocid;
    }

    public void setSdocid(String sdocid) {
        this.sdocid = sdocid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Object getWebviews() {
        return webviews;
    }

    public void setWebviews(Object webviews) {
        this.webviews = webviews;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPhotoset() {
        return photoset;
    }

    public void setPhotoset(String photoset) {
        this.photoset = photoset;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getShownav() {
        return shownav;
    }

    public void setShownav(String shownav) {
        this.shownav = shownav;
    }

    public List<?> getTopicsplus() {
        return topicsplus;
    }

    public void setTopicsplus(List<?> topicsplus) {
        this.topicsplus = topicsplus;
    }

    public List<?> getHeadpics() {
        return headpics;
    }

    public void setHeadpics(List<?> headpics) {
        this.headpics = headpics;
    }

    public List<TopicsEntity> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicsEntity> topics) {
        this.topics = topics;
    }

    public List<?> getTopicspatch() {
        return topicspatch;
    }

    public void setTopicspatch(List<?> topicspatch) {
        this.topicspatch = topicspatch;
    }

    public List<?> getTopicslatest() {
        return topicslatest;
    }

    public void setTopicslatest(List<?> topicslatest) {
        this.topicslatest = topicslatest;
    }

    public static class TopicsEntity {

        private int index;
        private String tname;
        private String type;
        private String shortname;
        private List<DocsEntity> docs;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getShortname() {
            return shortname;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }

        public List<DocsEntity> getDocs() {
            return docs;
        }

        public void setDocs(List<DocsEntity> docs) {
            this.docs = docs;
        }

        public static class DocsEntity {
            private int votecount;
            private String docid;
            private String lmodify;
            private String postid;
            private String source;
            private String title;
            private String ipadcomment;
            private String url;
            private int replyCount;
            private String ltitle;
            private int imgsum;
            private String digest;
            private String tag;
            private String imgsrc;
            private String ptime;
            private String skipType;
            private String skipID;
            private List<ImgextraEntity> imgextra;
            private VideoInfo videoinfo;


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

            public List<ImgextraEntity> getImgextra() {
                return imgextra;
            }

            public void setImgextra(List<ImgextraEntity> imgextra) {
                this.imgextra = imgextra;
            }

            public VideoInfo getVideoinfo() {
                return videoinfo;
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

            public static class VideoInfo {

                private String title;
                private String description;
                private String ptime;
                private String mp4_url;
                private String mp4Hd_url;
                private String m3u8_url;
                private String m3u8Hd_url;

                public String getTitle() {
                    return title;
                }

                public String getDescription() {
                    return description;
                }

                public String getPtime() {
                    return ptime;
                }

                public String getMp4_url() {
                    return mp4_url;
                }

                public String getMp4Hd_url() {
                    return mp4Hd_url;
                }

                public String getM3u8_url() {
                    return m3u8_url;
                }

                public String getM3u8Hd_url() {
                    return m3u8Hd_url;
                }
            }


            public int getVotecount() {
                return votecount;
            }

            public void setVotecount(int votecount) {
                this.votecount = votecount;
            }

            public String getDocid() {
                return docid;
            }

            public void setDocid(String docid) {
                this.docid = docid;
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

            public String getIpadcomment() {
                return ipadcomment;
            }

            public void setIpadcomment(String ipadcomment) {
                this.ipadcomment = ipadcomment;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getReplyCount() {
                return replyCount;
            }

            public void setReplyCount(int replyCount) {
                this.replyCount = replyCount;
            }

            public String getLtitle() {
                return ltitle;
            }

            public void setLtitle(String ltitle) {
                this.ltitle = ltitle;
            }

            public int getImgsum() {
                return imgsum;
            }

            public void setImgsum(int imgsum) {
                this.imgsum = imgsum;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
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
        }
    }
}
