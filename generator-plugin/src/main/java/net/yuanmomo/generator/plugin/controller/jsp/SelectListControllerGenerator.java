package net.yuanmomo.generator.plugin.controller.jsp;

import net.yuanmomo.util.generator.PaginationBean;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;


public class SelectListControllerGenerator {
	public static List<Method> generator(FullyQualifiedJavaType beanType,String beanName,
			String businessFieldName,String criteriaType){
		List<Method> methodList = new ArrayList<Method>();
		
		Method method = new Method();
        method.addAnnotation("@RequestMapping(value = \"select" + beanName + "List.do\")");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(String.class.getName()));
        method.setName("select" + beanName + "List");
        Parameter param1 = new Parameter(new FullyQualifiedJavaType(Short.class.getName()), "conditionType");
        param1.addAnnotation("@RequestParam(\"conditionType\") ");
        Parameter param2 = new Parameter(FullyQualifiedJavaType.getStringInstance(), "conditionValue");
        param2.addAnnotation("@RequestParam(\"conditionValue\") ");
        Parameter param3 = new Parameter(new FullyQualifiedJavaType(PaginationBean.class.getName()), "paginationBean");
        param3.addAnnotation("@ModelAttribute ");
        Parameter paramModelMap = new Parameter(new FullyQualifiedJavaType(ModelMap.class.getName()), "map");
        method.addParameter(param1); 
        method.addParameter(param2); 
        method.addParameter(param3); 
        method.addParameter(paramModelMap); 
        // 方法body
        method.addBodyLine("try {");
        method.addBodyLine("int currentPage = paginationBean.getPageNum();");
        method.addBodyLine("int pageSize = paginationBean.getNumPerPage(); ");
        method.addBodyLine("");
        method.addBodyLine("if(pageSize < 1){");
        method.addBodyLine("map.put(\"message\",\"pageSize 错误。\"); ");
        method.addBodyLine(ControllerPluginUtil.RETURN);
        method.addBodyLine("}");
        method.addBodyLine("if(currentPage<1){");
        method.addBodyLine("map.put(\"message\",\"currentPage 错误。\"); ");
        method.addBodyLine(ControllerPluginUtil.RETURN);
        method.addBodyLine("}");
        method.addBodyLine("// 构造查询参数");
        method.addBodyLine(""+criteriaType + " param =new " + criteriaType + "();");
        method.addBodyLine("//"+criteriaType + ".Criteria criteria = param.createCriteria();");
        method.addBodyLine("");
        method.addBodyLine("// 根据参数设置查询条件");
        method.addBodyLine("");
        method.addBodyLine("// 取得当前查询的总记录结果");
        method.addBodyLine("int total = this." + businessFieldName + ".count" + beanName + "List(param);");
        method.addBodyLine("if(total == 0){");
        method.addBodyLine("// 没有记录数");
        method.addBodyLine("map.put(\"message\",\"没有记录。\"); ");
        method.addBodyLine(ControllerPluginUtil.RETURN);
        method.addBodyLine("}");
        method.addBodyLine("paginationBean.setTotalCount(total);");
        method.addBodyLine("// 判断当前请求的页码有没有超过总页数");
        method.addBodyLine("int totalPages = PaginationUtil.getPages(total, pageSize);");
        method.addBodyLine("paginationBean.setTotalPages(totalPages);");
        method.addBodyLine("");
        method.addBodyLine("if(currentPage > totalPages){");
        method.addBodyLine("// 当前页超过总页数,取最大数");
        method.addBodyLine("currentPage = totalPages;");
        method.addBodyLine("paginationBean.setPageNum(currentPage);");
        method.addBodyLine("}");
        method.addBodyLine("");
        method.addBodyLine("// 设置排序");
        method.addBodyLine("// param.setOrderByClause(\" id asc \");");
        method.addBodyLine("");
        method.addBodyLine("int start = (currentPage - 1) * pageSize;");
        method.addBodyLine("param.setStart(start);");
        method.addBodyLine("param.setCount(pageSize);");
        method.addBodyLine("");
        method.addBodyLine("List<" + beanName + "> " + StringUtils.uncapitalize(beanName) +"List = this." + businessFieldName + ".select" + beanName + "List(param);");
        method.addBodyLine("");
        method.addBodyLine("paginationBean.setResult(" + StringUtils.uncapitalize(beanName) +"List);  // 返回数据结果");
        method.addBodyLine("map.put(\"message\",JSON.toJSONString(" + StringUtils.uncapitalize(beanName) +"List));");
        method.addBodyLine("} catch (Exception e) {");
        method.addBodyLine("logger.error(\"查询异常\" + e.getMessage());");
        method.addBodyLine("map.put(\"message\", \"查询异常\" + e.getMessage());");
        method.addBodyLine("}"); 
        method.addBodyLine(ControllerPluginUtil.RETURN);
        
		methodList.add(method);
        
        return methodList;
	}
}
