package net.yuanmomo.framework.exception;

/**
 *
 * 业务异常。
 *
 * Created by Hongbin.Yuan on 2015-10-13 00:21.
 */

public class LogicException extends BaseException {

    private static final long serialVersionUID = -5535136581532615754L;

    public LogicException(int code, String message) {
        super(code, message);
    }

    public LogicException(EC ec) {
        super(ec);
    }
}
