/** 
 * Project Name : Tools
 * Package Name : net.yuanmomo.tools.plugin.spring.springmvc.json
 * Created on   : 2014-1-7上午9:27:46
 * File Name    : CustomObjectMapper.java
 *
 * Author       : Hongbin.Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.util.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;
import java.util.TimeZone;


/**
 * ClassName : CustomObjectMapper 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-1-7 上午9:27:46 
 *
 * @author   : Hongbin.Yuan
 * @version  
 * @since      JDK 1.7
 * @see 	 
 */
public class CustomObjectMapper extends ObjectMapper{
    /**
	 * serialVersionUID:TODO.
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;

	public CustomObjectMapper(){
        StdSerializerProvider sp = new StdSerializerProvider();
        sp.setNullValueSerializer(NullSerializer.instance);
        this.setSerializerProvider(sp);

        //去掉默认的时间戳格式
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
//        this.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        this.setTimeZone(TimeZone.getDefault());
        this.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

        //序列化时，日期的统一格式
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        this.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

    }
}