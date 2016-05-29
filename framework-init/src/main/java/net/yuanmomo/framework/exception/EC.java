package net.yuanmomo.framework.exception;

import net.yuanmomo.util.exception.LogicException;

/**
 * Created by Hongbin.Yuan on 2015-10-13 00:20.
 */

public class EC {
    public static LogicException SYSTEM_ERROR = new LogicException(-100000, "SYSTEM_ERROR");

    public static LogicException ID_INVALID = new LogicException( -100001,"ID_INVALID");
}
