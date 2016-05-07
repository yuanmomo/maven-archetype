package net.yuanmomo.util.exception;

/**
 * Created by Hongbin.Yuan on 2015-10-13 00:18.
 */

public abstract class BaseException extends Exception{
    private int code;

    private String key;

    public BaseException(int code, String key){
        this.code = code;
        this.key = key;
    }


    public int getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }
}
