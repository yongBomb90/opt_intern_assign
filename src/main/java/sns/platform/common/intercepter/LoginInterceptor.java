/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.common.intercepter;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import sns.platform.biz.user.UserVO;
/**
 * <pre>
 * 로그인 회원수정시 세션에 유저를 담기위한 인터셉터
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 생성자
	 */
	public LoginInterceptor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			request.setAttribute("user", session.getAttribute("user"));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		UserVO user = (UserVO) request.getAttribute("user");
			
		if (user != null) {
			String URI = (String) request.getSession().getAttribute("ConnectionURI");
			request.getSession().setAttribute("user", user);
			if (URI != null) {
				request.getSession().removeAttribute("ConnectionURI");
				response.sendRedirect(URI);
				return;
			}

		}
		
	}

}
