/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.reply;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * 댓글에 대한 DAO
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Repository
public class ReplyDAO {

	/**
	 * database connection 을 위한 sql session
	 */
	@Inject
	private SqlSession session;

	/**
	 * mapper의 namespace
	 */
	private static String NAME_SPACE = "ReplyMapper";

	/**
	 * 생성자 함수
	 */
	public ReplyDAO() {
		super();
	}

	/**
	 * 댓글 추가
	 * 
	 * @param map
	 *            추가할 정보를 담은 맵
	 * @return 추가된 갯수
	 */
	public int insert(Map<String, Object> map) {
		return session.insert(NAME_SPACE + ".insert", map);
	}

	/**
	 * 댓글 목록 조회
	 * 
	 * @param map
	 *            조회시 필요한 정보 (boardNum,lastReplyNum,pageNum)
	 * @return 댓글정보 리스트
	 */
	public List<Map<String, Object>> selectList(Map<String, Object> map) {
		return session.selectList(NAME_SPACE + ".selectPage", map);
	}

	/**
	 * 댓글 삭제
	 * 
	 * @param map
	 *            삭제시 필요한 정보
	 * @return 삭제 갯수
	 */
	public int delete(Map<String, Object> map) {
		return session.delete(NAME_SPACE + ".deleteOne", map);
	}

	/**
	 * 댓글 수정
	 * 
	 * @param map
	 *            수정시 필요한 데이터
	 * @return 수정 갯수
	 */
	public int update(Map<String, Object> map) {
		return session.update(NAME_SPACE + ".update", map);
	}

}
