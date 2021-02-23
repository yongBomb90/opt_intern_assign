/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.file;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;


import sns.platform.biz.user.UserDAO;
import sns.platform.common.constant.ConstantDefined;
import sns.platform.common.resolver.AttrMap;

/**
 * <pre>
 *	파일의 서비스
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Service
public class FileService {

	/**
	 * 필요한 DAO 인젝트
	 */
	@Inject
	private FileDAO dao;
	@Inject
	private UserDAO udao;

	/**
	 * 생성자
	 */
	public FileService() {
		super();
	}

	/**
	 * 게시글에 대한 파일 정보리스트를 불러온다
	 * 
	 * @param boardNum
	 * 					-조회할 게시글 번호
	 * @return
	 * 			클라이언트에게 보내야하는 파일 리스트
	 */
	public List<Map<String, Object>> searchFiles(int boardNum) {
		List<Map<String, Object>> list = dao.selectList(boardNum);
		for (Map<String, Object> map : list) {//리스트 파일정보를 통해 url만들어준후 넣어준다
			String Url = ConstantDefined.HOST_ADDRESS + "/board/" + boardNum + "/file/" + map.get("fileNum");
			map.remove("filePath");
			map.put("fileUrl", Url);
		}
		return list;
	}
	
	/**
	 * 하나의 파일 찾기
	 * 
	 * @param map 
	 * 			  조회할 파일에 대한 정보가 있는 맵
	 * @return
	 * 			조회 파일 정보
	 */
	public Map<String, Object> searchFile(AttrMap map) {
		Map<String, Object> file = dao.selectOne((int)map.get("fileNum"));
		if (file != null) {//조회한 파일의 정보를 통해 실제 물리주소로 변환하여 filePath에 수정
			String filePath = ConstantDefined.LOCAL_FILE_PATH + file.get("filePath");
			file.put("filePath", filePath);
		}
		return file;
	}

	/**
	 * 유저의 프로파일 이미지 경로 
	 * 
	 * @param userNum
	 * 				   -조회할 유저 정보
	 * @return
	 * 			유저 프로필파일의 정보
	 */
	public Map<String, Object> searchProfile(int userNum) {
		Map<String, Object> user = udao.selectOne(userNum);//유저정보를 불러온다
		if (user == null) {
			return null;
		}
		String imagepath = (String) user.get("userFile"); // 프로파일 이미지 경로를 불러온다
		String filePath = ConstantDefined.LOCAL_FILE_PATH + imagepath;//물리적 주소를 만들어준다
		int idx = imagepath.lastIndexOf("."); 
		String content = imagepath.substring(idx);
		String fileName = (String) user.get("userId") + content; //파일이름을 아이디로 한다(ex 아이디.jpg)
		File file = new File(filePath);//파일 로드
		double fileSize = file.getTotalSpace() / 1024.0;//파일사이즈를 구한다
		user.clear();
		user.put("filePath", filePath);//파일경로 입력
		user.put("fileName", fileName);//파일이름 입력
		user.put("fileType", "image/png");//마임타입을 이미지로 설정
		user.put("fileSize", fileSize);//사이즈 입력
		return user;//파일정보로된 맵 반납
	}

}
