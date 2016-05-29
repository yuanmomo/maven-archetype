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
 * Package Name : net.yuanmomo.tools.common
 * Created on   : Jun 24, 20135:29:08 PM
 * File Name    : MD5.java
 *
 * Author       : yuanmomo
 * Blog         : yuanmomo.net
 * Company      : 北京华青融天技术有限责任公司  
 */

package net.yuanmomo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName : MD5 Function : 计算执行的字符串的MD5值 Reason : 加密密码 Date : Jun 24, 2013
 * 5:29:08 PM
 * 
 * @author : MoMo
 * @version
 * @since JDK 1.6
 * @see
 */
public class MD5 {

	private static char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
	'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f'};
	/**
	 * getMD5: . <br/>
	 * 
	 * 
	 * @author Hongbin.Yuan
	 * @param source
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static String getMD5(byte[] source) throws Exception {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			return byteToHexString(tmp);
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
	}

	public static String getSHA256(byte[] source) throws Exception {
		MessageDigest sha256 = null;
		try {
			sha256 = MessageDigest.getInstance("SHA-256");
			sha256.update(source);
			byte tmp[] = sha256.digest();
			return byteToHexString(tmp);
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
	}

	/**
	 * getMD5: . <br/>
	 * 
	 * 
	 * @author Hongbin.Yuan
	 * @param param
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static String getMD5(String param) throws Exception {
		byte[] source = param.getBytes();
		return getMD5(source);
	}

	public static String getSHA256(String param) throws Exception {
		byte[] source = param.getBytes();
		return getSHA256(source);
	}

	/**
	 * getMD5: 文件MD5加密. <br/>
	 * 
	 * @author Hongbin.Yuan
	 * @param file
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static String getMD5(File file) throws Exception {
		FileInputStream fis = null;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[2048];
			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			byte[] b = md.digest();
			return byteToHexString(b);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				throw ex;
			}
		}
	}

	/**
	 * 把byte[]数组转换成十六进制字符串表示形式
	 * 
	 * @param tmp
	 *            要转换的byte[]
	 * @return 十六进制字符串表示形式
	 */
	public static String byteToHexString(byte[] tmp) {
		String s;
		// 用字节表示就是 16 个字节
		char str[] = new char[tmp.length * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
		// 所以表示成 16 进制需要 32 个字符
		int k = 0; // 表示转换结果中对应的字符位置
		for (int i = 0; i < tmp.length; i++) { // 从第一个字节开始，对 MD5 的每一个字节
			// 转换成 16 进制字符的转换
			byte byte0 = tmp[i]; // 取第 i 个字节
			str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			// >>> 为逻辑右移，将符号位一起右移
			str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		s = new String(str); // 换后的结果转换为字符串
		return s;
	}

	public static void main(String[] args) {
	}
}
