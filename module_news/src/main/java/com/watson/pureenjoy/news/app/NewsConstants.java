/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.watson.pureenjoy.news.app;

/**
 * ================================================
 * Created by Watson on 01/05/2019
 * ================================================
 */
public class NewsConstants {
    public static final String CHANNEL_FILE = "NewsChannel";
    public static final String CHANNEL_SELECTED = "SelectedChannel";

    public static final String HEADER_AGENT = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
    public static final String CACHE_CONTROL = "Cache-Control: public, max-age=3600";

    public static final String RECOMMEND_TYPE_ID = "recommend_id";
    public static final String CLICK_TYPE_ID = "click_id";
    public static final String HEADLINE_TYPE_ID = "T1348647909107";
    public static final String HEADLINE_NAME = "头条";

    //新闻栏目
    public static final String TYPE_ID = "typeId";
    public static final String TYPE_NAME = "name";
    //新闻类型
    public static final String PHOTO_SET = "photoset";
    public static final String SPECIAL = "special";
    //新闻字段
    public static final String SPECIAL_ID = "specialID";
    public static final String PHOTO_SET_ID = "photosetID";
    public static final String POST_ID = "postid";
    public static final String URL = "url";
    public static final String BIG_IMG = "bigimg";

    public static final Long NEWS_EXPIRE_TIME = (long)2*3600*1000; //数据库超时时间2小时

}
