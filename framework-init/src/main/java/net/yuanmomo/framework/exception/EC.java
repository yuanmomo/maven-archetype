package net.yuanmomo.framework.exception;


/**
 * Created by Hongbin.Yuan on 2015-10-13 00:20.
 */

public enum  EC {
    SYSTEM_ERROR(-100000, "SYSTEM_ERROR"),
    ID_INVALID ( -100001,"ID_INVALID");

    private int code;
    private String desc;

    EC(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
