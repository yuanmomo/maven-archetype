package net.yuanmomo.framework.util;

/**
 * Created by Hongbin.Yuan on 2015-10-12 23:42.
 */

public enum DateFormatEnu {
    DEFATUL("yyyy-MM-dd HH:mm:ss"),
    yyyy_MM_dd_HH_mm_ss_SSS("yyyy-MM-dd HH:mm:ss:SSS"),
    yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
    yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),
    yyyy_MM_dd("yyyy-MM-dd"),
    yyyyMMdd("yyyyMMdd"),
    yyyy_MM("yyyy-MM"),
    MM_dd_HH_mm("MM-dd HH:mm"),
    MM_dd("MM-dd");
    
    private String format;

    DateFormatEnu(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
