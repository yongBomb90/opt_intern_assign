/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.common.intercepter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import sns.platform.biz.user.UserVO;

/**
 * <pre>
 * 권한체크 하기위한 인터셉터
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
public class AuthoIntercepter extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthoIntercepter.class);

	/**
	 * 생성자
	 */
	public AuthoIntercepter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("connection.... URL : " + request.getRequestURL().toString());

		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {// 세션에 user가 없다면 로그인으로 리다이렉트
			request.getSession().setAttribute("ConnectionURI", request.getRequestURI());
			response.sendRedirect("/login");
			return false;
		} else {
			request.setAttribute("user", session.getAttribute("user"));
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		UserVO user = (UserVO) request.getAttribute("user");// 유저정보를 보기 위함
		logger.info("disconnection.... USER : " + user.toString());
		logger.info("disconnection.... URL : " + request.getRequestURL().toString());
		logger.info("*************************************************************************");

	}

}
