package net.yuanmomo.dwz.controller;

import javax.servlet.http.HttpServletRequest;

import net.yuanmomo.dwz.bean.Test;
import net.yuanmomo.dwz.business.mybatis.TestBusiness;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/result.do")
public class TestController {

	@Autowired
	private TestBusiness testBusiness = null;
	public void setTestBusiness(TestBusiness testBusiness) {
		this.testBusiness = testBusiness;
	}

}
