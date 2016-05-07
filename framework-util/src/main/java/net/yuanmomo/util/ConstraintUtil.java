package net.yuanmomo.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Hongbin.Yuan on 2015-11-01 23:30.
 */

public class ConstraintUtil {

    private static Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    public static String validateModel(Object obj) {//验证某一个对象

        StringBuffer buffer = new StringBuffer(64);//用于存储验证后的错误信息

        Set<ConstraintViolation<Object>> constraintViolations = validator
                .validate(obj);//验证某个对象,，其实也可以只验证其中的某一个属性的

        Iterator<ConstraintViolation<Object>> iter = constraintViolations
                .iterator();
        while (iter.hasNext()) {
            String message = iter.next().getMessage();
            buffer.append(message + "\n");
        }
        return buffer.toString();
    }

}
