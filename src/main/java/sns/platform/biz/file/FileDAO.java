/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.file;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *	파일에 대한 DAO 클래스
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Repository
public class FileDAO {

	/**
	 * database connection 을 위한 sql session
	 */
	@Inject
	private SqlSession session;


	/**
	 * mapper의 namespace
	 */
	private static String NAME_SPACE = "FileMapper";

	/**
	 * 생성자 함수
	 */
	public FileDAO() {
		super();
	}
	/**
	 * 파일정보 인설트
	 * 
	 * @param map 
	 * 			  파일정보를 담은 맵
	 * @return
	 * 			입력된 갯수
	 */
	public int insert(Map<String, Object> map) {
		return session.insert(NAME_SPACE + ".insert", map);
	}
	
	/**
	 * 게시글에 대한 파일의 정보를 리스트로 준다
	 * 
	 * @param boardNum
	 * 					조회할 게시글번호
	 * @return
	 * 			파일정보 리스트
	 */
	public List<Map<String, Object>> selectList(int boardNum) {
		return session.selectList(NAME_SPACE + ".selectList", boardNum);
	}

	/**
	 * 하나의 파일정보 조회
	 * 
	 * @param fileNum 
	 * 					조회할 파일번호
	 * @return
	 * 			파일 정보		
	 */
	public Map<String, Object> selectOne(int fileNum) {
		return session.selectOne(NAME_SPACE + ".selectOne", fileNum);
	}

	/**
	 * 파일 정보들 삭제
	 * 
	 * @param map
	 * 				지우고싶은 파일 정보 리스트 담긴 멥
	 * @return
	 * 			지워진 파일 갯수
	 */
	public int deleteFiles(Map<String, Object> map) {
		return session.delete(NAME_SPACE + ".deleteFiles", map);
	}


}
