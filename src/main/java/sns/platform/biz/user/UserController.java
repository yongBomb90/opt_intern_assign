/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.user;

import java.util.Map;

import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import sns.platform.common.constant.EntityDTO;
import sns.platform.common.resolver.AttrMap;

/**
 * <pre>
 * 유저에 대한 컨트롤러
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Controller
public class UserController {

	/**
	 * 필요한 서비스 인젝트
	 */
	@Inject
	private UserService service;

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	/**
	 * 생성자 함수
	 */
	public UserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 유저 등록
	 * 
	 * @param map 
	 * 			   등록시 필요한 정보
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ModelAndView createUserFile(AttrMap map) {
		LOG.info("유저 등록");
		EntityDTO dto = new EntityDTO();
		if (map.checkParam("userId", "userPass", "userNick","userType")) {
			String type= (String) map.get("userType");
			if("user".equals(type) || "sns".equals(type)){
				dto.setCode(service.createUser(map));
			}
		}
		return new ModelAndView("jsonView", dto.getDTO());
	}

	/**
	 * 유저 조회
	 * 
	 * @param userNum
	 * 				 조회할 유저 번호
	 * @return
	 */
	@RequestMapping(value = "/user/{userNum}", method = RequestMethod.GET)
	public ModelAndView searchUser(@PathVariable("userNum") String userNum) {
		LOG.info("유저조회 " + userNum);
		EntityDTO dto = new EntityDTO();
		int userNumber = NumberUtils.toInt(userNum, -1);
		Map<String, Object> data = service.search(userNumber);
		if (data == null) {
			return new ModelAndView("jsonView", dto.getDTO());
		}
		dto.setCode(1);
		dto.setData(data);
		return new ModelAndView("jsonView", dto.getDTO());
	}

	/**
	 * 유저 수정
	 * 
	 * @param map 
	 * 			  수정항 유정 정보
	 * @param req
	 * 			 수정후 세션에 추가하기위한 리퀘스트
	 * @return
	 */
	@RequestMapping(value = "/user/modify", method = RequestMethod.POST, consumes = "multipart/form-data") // 회원수정
	public ModelAndView modifyUser(AttrMap map, HttpServletRequest req) {
		LOG.info("회원 수정 " + map.toString());
		LOG.info("회원 아이디 " + map.get("userId"));
		EntityDTO dto = new EntityDTO();
		if (map.getUser() == null) {
			return new ModelAndView("jsonView", dto.getDTO());
		}
		if (map.getUserFile() != null || map.checkParam("userPass") || map.checkParam("userNick")) {
			UserVO user = service.modifyUser(map);
			if (user != null) {
				req.setAttribute("user", user);
				dto.setCode(1);
			}
		}
		return new ModelAndView("jsonView", dto.getDTO());
	}

	/**
	 * 회원탈퇴
	 * 
	 * @param map
	 * 			  탈퇴할 유저정보
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.DELETE) // 회원자기자신만탈퇴
	public ModelAndView removeUser(AttrMap map,HttpServletRequest req) {
		// 세션처리 를 통해 유저얻은 userNum으로 service.remove(int userNum)을통해 삭제
		LOG.info("유저삭제");
		EntityDTO dto = new EntityDTO();
		if (map.getUser() != null ) {
			service.removeUser(map);
		}
		dto.setCode(1);
		return new ModelAndView("jsonView", dto.getDTO());
	}

	/**
	 * 로그인 
	 * 
	 * @param map 
	 * 				로그인 에 필요한 정보(userId,userPass)
	 * @param req
	 * 				로그인후 세션에 넣어주기위해 받은 request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST) // 로그인처리
	public ModelAndView login(AttrMap map, HttpServletRequest req) {
		LOG.info("로그인 ");
		EntityDTO dto = new EntityDTO();
		UserVO user = null;
		if (map.checkParam("userId", "userPass","userType")) {
			user = service.login(map);
			if (user == null) {
				return new ModelAndView("jsonView", dto.getDTO());
			}
			req.setAttribute("user", user);
			dto.setCode(1);
			dto.setData(user.getUserDTO());
			return new ModelAndView("jsonView", dto.getDTO());
		} 
		return new ModelAndView("jsonView", dto.getDTO());
		
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET) // TEST
	public String loginTest() {
		return "login";
	}

	/**
	 * 로그아웃
	 * 
	 * @param req 
	 * 				로그아웃시 세션에 지워지기위함
	 * @param res
	 * 				쿠키 조작
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET) // 로그아웃처리
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse res) {
		LOG.info("로그아웃 ");
		req.getSession().removeAttribute("user");
		EntityDTO dto = new EntityDTO();
		dto.setCode(1);
		return new ModelAndView("jsonView", dto.getDTO());
	}

	/**
	 * 유저아이디 체크
	 * 
	 * @param userId
	 * 				 체크할 유저아이디
	 * @param map
	 * 				현재유저 정보를 담음 맵	
	 * @return
	 */
	@RequestMapping(value = "/user/checkid/{userId}", method = RequestMethod.GET)
	public ModelAndView checkid(@PathVariable("userId") String userId, AttrMap map) {
		LOG.info("아이디체크 " + userId);
		EntityDTO dto = new EntityDTO();
		if (userId == map.get("userId")) {
			dto.setCode(1);
			return new ModelAndView("jsonView", dto.getDTO());
		}
		dto.setCode(service.checkId(userId));
		return new ModelAndView("jsonView", dto.getDTO());
	}

	/**
	 * 유저 이름 체크
	 * 
	 * @param userNick
	 * 					체크할 유저이름
	 * @param map
	 * 					현재유저 정보를 담음 맵	
	 * @return
	 */
	@RequestMapping(value = "/user/checkname/{userNick}", method = RequestMethod.GET)
	public ModelAndView userNick(@PathVariable("userNick") String userNick, AttrMap map) {
		LOG.info("이름체크 " + userNick+" 세션 "+map.get("userNick"));
		EntityDTO dto = new EntityDTO();
		if (userNick.equals((String)map.get("userNick"))) {
			dto.setCode(1);
			dto.setmessage("본인의 이름입니다");
			return new ModelAndView("jsonView", dto.getDTO());
		}
		dto.setCode(service.checkNick(userNick));
		return new ModelAndView("jsonView", dto.getDTO());
	}
	
	
	/**
	 * 유저 비밀번호 체크
	 * 
	 * @param map
	 * 			  유저 정보를 담음 맵
	 * @return
	 */
	@RequestMapping(value = "/user/checkpass", method = RequestMethod.POST)
	public ModelAndView checkpass(AttrMap map) {
		LOG.info("비밀번호 " + map.get("userPass"));
		LOG.info("비밀번호 " + map.getUser());
		EntityDTO dto = new EntityDTO();
		if (map.getUser() != null && map.checkParam("userPass")) {
			dto.setCode(service.checkPass(map));
		}
		return new ModelAndView("jsonView", dto.getDTO());
	}
}
