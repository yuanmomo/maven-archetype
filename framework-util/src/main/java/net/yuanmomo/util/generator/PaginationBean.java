package net.yuanmomo.util.generator;

import java.util.List;

public class PaginationBean {
	private int pageNum;
	
	private int numPerPage;
	
	private int totalCount;
	
	private int totalPages;
	
	private List<?> result;

	/**
	 * totalPages.
	 *
	 * @return  the totalPages
	 * @since   JDK 1.7
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * totalPages.
	 *
	 * @param   totalPages    the totalPages to set
	 * @since   JDK 1.7
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * totalCount.
	 *
	 * @return  the totalCount
	 * @since   JDK 1.7
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * totalCount.
	 *
	 * @param   totalCount    the totalCount to set
	 * @since   JDK 1.7
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * pageNum.
	 *
	 * @return  the pageNum
	 * @since   JDK 1.7
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * pageNum.
	 *
	 * @param   pageNum    the pageNum to set
	 * @since   JDK 1.7
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * numPerPage.
	 *
	 * @return  the numPerPage
	 * @since   JDK 1.7
	 */
	public int getNumPerPage() {
		return numPerPage;
	}

	/**
	 * numPerPage.
	 *
	 * @param   numPerPage    the numPerPage to set
	 * @since   JDK 1.7
	 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	/**
	 * result.
	 *
	 * @return  the result
	 * @since   JDK 1.7
	 */
	public List<?> getResult() {
		return result;
	}

	/**
	 * result.
	 *
	 * @param   result    the result to set
	 * @since   JDK 1.7
	 */
	public void setResult(List<?> result) {
		this.result = result;
	}
}
