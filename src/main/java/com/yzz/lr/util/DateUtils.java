package com.yzz.lr.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/16.
 */
public class DateUtils {

    /**
     * 获取前一天时间
     * @return
     */
    public static String getBeforeDate() {

        Calendar c = Calendar.getInstance();

        c.setTime(new Date());

        c.add(Calendar.DATE, -1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(c.getTime());

    }
}
