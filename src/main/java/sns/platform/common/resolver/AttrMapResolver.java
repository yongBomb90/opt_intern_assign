/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.common.resolver;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.core.MethodParameter;

import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import sns.platform.biz.user.UserVO;

/**
 * <pre>
 * AttrMap 에 대한 리졸버
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
public class AttrMapResolver implements HandlerMethodArgumentResolver {

	/**
	 * 생성자
	 */
	public AttrMapResolver() {
		super();
	}

	@Override
	public boolean supportsParameter(MethodParameter parmam) {
		return AttrMap.class.isAssignableFrom(parmam.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter param, ModelAndViewContainer mv, NativeWebRequest wreq,
			WebDataBinderFactory wdf) throws Exception {
		AttrMap map = new AttrMap();// Attrmap 생성
		HttpServletRequest req = (HttpServletRequest) wreq.getNativeRequest();
		Enumeration<String> list = req.getParameterNames();
		String contentType = req.getContentType();
		String key = "";
		String value[] = null;

		map.setURI(req.getRequestURI().toString());

		if ( req.getSession().getAttribute("user") != null) {
			map.setUser((UserVO) req.getSession().getAttribute("user"));
		}

		// 받은 파라미터를 맵에 넣어준다
		while (list.hasMoreElements()) {
			key = list.nextElement();
			value = req.getParameterValues(key);
			
			if (value.length == 1) {
				map.put(key, value[0].toString());
				System.out.println("파라미터 키: "+key+" 값 :"+value[0].toString());
			} else {
				map.put(key, value);
				System.out.println("파라미터 키: "+key+" 값 :"+value);
			}
		}

		// contentType 검사
		if (contentType == null || !contentType.contains("multipart/form-data")) {
			return map;
		}

		//맵에 파일 데이터를 넣어준다
		MultipartRequest multipartReq = (MultipartRequest) wreq.getNativeRequest();
		List<MultipartFile> userFile = multipartReq.getFiles("userFile");
		List<MultipartFile> files = multipartReq.getFiles("files");

		if (userFile != null && !userFile.isEmpty()) {
			map.setUserFile(userFile.get(0));
		}

		if (files != null && !files.isEmpty()) {
			map.setFiles(files);
		}

		return map;

	}

}
