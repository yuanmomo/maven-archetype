package net.yuanmomo.util.exception;

/**
 *
 * 业务异常。
 *
 * Created by Hongbin.Yuan on 2015-10-13 00:21.
 */

public class LogicException extends BaseException {

    public LogicException(int code, String message) {
        super(code, message);
    }
}
