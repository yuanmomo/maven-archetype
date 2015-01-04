/** Copyright (c) 2013 MoMo, yuanhb@fusionskye.com All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Project Name : Tools
 * Package Name : net.yuanmomo.tools.string
 * Created on   : Jun 24, 20133:47:39 PM
 * File Name    : StringUtil.java
 *
 * Author       : yuanmomo
 * Blog         : yuanmomo.net
 * Company      : 北京华青融天技术有限责任公司  
 */

package net.yuanmomo.dwz.util;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName : StringUtil 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : Jun 24, 2013 3:47:39 PM 
 *
 * @author   : MoMo
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */
public class StringUtil {
	private static Logger logger=LoggerFactory.getLogger(StringUtil.class);
	/**
	 * charCount: 统计一个字符在字符串中出现的次数. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param source
	 * @param target
	 * @return
	 * @since JDK 1.6
	 */
	public static int charCount(String source,char target){
		if(source==null){
			return 0;
		}
		int count=0;
		for(int i=0;i<source.length();i++){
			if(target==source.charAt(i)){
				count++;
			}
		}
		return count;
	}
	/**
	 * isBlank: 判断字符串为空. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param str
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isBlank(String str){
		if(str==null){
			return true;
		}
		if("".equals(str.trim())){
			return true;
		}
		return false;
	}
	public static boolean isNull(String str){
		if(str==null){
			return true;
		}
		return false;
	}
	public static boolean isNotNull(String str){
		return !isNull(str);
	}
	
	/**
	 * isNotBlank: 判断字符串不为空. <br/>
	 * TODO .<br/>
	 * TODO .<br/>
	 *
	 * @author Hongbin Yuan
	 * @param str
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}
	
	/**
	 * upperFirstChar:  转换字符串的首字母大写. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param   oldString 转换前的字符串
	 * @return  String   转换后的字段串，首字母大写
	 * @since JDK 1.6
	 */
	public static String upperFirstChar(String oldString) {
		logger.debug("UpperCase the first letter of "+ oldString);
		if (isBlank(oldString)) {
			return oldString;
		}
		String target = new StringBuffer()
				.append(oldString.substring(0, 1).toUpperCase())
				.append(oldString.substring(1)).toString();
		return target;
	}
	/**
	 * lowerFirstChar:  转换字符串的首字母小写. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param   oldString 转换前的字符串
	 * @return  String   转换后的字段串，首字母大写
	 * @since JDK 1.6
	 */
	public static String lowerFirstChar(String oldString) {
		logger.debug("LowerCase the first letter of "+ oldString);
		if (isBlank(oldString)) {
			return oldString;
		}
		String target = new StringBuffer()
				.append(oldString.substring(0, 1).toLowerCase())
				.append(oldString.substring(1)).toString();
		return target;
	} 
	
	/**
	 *  随机生成一个字符串
	 * 
	 * @param Length
	 * @param Sleep
	 * @return
	 */
	public static String getRandomString(int length) {
		if(length <= 0){
			length = 10;
		}
		char[] pattern = new char[] { '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z' };
		StringBuilder result = new StringBuilder();
		int n = pattern.length;
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int rnd = random.nextInt(n);
			result.append(pattern[rnd]);
		}
		return result.toString();
	}
}
