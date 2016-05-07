package net.yuanmomo.util.exception;

/**
 * Created by Hongbin.Yuan on 2015-10-13 00:20.
 */

public class ExceptionConstant {

    /************************************ 生成文件异常 ************************************/
    public static LogicException FILE_GENERATE_ERROR = new LogicException(-1, "FILE_GENERATE_ERROR");
    
    /************************************ 生成代收或者代付文件名异常 ************************************/
    public static LogicException CLIENT_NO_INVALID = new LogicException(-100001, "CLIENT_NO_INVALID");
    public static LogicException TYPE_INVALID = new LogicException(-100002, "TYPE_INVALID");
    public static LogicException DAY_NO_INVALID = new LogicException(-100003, "DAY_NO_INVALID");
    public static LogicException GENERATED_FILE_LENGTH_INCORRECT = new LogicException(-100004, "GENERATED_FILE_LENGTH_INCORRECT");
    public static LogicException VERSION_INVALID = new LogicException(-100004, "VERSION_INVALID");


    /**
     * 生成代收，或者代付文件时，保存文件内容的List 为空。
     */
    public static LogicException FILE_CONTENT_IS_NULL = new LogicException(-10000100, "FILE_CONTENT_IS_NULL");

    /**
     * 生成代收，或者代付文件时，业务类型错误。
     */
    public static LogicException BUSINESS_TYPE_INVALID = new LogicException(-10000200, "BUSINESS_TYPE_INVALID");


}
