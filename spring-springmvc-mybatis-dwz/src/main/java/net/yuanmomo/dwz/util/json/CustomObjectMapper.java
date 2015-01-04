/** 
 * Project Name : Tools
 * Package Name : net.yuanmomo.tools.plugin.spring.springmvc.json
 * Created on   : 2014-1-7上午9:27:46
 * File Name    : CustomObjectMapper.java
 *
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.dwz.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * ClassName : CustomObjectMapper 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-1-7 上午9:27:46 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */
public class CustomObjectMapper extends ObjectMapper{
    /**
	 * serialVersionUID:TODO.
	 * @since JDK 1.6
	 */
	private static final long serialVersionUID = 1L;

	public CustomObjectMapper(){
        StdSerializerProvider sp = new StdSerializerProvider();
        sp.setNullValueSerializer(NullSerializer.instance);
        this.setSerializerProvider(sp);
    }
}