package net.yuanmomo.dwz.bean;

import net.yuanmomo.dwz.util.PaginationBean;


public class AjaxResponseBean {
	/**
	 * navTabAjaxDone是DWZ框架中预定义的表单提交回调函数．
	 * 服务器转回navTabId可以把那个navTab标记为reloadFlag=1, 下次切换到那个navTab时会重新载入内容. 
	 * callbackType如果是closeCurrent就会关闭当前tab
	 * 只有callbackType="forward"时需要forwardUrl值
	 * navTabAjaxDone这个回调函数基本可以通用了，如果还有特殊需要也可以自定义回调函数.
	 * 如果表单提交只提示操作是否成功, 就可以不指定回调函数. 框架会默认调用DWZ.ajaxDone()
	 * <form action="/user.do?method=save" onsubmit="return validateCallback(this, navTabAjaxDone)">
	 * 
	 * form提交后返回json数据结构statusCode=DWZ.statusCode.ok表示操作成功, 做页面跳转等操作. statusCode=DWZ.statusCode.error表示操作失败, 提示错误原因. 
	 * statusCode=DWZ.statusCode.timeout表示session超时，下次点击时跳转到DWZ.loginUrl
	 * {"statusCode":"200", "message":"操作成功", "navTabId":"navNewsLi", "forwardUrl":"", "callbackType":"closeCurrent"}
	 * {"statusCode":"300", "message":"操作失败"}
	 * {"statusCode":"301", "message":"会话超时"}
	 * 
	 */
	private int statusCode;
	private Object message;
	private String navTabId = "";
	private String rel = "";
	private String callbackType = "";
	private String forwardUrl = "";
	
	/**
	 * returnValue: 后台相应返回的json数据.
	 * @since JDK 1.6
	 */
	private Object returnValue = "";

	public static final int DWZ_STATUSCODE_OK = 200;
	public static final String DWZ_STATUSCODE_OK_MESSAGE = "操作成功";
	public static final String DWZ_STATUSCODE_NO_DATA_MESSAGE = "没有数据结果";
	
	public static final int DWZ_STATUSCODE_ERROR = 300;
	public static final String DWZ_STATUSCODE_ERROR_MESSAGE = "操作失败";
	
	public static final String DWZ_STATUSCODE_PARAMETER_INVALID_ERROR_MESSAGE = "提交参数格式不正确";
	
	public static final int DWZ_STATUSCODE_TIMEOUT = 301;
	public static final String DWZ_STATUSCODE_TIMEOUT_MESSAGE = "会话超时";
	
	public static final int DWZ_STATUSCODE_PERMISSION_DENY = 302;
	public static final String DWZ_STATUSCODE_PERMISSION_DENY_MESSAGE = "权限不足";
	
	public AjaxResponseBean() {}

	private AjaxResponseBean(int statusCode, Object message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	private AjaxResponseBean(int statusCode, String message,Object returnValue) {
		this.statusCode = statusCode;
		this.message = message;
		this.returnValue = returnValue;
	}
	public AjaxResponseBean(int statusCode, String message, String navTabId,
			String rel, String callbackType, String forwardUrl) {
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.rel = rel;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
	}
	public static class Const{
		/************************************* 操作失败 **********************************************/
		public static AjaxResponseBean TIMEOUT_RESPONSE_BEAN =
				new AjaxResponseBean(DWZ_STATUSCODE_TIMEOUT, DWZ_STATUSCODE_TIMEOUT_MESSAGE);
		
		public static AjaxResponseBean NO_PERMISSION_RESPONSE_BEAN =
				new AjaxResponseBean(DWZ_STATUSCODE_PERMISSION_DENY, DWZ_STATUSCODE_PERMISSION_DENY_MESSAGE);
		
		public static AjaxResponseBean ERROR_RESPONSE_BEAN =
				new AjaxResponseBean(DWZ_STATUSCODE_ERROR, DWZ_STATUSCODE_ERROR_MESSAGE);
		
		public static AjaxResponseBean PARAMETER_INVALID_ERROR_RESPONSE_BEAN =
				new AjaxResponseBean(DWZ_STATUSCODE_ERROR,DWZ_STATUSCODE_PARAMETER_INVALID_ERROR_MESSAGE );
		
		/************************************* 操作成功 **********************************************/
		public static AjaxResponseBean NO_DATA_RESPONSE_BEAN =
				new AjaxResponseBean(DWZ_STATUSCODE_OK,DWZ_STATUSCODE_NO_DATA_MESSAGE );
		
		public static AjaxResponseBean SUCCESS_RESPONSE_BEAN =
				new AjaxResponseBean(DWZ_STATUSCODE_OK, DWZ_STATUSCODE_OK_MESSAGE);
	}

	public static AjaxResponseBean getReturnValueResponseBean(Object returnObj) {
		return new AjaxResponseBean(DWZ_STATUSCODE_OK, DWZ_STATUSCODE_OK_MESSAGE,returnObj);
	}

	public static AjaxResponseBean getReturnValueResponseBean(int statusCode,
			String message,Object returnObj) throws Exception {
		return new AjaxResponseBean(statusCode,message, returnObj);
	}
	
	public static AjaxResponseBean getErrorResponseBean(String message)  {
		return new AjaxResponseBean(DWZ_STATUSCODE_ERROR,message);
	}

	public static AjaxResponseBean getNoDataReturnValueResponseBean() throws Exception {
		return getNoDataReturnValueResponseBean(null);
	}
	public static AjaxResponseBean getNoDataReturnValueResponseBean(PaginationBean paginationBean) throws Exception {
		if(paginationBean == null){
			paginationBean  = new PaginationBean();
		}
		return new AjaxResponseBean(DWZ_STATUSCODE_OK,DWZ_STATUSCODE_NO_DATA_MESSAGE,paginationBean);
	}
	public int getStatusCode() {
		return statusCode;
	}
	public Object getMessage() {
		return message;
	}
	public String getNavTabId() {
		return navTabId;
	}
	public String getRel() {
		return rel;
	}
	public String getCallbackType() {
		return callbackType;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public Object getReturnValue() {
		return returnValue;
	}
}
