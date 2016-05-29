package net.yuanmomo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
    
    private SimpleDateFormat sdf;
    
    private DateFormatEnu(String format){
        this.format = format;
        this.sdf = new SimpleDateFormat(format);
    }

    public String getFormat() {
        return format;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    /**
     * 通过{@link #format}转换{@link DateFormatEnu}。
     */
    public static DateFormat valueOfFormat(String format) {
        DateFormatEnu[] types = DateFormatEnu.values();
        for(DateFormatEnu p : types ){
            if(p.getFormat().equalsIgnoreCase(format)){
                return p.getSdf();
            }
        }
        return null;
    }
}
