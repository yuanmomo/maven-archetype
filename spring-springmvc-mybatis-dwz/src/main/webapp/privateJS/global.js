$.ajaxSubmit = function(op){
	$.ajax({
		type: op.type || 'GET',
		url: op.url,
		data: op.data,
		success: function(response){
			var json = DWZ.jsonEval(response);
			
			if (json.statusCode==DWZ.statusCode.timeout){
				if ($.pdialog) $.pdialog.checkTimeout();
				if (navTab) navTab.checkTimeout();

				alertMsg.error(json.message || DWZ.msg("sessionTimout"), {okCall:function(){
					DWZ.loadLogin();
				}});
			} 
			var json = DWZ.jsonEval(response);
			
			if (json.statusCode==DWZ.statusCode.error){
				if (json.message) alertMsg.error(json.message);
			} else {
				if ($.isFunction(op.callback)) {
					op.callback(response);
				}
			}
		}
	});
};

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function (fmt) { //author: meizz 
	 var o = {
	     "M+": this.getMonth() + 1, //月份 
	     "d+": this.getDate(), //日 
	     "H+": this.getHours(), //小时 
	     "m+": this.getMinutes(), //分 
	     "s+": this.getSeconds(), //秒 
	     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	     "S": this.getMilliseconds() //毫秒 
	 };
	 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	 for (var k in o)
	 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	 return fmt;
};

/**
* 批量设置默认日期
*/
function addDays(items, addDays) {
	var date = new Date();
	if (addDays == null) {
		addDays = 0;
	}
	date.setTime(date.getTime() + 24 * 60 * 60 * 1000 * addDays);
	for ( var key = 0; key < items.length; key++) {
		var obj = $("#" + items[key],navTab.getCurrentPanel());
		var fmt = obj.attr("datefmt");
		var dateStr = null;
		if(fmt === "yyyy-MM-dd"){
			dateStr = date.format(fmt) ;
		}else if(fmt === "yyyy-MM-dd HH:mm:ss"){
			dateStr = date.format("yyyy-MM-dd") + " 00:00:00";
		}else{
			dateStr = date.format(fmt);
		}
		obj.val(dateStr);
	}
}

function setMonthDate(items){
	var date = new Date();
	for ( var key = 0; key < items.length; key++) {
		var obj = $("#" + items[key],navTab.getCurrentPanel());
		var month = null;
		if((date.getMonth() + 1) < 10 ){
			month = "0" + (date.getMonth() + 1);
		}
		var dateStr = date.getFullYear() + "-" + month;
		dateStr += "-01 00:00:00";
		obj.val(dateStr);
	}
}
/**
 * 提取URL中的参数
 */
function getSingleParameter(parameter) {
	//获取URL中的查询字符串参数
	var query = window.location.search;
	query = query.substring(1);

	//这里的pairs是一个字符串数组
	var pairs = query.split("&");//name=myname&password=1234&sex=male&address=nanjing

	for ( var i = 0; i < pairs.length; i++) {
		var sign = pairs[i].indexOf("=");
		//如果没有找到=号，那么就跳过，跳到下一个字符串（下一个循环）。
		if (sign == -1) {
			continue;
		}

		var aKey = pairs[i].substring(0, sign);
		if(aKey == parameter){
			return pairs[i].substring(sign + 1);
		}
	}
}


/**
 * 	设置value的值到obj
 * 
 * @param obj
 * @param value
 */
function setCurrentOperateItemId(obj,value){
	$("#"+obj, navTab.getCurrentPanel()).val(value);
}


/**
 * 	取得当前操作的对象值
 * 
 * @param obj
 * @param value
 */
function getCurrentOprateItemId(obj){
	return $("#"+obj, navTab.getCurrentPanel()).val();
}



/**
 * 	根据指定的模板和数据,生成html代码, 并拼凑在指定的标签中
 * @param appender
 * @param templateName
 * @param dataList
 */
