/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.board;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang3.math.NumberUtils;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import sns.platform.biz.file.FileDAO;

import sns.platform.common.constant.ConstantDefined;
import sns.platform.common.resolver.AttrMap;

/**
 * <pre>
 *게시글에 대한 Service 클래스
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Service
public class BoardService {
	/**
	 * 필요한 DAO 주입 FileDAO,BoardDAO,ReplyDAO
	 */
	@Inject
	private FileDAO fdao;
	@Inject
	private BoardDAO dao;

	/**
	 * 생성자함수
	 */
	public BoardService() {
		super();
	}

	/**
	 * 게시글 작성
	 * 
	 * @param map
	 *            -게시글 작성 정보를 담은 맵
	 * @return
	 */
	public int createBoard(AttrMap map) {
		int boardNum = dao.insert(map.getMap());
		if (map.getFiles() != null && !map.getFiles().isEmpty()) {
			map = this.createBinaryFile(map);
			if (map == null) {
				return 0;
			}
			List<Map<String, Object>> files = (List<Map<String, Object>>) map.get("fileList");
			for (Map<String, Object> file : files) {
				file.put("boardNum", boardNum);
				fdao.insert(file);
			}
			dao.update(map.getMap());
		}
		return 1;
	}

	/**
	 * 게시글 상세 조회
	 * 
	 * @param boardNum
	 *            조회할 게시글 번호
	 * @return Map<String, Object> 게시글 데이터
	 * 
	 */
	public Map<String, Object> searchBoard(int boardNum) {
		return dao.selectOne(boardNum);
	}

	/**
	 * 게시글 목록
	 * 
	 * @param map
	 *            조회하고 싶은 게시글목록 에 대한
	 *            데이터(searchType,keyWord,pageNum,lastBoardNum)
	 * @return List<Map<String, Object>> 찾는 게시글 리스트
	 */
	public List<Map<String, Object>> searchBoards(AttrMap map) {
		List<Map<String, Object>> list = dao.selectList(map.getMap());
		for (Map<String, Object> board : list) {
			if (board.get("boardFirstImg") != null) {
				String url = ConstantDefined.HOST_ADDRESS + "/board/" + board.get("boardNum") + "/file/"
						+ board.get("boardFirstImg") + "";
				board.remove("boardFirstImg");
				board.put("firstImageUrl", url);
			}
		}
		return list;
	}

	/**
	 * 게시글 삭제
	 * 
	 * @param map
	 *            삭제하고 싶은 게시글 번호와 유저정보
	 * @return 삭제된 데이터 수
	 */
	public int removeBoard(AttrMap map) {
		List<Map<String, Object>> list = fdao.selectList((int) map.get("boardNum"));
		int res = dao.delete(map.getMap());
		if (res <= 0) {
			return 0;
		}
		for (Map<String, Object> file : list) {
			String path = (String) file.get("filePath");
			if (path != null && path != "") {
				File fileBinary = new File(ConstantDefined.LOCAL_FILE_PATH + path);
				fileBinary.delete();
			}
		}
		return 1;
	}

	/**
	 * 수정하고싶은 게시글정보
	 * 
	 * @param map
	 *            수정하고자하는 게시글에대한 정보
	 * @return 수정된 갯수
	 */
	public int modifyBoard(AttrMap map) {
		Object temp = map.get("delFiles");// 지울 파일번호
		List<Integer> delFiles = null;// 지울 파일번호를 integer리스트로 다운 캐스팅해여 저장할 변수
		List<String> delFilePaths = new ArrayList<>();// 지울 파일의 경로의 리스트
		List<MultipartFile> files = map.getFiles();// 저장할 파일의 binarydata리스트
		int boardNum = (int) map.get("boardNum");// 수정할 게시판번호

		// 업로드 파일이 있다면
		if (files != null && !files.isEmpty()) {
			map = this.createBinaryFile(map);// 로컬에 파일을 저장
			if (map == null) { // IO예외발생시
				return 0;
			}
			List<Map<String, Object>> filelist = (List<Map<String, Object>>) map.get("fileList"); // DB에
																									// 저장할
																									// 값
																									// 리스트
			for (Map<String, Object> file : filelist) {
				file.put("boardNum", boardNum);// 게시글번호를 넣어준다
				fdao.insert(file);// 파일데이터를 넣어준다
			}
		}

		if (temp != null) {// 지울 파일이 있다면 Attr
			delFiles = new ArrayList<>();
			//배열
			if (temp.getClass() == String.class) {
				delFiles.add(NumberUtils.toInt((String) temp, -1));
			} else {
				String[] fileNums = (String[]) temp;
				for (String str : fileNums) {
					delFiles.add(NumberUtils.toInt(str, -1));
				}
			}
////////////
			for (int fileNumber : delFiles) { // 지울 파일에대한 정보를 모두 불러온다
				Map<String, Object> fileVO = fdao.selectOne(fileNumber);
				if (fileVO != null) {
					delFilePaths.add((String) fileVO.get("filePath"));// 지울
																		// 파일경로에
																		// 하나씩
																		// 추가
				}

			}
///////////
			map.put("delFiles", delFiles);// map에 지울파일(int리스트)을 넣어줌
			fdao.deleteFiles(map.getMap());// 파일DB데이터 지우기
		}
		dao.update(map.getMap());// 게시판 수정

		// 바이너리 지우기
		for (String path : delFilePaths) {
			File deleteFile = new File(ConstantDefined.LOCAL_FILE_PATH + path);
			deleteFile.delete();
		}

		return 1;
	}

	/**
	 * 로컬에 파일을 저장
	 * 
	 * @param map
	 *            저장할 파일이 있는 map
	 * @return 디비에 저장해야 하는 파일정보 리스트
	 */
	public AttrMap createBinaryFile(AttrMap map) {
		List<Map<String, Object>> fileList = new ArrayList<>(); // 디비에 저장할 파일정보
																// 리스트
		List<MultipartFile> files = map.getFiles(); // 로컬에 저장할 파일 binary정보
		String filePath = null; // 파일경로 변수
		File binaryFile = null; // 저장할 파일
		Date date = new Date(); // 오늘날짜
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");// 20161110
		String dirPath = ConstantDefined.LOCAL_FILE_PATH + ConstantDefined.BOARD_DIR + format.format(date);// 디렉토리path
		File dir = new File(dirPath);
		dir.mkdirs();// 디렉토리를 만든다
		for (MultipartFile file : files) {
			filePath = ConstantDefined.BOARD_DIR + format.format(date) + ConstantDefined.PATH_SECTION + UUID.randomUUID().toString() + "_"
					+ file.getOriginalFilename(); // 실제로 저장될 파일의 주소
			binaryFile = new File(ConstantDefined.LOCAL_FILE_PATH + filePath);
			Map<String, Object> fileVO = new HashMap<>();// 파일정보를 담을 맵생성
			fileVO.put("fileName", file.getOriginalFilename()); // 실제이름
			fileVO.put("filePath", filePath);// 파일경로
			fileVO.put("fileType", file.getContentType());// 마임타입
			fileVO.put("fileSize", file.getSize() / 1024.0);// 크기
			try {
				file.transferTo(binaryFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				for (Map<String, Object> vo : fileList) {// 예외발생시 지금까지 저장된 파일을
															// 지운다
					binaryFile = new File(ConstantDefined.LOCAL_FILE_PATH + vo.get("filePath"));
					binaryFile.delete();
				}
				return null;// 실패시 null 반납
			}
			fileList.add(fileVO);// 한개 성공시 마다 리스트에 더해준다

		}
		map.put("fileList", fileList);// 맵에 넣어서 리턴
		return map;

	}

}
