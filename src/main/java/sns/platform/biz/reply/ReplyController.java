/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.reply;

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
 * 댓글에 대한 컨트롤러
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Controller
public class ReplyController {

	private static final Logger LOG = LoggerFactory.getLogger(ReplyController.class);

	/**
	 * 필요한 서비스 인젝트
	 */
	@Inject
	private ReplyService service;

	/**
	 * 생성자
	 */
	public ReplyController() {
		super();
	}

	/**
	 * 댓글 등록
	 * 
	 * @param boardNum
	 * 				   등록하고자하는 게시글 번호
	 * @param map
	 * 				  등록시 필요한 정보
	 * @return
	 */
	@RequestMapping(value = "/board/{boardNum}/reply", method = RequestMethod.POST)
	public ModelAndView createReply(@PathVariable("boardNum") String boardNum, AttrMap map) {
		LOG.info("댓글 등록 boardNum " + boardNum + "  " + map.toMapString());
		EntityDTO dto = new EntityDTO();
		int boardNumber = NumberUtils.toInt(boardNum, -1);
		if (boardNumber < 0) {
			return new ModelAndView("jsonView", dto.getDTO());
		}
		map.put("boardNum", boardNumber);
		if (map.checkParam("replyContent")) {
			dto.setCode(service.createReply(map));// 게시글 3번으로 무조건 입력(댓글 입력 확인위해0
			return new ModelAndView("jsonView", dto.getDTO());
		}
		return new ModelAndView("jsonView", dto.getDTO());
	}

	/**
	 * 수정하고싶은 댓글
	 * 
	 * @param map
	 * 			   -수정에 필요한 정보(사용자)
	 * @param boardNum
	 * 			   -수정할 댓글이 소속된 게시글 번호
	 * @param replyNum
	 * 			   -수정할 댓글번호
	 * @return
	 */
	@RequestMapping(value = "/board/{boardNum}/reply/{replyNum}", method = RequestMethod.POST)
	public ModelAndView modifyReply(AttrMap map, @PathVariable("boardNum") String boardNum,
			@PathVariable("replyNum") String replyNum) {
		LOG.info("댓글 수정 boardNum " + boardNum + "replynum " + replyNum + " replyContent " + map.get("replyContent"));
		EntityDTO dto = new EntityDTO();
		int boardNumber = NumberUtils.toInt(boardNum, -1);
		int replyNumber = NumberUtils.toInt(replyNum, -1);
		if (boardNumber < 0 || replyNumber < 0) {
			return new ModelAndView("jsonView", dto.getDTO());
		}
		map.put("boardNum", boardNumber);
		map.put("replyNum", replyNumber);
		if (map.checkParam("replyContent")) {
			dto.setCode(service.modifyReply(map));
			return new ModelAndView("jsonView", dto.getDTO());
		}
		return new ModelAndView("jsonView", dto.getDTO());
	}

	/**
	 * 댓글 삭제
	 * 
	 * @param boardNum
	 * 					-삭제할 댓글이 소속된 게시글 번호
	 * @param replyNum
	 * 					-삭제할 댓글 번호
	 * @param map
	 * 					-삭제시 필요한 정보(사용자와 작성자 비교)
	 * @return
	 */
	@RequestMapping(value = "/board/{boardNum}/reply/{replyNum}", method = RequestMethod.DELETE)
	public ModelAndView deleteReply(@PathVariable("boardNum") String boardNum,
			@PathVariable("replyNum") String replyNum, AttrMap map) {
		LOG.info("댓글 삭제 boardNum " + boardNum + "replynum " + replyNum);
		EntityDTO dto = new EntityDTO();
		int boardNumber = NumberUtils.toInt(boardNum, -1);
		int replyNumber = NumberUtils.toInt(replyNum, -1);
		map.put("boardNum", boardNumber);
		map.put("replyNum", replyNumber);
		dto.setCode(service.removeReply(map));
		return new ModelAndView("jsonView", dto.getDTO());
	}

	/**
	 * 하나의 게시글에 댓글 리스트 
	 * 
	 * @param boardNum
	 * 					조회할 게시글 번호
	 * @param map
	 * 					조회시 필요한 정보 (lastReplyNum,pageNum)
	 * @return
	 */
	@RequestMapping(value = "/board/{boardNum}/replys", method = RequestMethod.GET)
	public ModelAndView searchReplys(@PathVariable("boardNum") String boardNum, AttrMap map) {
		LOG.info("댓글 리스트 boardNum " + boardNum + "lastReplyNum " + map.get("lastReplyNum") + " pageNum "
				+ map.get("pageNum"));
		EntityDTO dto = new EntityDTO();
		int boardNumber = NumberUtils.toInt(boardNum, -1);
		List<Map<String, Object>> data = null;
		map.put("boardNum", boardNumber);
		if (map.checkParam("pageNum", "lastReplyNum")) {
			map.put("pageNum", NumberUtils.toInt((String) map.get("pageNum"), 5));
			map.put("lastReplyNum", NumberUtils.toInt((String) map.get("lastReplyNum"), -1));

		} else {
			map.put("pageNum", 5);
			map.put("lastReplyNum", -1);
		}
		data = service.searchReplys(map);
		if (data == null || data.isEmpty()) {
			return new ModelAndView("jsonView", dto.getDTO());
		}
		dto.setCode(1);
		dto.setData(data);
		return new ModelAndView("jsonView", dto.getDTO());
	}

}