function paginationDataHandler(appender,templateName,dataList){
	var appenderDIV = $("#"+appender,navTab.getCurrentPanel());
	appenderDIV.html("");// 清空table
	if (dataList != null && dataList.length > 0) {
		var html = _.template($('#'+templateName,navTab.getCurrentPanel()).html(),
				dataList);
		appenderDIV.html(html);
		// css tables
		$('table.list', navTab.getCurrentPanel()).cssTable();
		
		// a ajaxToDo
		if ($.fn.ajaxTodo) $("a[target=ajaxTodo]",  navTab.getCurrentPanel()).ajaxTodo();
		
		if ($.fn.combox) $("select.combox", navTab.getCurrentPanel()).combox();
		
		//dialogs
		$("a[target=dialog]", navTab.getCurrentPanel()).each(function(){
			$(this).click(function(event){
				var $this = $(this);
				var title = $this.attr("title") || $this.text();
				var rel = $this.attr("rel") || "_blank";
				var options = {};
				var w = $this.attr("width");
				var h = $this.attr("height");
				if (w) options.width = w;
				if (h) options.height = h;
				
				options.max = eval($this.attr("max") || "false");
				options.mask = eval($this.attr("mask") || "false");
				options.maxable = eval($this.attr("maxable") || "true");
				options.minable = eval($this.attr("minable") || "true");
				options.fresh = eval($this.attr("fresh") || "true");
				options.resizable = eval($this.attr("resizable") || "true");
				options.drawable = eval($this.attr("drawable") || "true");
				options.close = eval($this.attr("close") || "");
				options.param = $this.attr("param") || "";

				var url = unescape($this.attr("href")).replaceTmById($(event.target).parents(".unitBox:first"));
				DWZ.debug(url);
				if (!url.isFinishedTm()) {
					alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
					return false;
				}
				$.pdialog.open(url, rel, title, options);
				
				return false;
			});
		});
	} else {
		alertMsg.info("没有数据");
	}
}


/**
 *  检查指定的元素列表是否选择了值
 */
function validateItems(items){
	for ( var key = 0; key < items.length; key++) {
		var currentItem = $("#" + items[key], $.pdialog.getCurrent());
		var value = currentItem.val();
		if(value == undefined || value == null || value == '' || value == 0){
			alertMsg.info(currentItem.attr("message"));
			return false;
		}
	}
	return true;
}


/**
 * @param response   相应数据
 * @param callback	 自定义的回调函数
 */
function selfCallBack(response){
	var json = DWZ.jsonEval(response);
	
	if (json.statusCode==DWZ.statusCode.timeout){
		alertMsg.error(json.message || DWZ.msg("sessionTimout"), {okCall:function(){
			DWZ.loadLogin();
		}});
		return;
	}
	if (json.statusCode==DWZ.statusCode.error){
		if (json.message) alertMsg.error(json.message);
	}else{
		if (json.message) {
			alertMsg.info(json.message);
			return true;
		}
	}
}

/**
 * 	 检查APK图片详情文件上传列表, 由于一个APK广告只是一张,最多可以有4张图片. 图片有4个图片选择按钮
 * 	 如果只选择了一张, 这样的话, 提交表单时会出现400, 所以,如果没有选择图片的input=file, 不能设置name
 */
function setFileItems(){
	$("input[type='file']", $.pdialog.getCurrent()).each(function() {
		var fname = $(this).val();
		if (fname != undefined && fname != "") {
			var name = $(this).attr("tempName");
			$(this).attr("name",name);
		}
	});
}

/**
 * 	 检查APK图片详情文件上传列表, 由于一个APK广告只是一张,最多可以有4张图片. 图片有4个图片选择按钮
 * 	 如果只选择了一张, 这样的话, 提交表单时会出现400, 所以,如果没有选择图片的input=file, 不能设置name
 */
function resetFileItems(){
	$("input[type='file']", $.pdialog.getCurrent()).each(function() {
		$(this).attr("name","");
		$(this).val("");
	});
}
/**
 * 	改变当前select的值的时候,修改其对应的select的disabled
 * 
 * @param obj
 */
