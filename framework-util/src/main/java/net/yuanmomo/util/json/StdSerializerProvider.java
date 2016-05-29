/** 
 * Project Name : Tools
 * Package Name : net.yuanmomo.tools.plugin.spring.springmvc.json
 * Created on   : 2014-1-6下午4:44:43
 * File Name    : StdSerializerProvider.java
 *
 * Author       : Hongbin.Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.util.json;

import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

/**
 * ClassName : StdSerializerProvider 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-1-6 下午4:44:43 
 *
 * @author   : Hongbin.Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */
public class StdSerializerProvider extends DefaultSerializerProvider
{
    private static final long serialVersionUID = 1L;

    public StdSerializerProvider() { super(); }

    protected StdSerializerProvider(SerializerProvider src,
            SerializationConfig config,SerializerFactory f) {
        super(src, config, f);
    }

    @Override
    public StdSerializerProvider createInstance(SerializationConfig config,
            SerializerFactory jsf) {
        return new StdSerializerProvider(this, config, jsf);
    }
}