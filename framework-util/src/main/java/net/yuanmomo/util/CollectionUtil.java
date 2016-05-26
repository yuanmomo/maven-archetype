/**
 * Project Name : Tools
 * File Name    : ListUtil.java
 * Package Name : net.yuanmomo.tools.util.collention
 * Created on   : 2013-12-24下午11:48:08
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * ClassName : ListUtil 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2013-12-24 下午11:48:08 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.7
 * @see 	 
 */
public class CollectionUtil {
	private static Logger logger = LoggerFactory.getLogger(CollectionUtil.class);
	
	/**
	 * isNull: 判断指定的集合为空. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param con
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean isNull(Collection<?> con){
		if(con == null || con.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 * isNull: 判断指定的数组为空. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param array
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean isNull(Object[] array){
		if(array == null || array.length == 0){
			return true;
		}
		return false;
	}
	/**
	 * isNull: 判断指定的数组不为空. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param array
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean isNotNull(Object[] array){
		return !isNull(array);
	}
	
	
	/**
	 * isNotNull:  判断指定的集合不为空. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param con
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean isNotNull(Collection<?> con){
		return !isNull(con);
	}
	
	/**
	 * isNull: 判断指定的集合为空. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param map
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean isNull(Map<?,?> map){
		if(map == null || map.isEmpty()){
			return true;
		}
		return false;
	}
	/**
	 * isNotNull:  判断指定的集合不为空. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param map
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean isNotNull(Map<?,?> map){
		return !isNull(map);
	}
	
	
	/**
	 * toString: 按指定分隔符打印一个集合. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param col
	 * @param seperator
	 * @return
	 * @since JDK 1.7
	 */
	public static<T> String toString(Collection<T> col,String seperator){
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isBlank(seperator)){
			seperator = "\n";
		}
		Iterator<T> ite = col.iterator();
		while(ite.hasNext()){
			sb.append(ite.next()).append(seperator);
		}
		return sb.toString();
	}
	
	/**
	 * convert: 将一个List转换为targetType类型的List. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param sourceList
	 * @param targetType
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	public static<T> List<T> convert(List<?> sourceList,Class<T> targetType){
		List<T> result = new ArrayList<T>();
		if(isNull(sourceList)){
			return result;
		}
		Iterator<?> ite = sourceList.iterator();
		if(String.class.getName().equals(targetType.getName())){
			// 将指定类型转换为String类型，直接用Stirng.ValueOf方法
			Method targetMothod = null;
			try {
				targetMothod = targetType.getMethod("valueOf", new Class[]{Object.class});
			} catch (NoSuchMethodException e1) {
				logger.error("convert List value to "+targetType+" error,"+e1.getMessage());
				return result;
			} catch (SecurityException e2) {
				logger.error("convert List value to "+targetType+" error,"+e2.getMessage());
				return result;
			}
			while(ite.hasNext()){
				Object temp = ite.next();
				try {
					result.add((T) targetMothod.invoke(null,temp ));
				} catch (Exception e) {
					logger.error("convert value +" +temp+ " to "+ targetType+" error,"+e.getMessage());
					continue;
				}
			}
			return result;
		}else if(Number.class.getName().equals(targetType.getSuperclass().getName())){
			// 该目标类型是Number的子类
			Method[] mothods = targetType.getMethods();
			Method targetMothod = null;
			if(isNotNull(mothods)){
				for(Method m : mothods){
					if(m.getName().startsWith("parse")){
						Class<?>[] parameterTypes = m.getParameterTypes();
						if(parameterTypes !=null 
							&& parameterTypes.length==1 
							&& String.class.getName().equals(parameterTypes[0].getName()))
							// 该方法是parse开头，并且只有一个String的参数
							targetMothod = m;
					}
				}
			}
			if(targetMothod != null){
				// 找到数字类型的转换方法
				while(ite.hasNext()){
					Object temp = ite.next();
					if(!(temp instanceof String)){
						temp = String.valueOf(temp);
					}
					try {
						result.add((T)targetMothod.invoke(null,temp ));
					} catch (Exception e) {
						logger.error("convert value +" +temp+ " to "+ targetType +" error,"+e.getMessage());
						continue;
					}
				}
				
			}
		}
		
		return result;
	}
	
	/**
	 * toList: 根据指定字符拆分,转换字符串为一个字符串集合. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param source
	 * @param seperator
	 * @return
	 * @since JDK 1.7
	 */
	public static List<String> toList(String source,String seperator){
		if(StringUtils.isNotBlank(source)){
			String[] array = source.split(seperator);
			return Arrays.asList(array);
		}
		return new ArrayList<String>();
	}
	
	
	@SuppressWarnings({"rawtypes"})
	public static void print(Collection coll){
		if(isNotNull(coll)){
			System.out.println("********************************************");
			for(Object t : coll){
				if(t instanceof Collection){
					print((Collection)t);
				}else{
					System.out.println(t);
				}
			}
			return ;
		}
	}
	@SuppressWarnings("rawtypes")
	public static  void print(Map map){
		if(isNotNull(map)){
			System.out.println("********************************************");
			for(Object key : map.keySet()){
				Object value = map.get(key);
				if(value instanceof Collection){
					print((Map)value);
				}else{
					System.out.println("Key=" + key+"; Value=" + value);
				}
			}
			return;
		}
	}
	
	public static void print(Object[]  array){
		if(isNotNull(array)){
			System.out.println("********************************************");
			for(Object item : array){
				System.out.print(item + "\t");
			}
		}
		System.out.println();
	}
			
	/**
	 *  将map的key转换为一个list返回. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param map
	 */
	public static <K,V> List<K> keyToList(Map<K,V> map){
		return keyToList(map,map.size());
	}
	
	public static <K,V> List<K> keyToList(Map<K,V> map,int count){
		if(isNotNull(map)){
			if(map.size() <= count){
				return new ArrayList<K>(map.keySet());
			}else{
				List<K> list =  new ArrayList<K>(map.keySet());
				return list.subList(0, count);
			}
		}
		return null;
	}
	
	/**
	 *  将map的value转换为一个list返回. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param map
	 */
	public static <K,V> List<V> valueToList(Map<K,V> map){
		if(isNotNull(map)){
			return new ArrayList<V>(map.values());
		}
		return null;
	}
	/**
	 *  将map的value转换为一个TreeMap返回. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param map
	 */
	public static <K,V> Set<V> valueToTreeSet(Map<K,V> map){
		if(isNotNull(map)){
			Set<V> set = new TreeSet<V>();
			for(V v : map.values()){
				System.out.println(v.hashCode());
				System.out.println(set.contains(v));
				set.add(v);
			}
			return set;
		}
		return null;
	}
	
	public static <K,V> List<V> selectRandom(Map<K,V> map,int size){
		if(isNull(map)){
			return null;
		}
		List<V> values = valueToList(map);
		if(values.size() <= size){
			return new ArrayList<V>(values);
		}
		Collections.shuffle(values);
		return values.subList(0, size);
	}
	
	
	public static <K,V> String getParameterLine (Map<K,V> map){
		StringBuilder sb = new StringBuilder();
		if(map != null && map.size() > 0){
			Set<K> keySet = map.keySet();
			for(K key : keySet){
				V value = map.get(key);
				if(value != null){
					if(value instanceof String[]){
						String[] valueArray = (String[])value;
						for(String strValue : valueArray){
							if(sb.length() > 0){
								sb.append("&");
							}
							sb.append(key).append("=").append(strValue);
						}
					}else{
						if(sb.length() > 0){
							sb.append("&");
						}
						sb.append(key).append("=").append(value);
					}
				}else{
					if(sb.length() > 0){
						sb.append("&");
					}
					sb.append(key).append("=");
				}
			}
		}
		return sb.toString();
	}
}