function updateSelectDisable(obj){
	var $select = $(obj);
	var $selectValue = $select.val();
	
	var referTo = $select.attr("referTo");
	if( referTo == undefined || referTo == ""){
		return;
	}
	var referToSelectArray = referTo.split(" ");
	if(referToSelectArray == null || referToSelectArray.length == 0){
		return;
	}
	for ( var key = 0; key < referToSelectArray.length; key++) {
		var referToSelect = $("#" + referToSelectArray[key], $.pdialog.getCurrent());
		if($selectValue == undefined || $selectValue == "" || $selectValue == 0){
			referToSelect.val("");
			referToSelect.change();
			referToSelect.attr("disabled",true);
		}else{
			referToSelect.attr("disabled",false);
		}
	}
}


/**
 * 	检查表单是否更新,哪些字段需要提交
 * 
 * @returns {Boolean}
 */
function dirtyCheck(windowType){
	var hasDirtyItem = false;

	// 检查需要dirtyCheck的item是否有更新值
	var checkItemArray =  null;
	if(windowType == "dialog"){
		checkItemArray = $("[dirtyCheck='true']", $.pdialog.getCurrent());
	}else{
		checkItemArray = $("[dirtyCheck='true']", navTab.getCurrentPanel());
	}
	if(checkItemArray == null || checkItemArray.length == 0 ){
		return hasDirtyItem;
	}
	for ( var key = 0; key < checkItemArray.length; key++) {
		var itemToCheck = $(checkItemArray[key]);
		var newValue = itemToCheck.val();
		var oldValue = itemToCheck.attr("oldValue");
		if(newValue != oldValue){
			// 更新了值, 设置该标签name属性
			itemToCheck.attr("name",itemToCheck.attr("tempName"));
			hasDirtyItem = true;
			
			// 如果dirycheck的item值修改了, 需要提交, 则修改器refId 指向的item Name
			var idInput = null;
			if(windowType == "dialog"){
				idInput = $("#"+itemToCheck.attr("refId"), $.pdialog.getCurrent());
			}else{
				idInput = $("#"+itemToCheck.attr("refId"), navTab.getCurrentPanel());
			}
			if(idInput != undefined && idInput != null){
				idInput.attr("name",idInput.attr("tempName"));
			}
		}
	}
	// 检查页面是否选择了文件
	var fileItemArray = null;
	if(windowType == "dialog"){
		fileItemArray = $("input[type='file']", $.pdialog.getCurrent());
	}else{
		fileItemArray = $("input[type='file']", navTab.getCurrentPanel());
	}
	fileItemArray.each(function() {
		var fname = $(this).val();
		if (fname != undefined && fname != "") {
			hasDirtyItem = true;
			return hasDirtyItem;
		}
	});
	
	return hasDirtyItem;
}



/**
 * 	取得指定id的详细信息
 */
function getItemById(url,itemId,template,appender,selectArray){
	var id = getCurrentOprateItemId(itemId);
	if(id == undefined || id <=0){
		return false;
	}
	$("#"+appender,$.pdialog.getCurrent()).loadUrl(url,{"id":id},function(response){
		var appenderDIV = $("#"+appender,$.pdialog.getCurrent());
		appenderDIV.html("");// 清空table
		data = response.returnValue;
		if (data != null ) {
			var html = _.template($('#'+template,$.pdialog.getCurrent()).html(),
					data);
			appenderDIV.html(html);
			
			// 找到页面中所有的table,入过class != table, 则修改class = table
			tableArray = $("table",$.pdialog.getCurrent());
			tableArray.each(function(){
				var classValue = $(this).attr("class");
				if(classValue != "table"){
					$(this).attr("class","table");
				}
			});
			
			// 如需selectArray不为空,设置值
			if(selectArray != undefined && selectArray != null && selectArray.length >0){
				for ( var key = 0; key < selectArray.length; key++) {
					var selectItem  = $("#"+selectArray[key],$.pdialog.getCurrent());
					
					if(data[selectArray[key]] != undefined && data[selectArray[key]] != null
							&& data[selectArray[key]] !== ""){
						selectItem.val(data[selectArray[key]]);
						selectItem.change();
						selectItem.attr("disabled",false);
					}
				}
			}
			
			//tables 表格处理
			$($.pdialog.getCurrent()).initUI();
		}
	});
}


