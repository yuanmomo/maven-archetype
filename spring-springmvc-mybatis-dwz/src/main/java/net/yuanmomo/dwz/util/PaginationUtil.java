
/**
 * Project Name : azq-manager
 * File Name    : PaginationUtil.java
 * Package Name : com.azq.manager.util
 * Created on   : 2014-3-20下午2:55:03
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.dwz.util;

import java.util.List;

/**
 * ClassName : PaginationUtil 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-3-20 下午2:55:03 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */
public class PaginationUtil {
	
	/**
	 * getPages: 分页显示,根据总记录数,和每页显示的条数取得显示的总页数.  <br/>
	 *
	 * @author Hongbin Yuan
	 * @param total
	 * @param pageSize
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static int getPages(int total, int pageSize) throws Exception{
		if(pageSize <= 0){
			throw new Exception("分页显示中,每页显示的数量必须大于等于0;pageSize="+pageSize);
		}
		if(total < 0){
			throw new Exception("分页显示中,记录的总数必须大于0;total="+total);
		}
		return  total%pageSize == 0 ? total / pageSize : total/pageSize+ 1;
	}
	
	
	public static class PaginationBean{
		
		private int pageNum;
		
		private int numPerPage;
		
		private int totalCount;
		
		private int totalPages;
		
		private List<?> result;

		/**
		 * totalPages.
		 *
		 * @return  the totalPages
		 * @since   JDK 1.6
		 */
		public int getTotalPages() {
			return totalPages;
		}

		/**
		 * totalPages.
		 *
		 * @param   totalPages    the totalPages to set
		 * @since   JDK 1.6
		 */
		public void setTotalPages(int totalPages) {
			this.totalPages = totalPages;
		}

		/**
		 * totalCount.
		 *
		 * @return  the totalCount
		 * @since   JDK 1.6
		 */
		public int getTotalCount() {
			return totalCount;
		}

		/**
		 * totalCount.
		 *
		 * @param   totalCount    the totalCount to set
		 * @since   JDK 1.6
		 */
		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}

		/**
		 * pageNum.
		 *
		 * @return  the pageNum
		 * @since   JDK 1.6
		 */
		public int getPageNum() {
			return pageNum;
		}

		/**
		 * pageNum.
		 *
		 * @param   pageNum    the pageNum to set
		 * @since   JDK 1.6
		 */
		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
		}

		/**
		 * numPerPage.
		 *
		 * @return  the numPerPage
		 * @since   JDK 1.6
		 */
		public int getNumPerPage() {
			return numPerPage;
		}

		/**
		 * numPerPage.
		 *
		 * @param   numPerPage    the numPerPage to set
		 * @since   JDK 1.6
		 */
		public void setNumPerPage(int numPerPage) {
			this.numPerPage = numPerPage;
		}

		/**
		 * result.
		 *
		 * @return  the result
		 * @since   JDK 1.6
		 */
		public List<?> getResult() {
			return result;
		}

		/**
		 * result.
		 *
		 * @param   result    the result to set
		 * @since   JDK 1.6
		 */
		public void setResult(List<?> result) {
			this.result = result;
		}
	}
}
