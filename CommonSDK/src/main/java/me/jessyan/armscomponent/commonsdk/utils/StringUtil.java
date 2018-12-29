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
package me.jessyan.armscomponent.commonsdk.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.jessyan.armscomponent.commonsdk.R;

/**
 * ================================================
 * Created by Watson on 2018/12/29
 * ================================================
 */
public class StringUtil {

    /**
     * 30分钟内：刚刚
     * 30分钟到90分钟：××分钟前
     * 90分钟到6小时：××小时前
     * 前一天：昨天
     * 其他：年月日
     */
    public static String getFormatDateString(Context mContext, String inputTime) {
        try {
            if (inputTime != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(inputTime);
                long ts = date.getTime();
                Calendar currCal = Calendar.getInstance();
                Calendar inputCal = Calendar.getInstance();
                inputCal.setTime(date);
                int currYear = currCal.get(Calendar.YEAR);
                int inputYear = inputCal.get(Calendar.YEAR);
                int currMonth = currCal.get(Calendar.MONTH) + 1;
                int inputMoth = inputCal.get(Calendar.MONTH) + 1;
                int currDay = currCal.get(Calendar.DAY_OF_MONTH);
                int inputDay = inputCal.get(Calendar.DAY_OF_MONTH);
                if (currYear == inputYear && currMonth == inputMoth && (currDay - inputDay) == 1) {
                    return mContext.getString(R.string.time_yesterday);
                } else if ((System.currentTimeMillis()) - ts < 1000 * 60 * 30) {
                    return mContext.getString(R.string.time_just_now);
                } else if ((System.currentTimeMillis() - ts) >= 1000 * 60 * 30 && (System.currentTimeMillis() - ts) < 1000 * 60 * 60 * 6) {
                    if ((System.currentTimeMillis() - ts) / (1000 * 60 * 60) < 1) {
                        return (System.currentTimeMillis() - ts) / (60 * 1000) + mContext.getString(R.string.time_min_ago);
                    } else {
                        return (System.currentTimeMillis() - ts) / (60 * 1000 * 60) + mContext.getString(R.string.time_hours_ago);
                    }
                } else {
                    SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String displayDateTime = displayFormat.format(date);
                    return displayDateTime;
                }
            }
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return inputTime;
    }


}