/**
 * 	检查表单是否更新,哪些字段需要提交
 * 
 * @returns {Boolean}
 */
function updateDirtyForm(windowType){
	var checkItemArray =  null;
	if(windowType == "dialog"){
		checkItemArray = $("[dirtyCheck='true']", $.pdialog.getCurrent());
	}else{
		checkItemArray = $("[dirtyCheck='true']", navTab.getCurrentPanel());
	}
	if(checkItemArray == null || checkItemArray.length == 0 ){
		return;
	}
	for ( var key = 0; key < checkItemArray.length; key++) {
		var itemToCheck = $(checkItemArray[key]);
		var newValue = itemToCheck.val();
		var oldValue = itemToCheck.attr("oldValue");
		if(newValue != oldValue){
			// 更新成功,刷新oldValue的值
			itemToCheck.attr("oldValue",newValue);
			itemToCheck.val(newValue);
		}
	}
}


function ajaxSearch(url,form,template,appender){
	$.ajax({
		type: "POST",
		url: url,
		data: form == null ? {} : $("#" + form,navTab.getCurrentPanel()).serialize(),
		success: function(response){
			var json = DWZ.jsonEval(response);
			
			if (json.statusCode==DWZ.statusCode.timeout){
				if ($.pdialog) $.pdialog.checkTimeout();
				if (navTab) navTab.checkTimeout();

				alertMsg.error(json.message || DWZ.msg("sessionTimout"), {okCall:function(){
					DWZ.loadLogin();
				}});
			} 
			dataList = response.returnValue;
			paginationDataHandler(appender,template,dataList);
		}
	});
}

//Ajax 文件下载
function download(url, formHTML, method){
    // 获取url和data
    if( url && formHTML ){ 
    	var $form = $("<form></form>");
    	$form.attr("target","_blank");
    	$form.attr("action",url);
    	$form.attr("method",method||'post');
    	$form.css("display","none");
    	
    	var HTML = new String(formHTML);
    	$form.html(HTML.toString());
    	
        $form.appendTo('body');
        $form.submit();
        $form.remove();
    };
};



function inputChangeAction(itemId,value){
	var item = $("#" + itemId,navTab.getCurrentPanel());
	if(value != ''){
		item.attr("href",item.attr("href") + value);
	}
}



/**
 * 	onclick="updateAlertConfirm('确定封号吗?','banPhoneUserRemark<%=phoneUser.openuuid%>',
 * '请输入封号原因',{'url':'h/phoneUser/updatePhoneUserStatus.do',
 * 'data':{'openUUID':'<%=phoneUser.openuuid%>','status':'-1'},
 * 'callback':updatePhoneUserStatusCallBack})"
 * 
 * @param title
 * @param checkItem
 * @param errorMessage
 * @param param
 */
function updateAlertConfirm(title,checkItem,errorMessage,param){
	alertMsg.confirm(title, {
		okCall: function(){
			updateAjaxSubmit(checkItem,errorMessage,param);
		}
	});
}
function updateAjaxSubmit(checkItem,errorMessage,param){
	if(checkItem != null ){
		var value = $("#" + checkItem, navTab.getCurrentPanel()).val();
		if(value == undefined || value == null || value == ""){
			alertMsg.error(errorMessage);
			$("#" + checkItem, navTab.getCurrentPanel()).focus();
			return false;
		}else{
			param.data.data = value;
		}
	}
	$.ajaxSubmit({
		type :"POST",
		url : param.url, 
		data : param.data,
		callback:function (response){
			selfCallBack(response);
			var json = DWZ.jsonEval(response);
			if (json.statusCode==DWZ.statusCode.ok){
				param.callback(response);
			}
		}
	});
}
