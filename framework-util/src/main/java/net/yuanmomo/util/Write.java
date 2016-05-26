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
 * Package Name : net.yuanmomo.tools.io
 * Created on   : Jun 20, 20134:30:39 PM
 * File Name    : Output.java
 *
 * Author       : yuanmomo
 * Blog         : yuanmomo.net
 * Company      : 北京华青融天技术有限责任公司  
 */

package net.yuanmomo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

/**
 * ClassName : Output 
 * Date      : Jun 20, 2013 4:30:39 PM 
 *
 * @author   : MoMo
 * @version  
 * @since      JDK 1.7
 * @see 	 
 */
public class Write {
	private static Logger logger=LoggerFactory.getLogger(Write.class);
	
	/**
	 * output: 将一个list对象输出到path文件. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param path
	 * @param list
	 * @return
	 * @throws Exception
	 * @since JDK 1.7
	 */
	public static<T> boolean output(String path,List<T> list)throws Exception{
		File f=new File(path);
		return output(f, list);
	}
	/**
	 * output: 将一个list对象输出到指定的uri文件. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param uri
	 * @param list
	 * @return
	 * @throws Exception
	 * @since JDK 1.7
	 */
	public static<T> boolean output(URI uri,List<T> list)throws Exception{
		File file=new File(uri);
		return output(file, list);
	}
	
	/**
	 * output: 将一个list对象输出到指定的文件对象. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param file
	 * @param list
	 * @return
	 * @throws Exception
	 * @since JDK 1.7
	 */
	public static<T> boolean output(File file,List<T> list)throws Exception{
		if(file==null){
			return false;
		}
		logger.info("The output file is "+ file.getAbsolutePath());
		File parent = new File(file.getAbsolutePath()).getParentFile();
		if(parent==null || !parent.exists()){//父目录不存在，创建
			parent.mkdirs();
		}
		logger.info("Start to print file, "+file.getAbsolutePath());		
		PrintWriter pw =null;
		try {
			pw =new PrintWriter(file);
			Iterator<T> ite = list.iterator();
			while(ite.hasNext()){
				T temp = ite.next();
				if(temp != null){
					pw.println(temp.toString());
				}
			}
			logger.info("Finish printing the file, "+file.getAbsolutePath());
			return true;
		} catch (Exception e) {
			throw e;
		}finally{
			pw.close();
		}
	}
}
