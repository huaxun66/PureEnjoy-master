package com.watson.pureenjoy.news.http.entity;

import java.util.List;

public class NewsDetail {
    private String template;
    private String shareLink;
    private String source;
    private int threadVote;
    private String title;
    private String body;
    private String tid;
    private boolean picnews;
    private String articleType;
    private String digest;
    private String ptime;
    private String ec;
    private String docid;
    private int threadAgainst;
    private boolean hasNext;
    private String dkeys;
    private int replyCount;
    private String voicecomment;
    private String replyBoard;
    private String category;
    private List<ImgEntity> img;
    private List<TopiclistNewsEntity> topiclist_news;
    private List<?> book;
    private List<?> link;
    private List<RelativeSysEntity> relative_sys;
    private List<?> boboList;
    private List<?> ydbaike;
    private List<AskbarEntity> askbar;
    private List<?> votes;
    private List<TopiclistEntity> topiclist;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getThreadVote() {
        return threadVote;
    }

    public void setThreadVote(int threadVote) {
        this.threadVote = threadVote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public boolean isPicnews() {
        return picnews;
    }

    public void setPicnews(boolean picnews) {
        this.picnews = picnews;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
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

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public int getThreadAgainst() {
        return threadAgainst;
    }

    public void setThreadAgainst(int threadAgainst) {
        this.threadAgainst = threadAgainst;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getDkeys() {
        return dkeys;
    }

    public void setDkeys(String dkeys) {
        this.dkeys = dkeys;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getVoicecomment() {
        return voicecomment;
    }

    public void setVoicecomment(String voicecomment) {
        this.voicecomment = voicecomment;
    }

    public String getReplyBoard() {
        return replyBoard;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ImgEntity> getImg() {
        return img;
    }

    public void setImg(List<ImgEntity> img) {
        this.img = img;
    }

    public List<TopiclistNewsEntity> getTopiclist_news() {
        return topiclist_news;
    }

    public void setTopiclist_news(List<TopiclistNewsEntity> topiclist_news) {
        this.topiclist_news = topiclist_news;
    }

    public List<?> getBook() {
        return book;
    }

    public void setBook(List<?> book) {
        this.book = book;
    }

    public List<?> getLink() {
        return link;
    }

    public void setLink(List<?> link) {
        this.link = link;
    }

    public List<RelativeSysEntity> getRelative_sys() {
        return relative_sys;
    }

    public void setRelative_sys(List<RelativeSysEntity> relative_sys) {
        this.relative_sys = relative_sys;
    }

    public List<?> getBoboList() {
        return boboList;
    }

    public void setBoboList(List<?> boboList) {
        this.boboList = boboList;
    }

    public List<?> getYdbaike() {
        return ydbaike;
    }

    public void setYdbaike(List<?> ydbaike) {
        this.ydbaike = ydbaike;
    }

    public List<AskbarEntity> getAskbar() {
        return askbar;
    }

    public void setAskbar(List<AskbarEntity> askbar) {
        this.askbar = askbar;
    }

    public List<?> getVotes() {
        return votes;
    }

    public void setVotes(List<?> votes) {
        this.votes = votes;
    }

    public List<TopiclistEntity> getTopiclist() {
        return topiclist;
    }

    public void setTopiclist(List<TopiclistEntity> topiclist) {
        this.topiclist = topiclist;
    }

    public static class ImgEntity {
        private String ref;
        private String src;
        private String alt;
        private String pixel;

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getPixel() {
            return pixel;
        }

        public void setPixel(String pixel) {
            this.pixel = pixel;
        }
    }

    public static class TopiclistNewsEntity {
        private String ename;
        private boolean hasCover;
        private String tname;
        private String alias;
        private String subnum;
        private String tid;
        private String cid;

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getSubnum() {
            return subnum;
        }

        public void setSubnum(String subnum) {
            this.subnum = subnum;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }

    public static class RelativeSysEntity {
        private String docID;
        private String from;
        private String href;
        private String id;
        private String imgsrc;
        private String title;
        private String type;
        private String ptime;

        public String getDocID() {
            return docID;
        }

        public void setDocID(String docID) {
            this.docID = docID;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }
    }

    public static class AskbarEntity {
        private String expertId;
        private int concernCount;
        private String name;
        private String alias;
        private String headpicurl;
        private String title;

        public String getExpertId() {
            return expertId;
        }

        public void setExpertId(String expertId) {
            this.expertId = expertId;
        }

        public int getConcernCount() {
            return concernCount;
        }

        public void setConcernCount(int concernCount) {
            this.concernCount = concernCount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getHeadpicurl() {
            return headpicurl;
        }

        public void setHeadpicurl(String headpicurl) {
            this.headpicurl = headpicurl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class TopiclistEntity {
        private String ename;
        private boolean hasCover;
        private String tname;
        private String alias;
        private String subnum;
        private String tid;
        private String cid;

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getSubnum() {
            return subnum;
        }

        public void setSubnum(String subnum) {
            this.subnum = subnum;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }
}
