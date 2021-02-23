package sns.platform.biz.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sns.platform.common.resolver.AttrMap;
/**
 * <pre>
 * 화면에 대한 컨트롤러
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Controller
public class ViewController {
	
	/**
	 *  home 페이지
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String createUserFile(AttrMap map) {
			return "home/home";
	}
	@RequestMapping(value = "/boardView", method = RequestMethod.GET)
	public String boardsFile(AttrMap map, HttpServletRequest req) {
			req.setAttribute("user",map.getUser());
			return "board/boards";
	}
	
	/**
	 * 404에러 페이지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/error/404", method = RequestMethod.GET)
	public String eror404() {
		return "errorPage/404";
	}
	@RequestMapping(value = "/testPage", method = RequestMethod.GET)
	public String testPage() {
		return "test";
	}


}
