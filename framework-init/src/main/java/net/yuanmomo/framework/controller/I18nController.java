package net.yuanmomo.framework.controller;

import net.yuanmomo.framework.exception.EC;
import net.yuanmomo.util.NumberUtil;
import net.yuanmomo.util.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
@RequestMapping("/test/i18n/")
public class I18nController {
    private static Logger logger = LoggerFactory.getLogger(I18nController.class);

    /**
     * 切换国际化语言,langType=zh,中文；langType=en,英文。
     *
     * @param langType  切换的语言类型。
     * @param request
     * @return
     */
    @RequestMapping("lang.do")
    @ResponseBody
    public String lang(
            @RequestParam("langType") String langType,
            HttpServletRequest request){
        Locale locale = null;
        if("zh".equalsIgnoreCase(langType)){
            locale = new Locale("zh", "CN");
        }
        else if("en".equalsIgnoreCase(langType)){
            locale = new Locale("en", "US");
        }else {
            LocaleContextHolder.getLocale();
        }
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
        return null;
    }

    /**
     * 国际化测试。
     *
     * @param id  如果id 为正数，那么返回"RESULT"所对应得消息。如果为负数，则提示错误。
     * @param map 视图。
     * @return
     */
    @RequestMapping(value = "test.do")
    public String test(@RequestParam("id")  Long id,
                               ModelMap map) {
        try {
            if(NumberUtil.isPositive(id)){
                throw EC.ID_INVALID;
            }
            String result = "RESULT";
            map.put("message",result);
        } catch (BaseException e1) {
            map.put("message", e1.getKey());
        } catch (Exception e2){
            map.put("message", EC.SYSTEM_ERROR.getKey());
        }
        return "out";
    }
}