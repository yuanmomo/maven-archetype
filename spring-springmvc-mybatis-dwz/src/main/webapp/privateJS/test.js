/**
 * 根据用户选择的条件和输入的值搜索配置选项
 */
function selectTestListByConditon(){
	// 取得当前选择的搜索类型
	var searchConditionType = $("#searchConditionType", navTab.getCurrentPanel()).val();
	
	// 检查页面下拉框选择数据是否正确
	if( searchConditionType < 0){
		return;
	}
	
	// 设置用户输入的搜索值
	var condtionValue = $("#searchConditionValue", navTab.getCurrentPanel()).val();
	if(condtionValue == ""){
		alertMsg.error("请输入查询条件"); 
		return;
	}
	// 设置提交参数
	$("#conditionType", navTab.getCurrentPanel()).val(searchConditionType);
	$("#conditionValue", navTab.getCurrentPanel()).val(condtionValue);
	$("#pageNum",navTab.getCurrentPanel()).val(1); // 设置当前显示第一页
	$("#numPerPage",navTab.getCurrentPanel())
		.val($("#numPerPageSelect",navTab.getCurrentPanel()).val()); // 设置pageSize
	// 调用分页函数
	dwzPageBreak({targetType:"navTab", rel:"testDetaiList", 
		data:$("#pagerForm",navTab.getCurrentPanel()).serialize(),
		callback : "selectTestListCallback(response)"});
}

/**
 * 	分页显示时,回调函数,页面显示分页数据
 * @param response
 */
function selectTestListCallback(response){
	var valueObj = response.returnValue;
	dataList = valueObj.result;
	paginationDataHandler("testDetaiList","testDetailTemplate",dataList);
}


/**
 * 保存渠道排序
 * 
 */
function saveTestOrder(){
	var isDirty = dirtyCheck("navTab");
	if(!isDirty){
		alertMsg.error("没有修改任何信息");
		return false;
	}
	// 检验表单
	if (!$("#testDetailListForm", $.pdialog.getCurrent()).valid()) {
		return false;
	}
	
	$.ajaxSubmit({
		type:"POST",
		url:"test/batchUpdateSaveTest.do", 
		data :$("#testDetailListForm",navTab.getCurrentPanel()).serialize(),
		callback:function (response){
			alertMsg.info(response.message);
			updateDirtyForm("navTab");// 更新dirty form 页面数据
		}
	});
}
function selectTestList(){
	$("#pageNum",navTab.getCurrentPanel()).val(1); // 设置当前显示第一页
	$("#numPerPage",navTab.getCurrentPanel())
		.val($("#numPerPageSelect",navTab.getCurrentPanel()).val()); // 设置pageSize
	// 调用分页函数
	dwzPageBreak({targetType:"navTab", rel:"adTaskDetaiList", 
		data:$("#pagerForm",navTab.getCurrentPanel()).serialize(),
		callback : "selectTestListCallback(response)"});
}



function saveTest(formID,option){
	if(option != "update" && option != "insert"){
		return false;
	}
	if(option == "update"){ // 更新操作需要先判断页面是否有元素的值修改了
		var isDirty = dirtyCheck("dialog");
		if(!isDirty){
			alertMsg.error("没有对平台信息进行修改");
			return false;
		}
	}
	
	// 检验表单
	if (!$("#"+formID, $.pdialog.getCurrent()).valid()) {
		return false;
	}
	return true;
}

