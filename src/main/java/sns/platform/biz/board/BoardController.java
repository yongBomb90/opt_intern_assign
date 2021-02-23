/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.board;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
 *게시글에 대한 Controller 클래스
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 01
 * @updated 2016. 11. 01
 */
@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	/**
	 * service - 보드의 로직 처리 service
	 */
	@Inject
	private BoardService service;

	/**
	 * 생성자
	 */
	public BoardController() {
		super();
	}

	/**
	 * 게시글 등록
	 *
	 * @param map
	 *            - 파라미터들로된 멥(String,Object)과 현재 로그인한 유저 접속 URI를 담은 객체
	 * @param req
	 *            - 다수파일을 받기위한 MultipartHttpServletRequest
	 */
	@RequestMapping(value = "/board", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ModelAndView createBoardFile(AttrMap map) {
		logger.info("게시판 작성 ");
		EntityDTO dto = new EntityDTO(); // responce바디 구조를 담아놓은 객체
		if (map.checkParam("boardContent")) {// 피라미터 boardContent의 null체크
			dto.setCode(service.createBoard(map));// 서비스에게 데이터를 넘겨 게시글을 추가시킴
		}
		return new ModelAndView("jsonView", dto.getDTO());// responce바디 구조를 만들어서
															// client 측으로 보내줌
	}

	/**
	 * 게시글 상세조회
	 *
	 * @param boardNum
	 *            - 조회하고 싶은 게시판고유번호
	 */
	@RequestMapping(value = "/board/{boardNum}", method = RequestMethod.GET)
	public ModelAndView searchBoard(@PathVariable("boardNum") String boardNum) {
		logger.info("게시판 조회 boardNum " + boardNum);
		EntityDTO dto = new EntityDTO();// responce바디 구조를 담아놓은 객체
		int boardNumber = NumberUtils.toInt(boardNum, -1);
		if (boardNumber < 0) {
			return new ModelAndView("jsonView", dto.getDTO());
		}
		Map<String, Object> data = service.searchBoard(boardNumber);// boardNum을통한
																	// 게시글 조회
		if (data == null) {// 게시글이 없다면 실패 response바디 리턴
			return new ModelAndView("jsonView", dto.getDTO());
		}
		dto.setCode(1);// 성공코드로 set
		dto.setData(data);// 게시글을 data로 set
		return new ModelAndView("jsonView", dto.getDTO());// responce바디 구조를 만들어서
															// client 측으로 보내줌
	}

	/**
	 * 게시글 리스트 및 검색 조회
	 *
	 * @param pageNum
	 *            - 리스트형식에서 리스트의 최대길이
	 * @param lastBoardNum
	 *            - client가 받은 마지막 게시글번호
	 * @param map
	 *            - 파라미터들로된 멥(String,Object)과 현재 로그인한 유저 접속 URI를 담은 객체
	 */
	@RequestMapping(value = "/boards", method = RequestMethod.GET)
	public ModelAndView searchBoards(AttrMap map) {
		
		EntityDTO dto = new EntityDTO();// responce바디 구조를 담아놓은 객체
		List<Map<String, Object>> list = null;
		if (map.checkParam("pageNum", "lastBoardNum")) {
			map.put("pageNum", NumberUtils.toInt((String) map.get("pageNum"), 5));
			map.put("lastBoardNum", NumberUtils.toInt((String) map.get("lastBoardNum"), -1));
		} else {
			map.put("pageNum", 5);
			map.put("lastBoardNum", -1);
		}
		list = service.searchBoards(map);
		if (list == null || list.isEmpty()) {// 리스트가 없을경우
			return new ModelAndView("jsonView", dto.getDTO());// 실패 메시지 리턴
		}
		dto.setData(list);// data에 list를 set
		dto.setCode(1);// 성공코드 set
		return new ModelAndView("jsonView", dto.getDTO());// responce바디 구조를 만들어서
															// client 측으로 보내줌
	}

	/**
	 * 게시글 수정
	 *
	 * @param boardNum
	 *            - 수정하고싶은 게시글 고유번호
	 * @param map
	 *            - 파라미터들로된 멥(String,Object)과 현재 로그인한 유저 접속 URI를 담은 객체
	 * @param req
	 *            - 다수파일을 받기위한 MultipartHttpServletRequest
	 */
	@RequestMapping(value = "/board/{boardNum}", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ModelAndView modifyBoardFile(@PathVariable("boardNum") String boardNum, AttrMap map) {
		logger.info("게시판 수정  boardNum " + boardNum);
		EntityDTO dto = new EntityDTO();// responce바디 구조를 담아놓은 객체
		int boardNumber = NumberUtils.toInt(boardNum, -1);
		if (boardNumber < 0) {
			return new ModelAndView("jsonView", dto.getDTO());
		}
		map.put("boardNum", boardNumber);// map에 boardNum 게시글 고유번호 set
		dto.setCode(service.modifyBoard(map));// 수정 후 return 1 : 실패 return 0 :
												// 성공 code set
		return new ModelAndView("jsonView", dto.getDTO());// responce바디 구조를 만들어서
															// client 측으로 보내줌
	}

	/**
	 * 게시글 삭제
	 *
	 * @param boardNum
	 *            - 삭제하고싶은 게시글 고유번호
	 * @param map
	 *            - 파라미터들로된 멥(String,Object)과 현재 로그인한 유저 접속 URI를 담은 객체
	 */
	@RequestMapping(value = "/board/{boardNum}", method = RequestMethod.DELETE)
	public ModelAndView removeBoard(@PathVariable("boardNum") String boardNum, AttrMap map) {
		logger.info("게시판 삭제 boardNum " + boardNum);
		EntityDTO dto = new EntityDTO();// responce바디 구조를 담아놓은 객체
		int boardNumber = NumberUtils.toInt(boardNum, -1);
		if (boardNumber < 0) {
			return new ModelAndView("jsonView", dto.getDTO());
		}
		map.put("boardNum", boardNumber);
		dto.setCode(service.removeBoard(map));// 테스트중 무조건 성공
		return new ModelAndView("jsonView", dto.getDTO());// responce바디 구조를 만들어서
															// client 측으로 보내줌
	}
	
	
	
}
