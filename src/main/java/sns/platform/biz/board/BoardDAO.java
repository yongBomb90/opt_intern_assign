/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.board;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
/**
 * <pre>
 *게시글에 대한 DAO 클래스
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Repository
public class BoardDAO {
	/**
	 * database connection 을 위한 sql session
	 */
	@Inject
	private SqlSession session;

	/**
	 * mapper의 namespace
	 */
	private static String NAME_SPACE = "BoardMapper";
	
	/**
	 * 생성자 함수
	 */
	public BoardDAO() {
		super();
	}
	/**
	 * 게시글 등록 하기
	 * @param map
	 * 				-파라미터를 담은 (boardContent) 맵
	 * @return int
	 * 				-입력시 프라이머리키 값 boardNum
	 */
	public int insert(Map<String, Object> map) {
		session.insert(NAME_SPACE + ".insert", map);
		return (int) map.get("boardNum");
	}

	/**
	 * 게시글 목록 가져오기
	 * 
	 * @param map
	 * 				-searchType,pageNum,lastBoardNum 을 담은 맵 
	 * @return List<Map<String, Object>>
	 * 				-searchType,pageNum,lastBoardNum 에 따른 게시글 목록리스트
	 */
	public List<Map<String, Object>> selectList(Map<String, Object> map) {
		return session.selectList(NAME_SPACE + ".selectPage", map);
	}

	/**
	 * 게시글 상세 보기
	 * 
	 * @param boardNum 
	 * 					-가져올 게시글 번호
	 * @return  Map<String, Object>
	 * 					-게시글 
	 */
	public Map<String, Object> selectOne(int boardNum) {
		return session.selectOne(NAME_SPACE + ".selectOne", boardNum);
	}
	


	/**
	 * 게시글 삭제
	 * 
	 * @param map 
	 * 			  	-삭제할 게시글번호가 있는 맵
	 * @return 
	 * 				-삭제된 데이터들의 갯수(게시글,댓글,파일)
	 */
	public int delete(Map<String, Object> map) {
		return session.delete(NAME_SPACE + ".deleteOne", map);
	}
	
	
	/**
	 * 게시글 수정
	 * 
	 * @param map
	 * 				-수정할 데이터를 담은 맵
	 * @return int
	 * 				-수정된 데이터 수
	 */
	public int update(Map<String, Object> map) {
		return session.update(NAME_SPACE + ".update", map);
	}
	
	/**
	 * 댓글수 수정
	 * 
	 * @param map 
	 * 				-수정할 게시글 번호
	 * @return int
	 * 				-수정된 데이터 수
	 */
	public int replyCnt(Map<String, Object> map) {
		return session.update(NAME_SPACE + ".replyCnt", map);
	}
	
	
}
