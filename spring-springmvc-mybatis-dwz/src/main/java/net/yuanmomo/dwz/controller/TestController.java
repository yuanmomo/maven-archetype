package net.yuanmomo.dwz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.yuanmomo.dwz.bean.AjaxResponseBean;
import net.yuanmomo.dwz.bean.Test;
import net.yuanmomo.dwz.bean.TestParam;
import net.yuanmomo.dwz.business.mybatis.TestBusiness;
import net.yuanmomo.dwz.util.CollectionUtil;
import net.yuanmomo.dwz.util.PaginationUtil;
import net.yuanmomo.dwz.util.PaginationUtil.PaginationBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/backend/test/")
public class TestController {
	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	@Autowired private TestBusiness testBusiness;
	
	@RequestMapping(value = "selectTestList.do")
	@ResponseBody
	public AjaxResponseBean selectTestList(
//			@RequestParam("conditionType") Short conditionType,
//			@RequestParam("conditionValue") String conditionValue,
			@ModelAttribute PaginationBean paginationBean){
		try {
			int currentPage = paginationBean.getPageNum();
			int pageSize = paginationBean.getNumPerPage(); 
			
			if(pageSize < 1){
				return AjaxResponseBean.Const.PARAMETER_INVALID_ERROR_RESPONSE_BEAN; 
			}
			if(currentPage<1){
				return AjaxResponseBean.Const.PARAMETER_INVALID_ERROR_RESPONSE_BEAN; 
			}
			// 构造查询参数
			TestParam param =new TestParam();
//			TestParam.Criteria criteria = param.createCriteria();
			
			// 根据参数设置查询条件
			
			// 取得当前查询的总记录结果
			int total = this.testBusiness.countTestList(param);
			if(total == 0){ // 没有记录数
				return AjaxResponseBean.getNoDataReturnValueResponseBean();
			}
			paginationBean.setTotalCount(total);
			// 判断当前请求的页码有没有超过总页数
			int totalPages = PaginationUtil.getPages(total, pageSize);
			paginationBean.setTotalPages(totalPages);
			
			if(currentPage > totalPages){ // 当前页超过总页数,取最大数
				currentPage = totalPages;
				paginationBean.setPageNum(currentPage);
			}
			
			// 设置排序
			// param.setOrderByClause(" id asc ");
			
			int start = (currentPage - 1) * pageSize;
			param.setStart(start);
			param.setCount(pageSize);
			
			List<Test> configList = this.testBusiness.selectTestList(param);
			
			paginationBean.setResult(configList);  // 返回数据结果
			return AjaxResponseBean.getReturnValueResponseBean(paginationBean);
		} catch (Exception e) {
			logger.error("查询异常" + e.getMessage());
			return AjaxResponseBean.getErrorResponseBean("查询异常" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "updateSaveTest.do")
	@ResponseBody
	public AjaxResponseBean updateSaveTest(
			@ModelAttribute Test  test){
		try {
			if(test == null ){
				// || NumberUtil.isNotPositive(test.getId())){
				return AjaxResponseBean.Const.PARAMETER_INVALID_ERROR_RESPONSE_BEAN;
			}
			int updateCount = this.testBusiness.update(test);
			if(updateCount >0 ){
				return AjaxResponseBean.Const.SUCCESS_RESPONSE_BEAN;
			}
			return AjaxResponseBean.Const.ERROR_RESPONSE_BEAN;
		} catch (Exception e) {
			logger.error("更新异常" + e.getMessage());
			return AjaxResponseBean.getErrorResponseBean("更新异常" + e.getMessage());
		}
	}
	
	
	static class TestList{
		private List<Test> testList;
		public List<Test> getTestList() {
			return testList;
		}
		public void setTestList(List<Test> testList) {
			this.testList = testList;
		}
	}
	
	@RequestMapping(value = "batchUpdateSaveTest.do")
	@ResponseBody
	public AjaxResponseBean batchUpdateSaveTest(
			@ModelAttribute TestList  testList,
			HttpServletRequest request, ModelMap map){
		try {
			if(testList != null && CollectionUtil.isNotNull(testList.getTestList())){
				int updateCount = this.testBusiness.update(testList.getTestList());
				if(updateCount >0 ){
					return AjaxResponseBean.Const.SUCCESS_RESPONSE_BEAN;
				}
			}
			return AjaxResponseBean.Const.ERROR_RESPONSE_BEAN;
		} catch (Exception e) {
			logger.error("批量更新异常" + e.getMessage());
			return AjaxResponseBean.getErrorResponseBean("批量更新异常" + e.getMessage());
		}
	}
	
}
