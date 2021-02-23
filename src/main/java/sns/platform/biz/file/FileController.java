/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.file;

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
 * 실제 파일정보에 대한 컨트롤러
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Controller
public class FileController {

	private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

	/**
	 * 파일 서비스 인젝트
	 */
	@Inject
	private FileService service;

	/**
	 * 생성자 함수
	 */
	public FileController() {
		super();
	}

	/**
	 * 한게시물에 대한 파일의 목록
	 * 
	 * @param boardNum
	 *            조회할 게시글 번호
	 * 
	 * @return
	 */
	@RequestMapping(value = "/board/{boardNum}/files", method = RequestMethod.GET)
	public ModelAndView searchFiles(@PathVariable("boardNum") String boardNum) {
		LOG.info("파일리스트" + boardNum + "");
		EntityDTO dto = new EntityDTO();
		int boardNumber = NumberUtils.toInt(boardNum, -1);
		List<Map<String, Object>> data = service.searchFiles(boardNumber);
		if (data == null || data.isEmpty()) {
			return new ModelAndView("jsonView", dto.getDTO());
		}
		dto.setData(data);
		dto.setCode(1);
		return new ModelAndView("jsonView", dto.getDTO());
	}

	/**
	 * 파일의 실제 데이터를 보낸다
	 * 
	 * @param boardNum
	 *            게시글 번호
	 * @param fileNum
	 *            파일 번호
	 * @param map
	 *            사용자 정보및 뷰설정(view)
	 * @return 다운로드 뷰를 통한 자료 리턴
	 */
	@RequestMapping(value = "/board/{boardNum}/file/{fileNum}", method = RequestMethod.GET)
	public ModelAndView fileDown(@PathVariable("boardNum") String boardNum, @PathVariable("fileNum") String fileNum,
			AttrMap map) {
		int boardNumber = NumberUtils.toInt(boardNum, -1);
		int fileNumber = NumberUtils.toInt(fileNum, -1);
		if (boardNumber < 0 || fileNumber < 0) {
			return null;
		}
		map.put("boardNum", boardNumber);
		map.put("fileNum", fileNumber);
		Map<String, Object> file = service.searchFile(map);
		if (file == null) {
			return null;
		}
		LOG.info((String) file.get("filePath"));
		map.putAll(file);
		return new ModelAndView("downloadView", map.getMap());
	}

	/**
	 * 유저에 대한 프로필사진 조회
	 * 
	 * @param userNum
	 *            조회할 유저의 번호
	 * @return 다운로드뷰를 통한 데이터 제공
	 */
	@RequestMapping(value = "/user/{userNum}/image", method = RequestMethod.GET)
	public ModelAndView profileDown(@PathVariable("userNum") String userNum) {
		int userNumber = NumberUtils.toInt(userNum, -1);
		Map<String, Object> map = service.searchProfile(userNumber);
		if (map == null) {
			return null;
		}
		return new ModelAndView("downloadView", map);
	}

}
