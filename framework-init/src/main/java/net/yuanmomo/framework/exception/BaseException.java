package net.yuanmomo.framework.exception;

/**
 * Created by Hongbin.Yuan on 2015-10-13 00:18.
 */

public abstract class BaseException extends Exception{
    private static final long serialVersionUID = 7108939499237069014L;

    private int code;

    private String key;

    public BaseException(int code, String key){
        this.code = code;
        this.key = key;
    }


    public BaseException(EC ec){
        this.code = ec.getCode();
        this.key = ec.getDesc();
    }

    public int getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }
}
