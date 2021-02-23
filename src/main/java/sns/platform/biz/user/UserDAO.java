/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.user;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * 유저에 대한 DAO
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Repository
public class UserDAO {

	/**
	 * database connection 을 위한 sql session
	 */
	@Inject
	private SqlSession session;
	
	/**
	 * mapper의 namespace
	 */
	private static String NAME_SPACE = "UserMapper";
	
	/**
	 * 생성자 함수
	 */
	public UserDAO() {
		super();
	}

	/**
	 * 유저 정보 추가
	 * 
	 * @param map
	 *            추가할 정보가 있는 맵
	 * @return
	 */
	public int insert(Map<String, Object> map) {
		return session.insert(NAME_SPACE+".insert", map);
	}

	/**
	 * 유저 조회
	 * 
	 * @param userNum
	 *            조회할 유저 번호
	 * @return
	 */
	public Map<String, Object> selectOne(int userNum) {
		return session.selectOne(NAME_SPACE+".selectOne", userNum);
	}

	/**
	 * 로그인
	 * 
	 * @param map
	 *            userId,userPass를 담은 맵
	 * @return
	 */
	public UserVO login(Map<String, Object> map) {
		return session.selectOne(NAME_SPACE+".login", map);
	}

	/**
	 * 회원정보 수정
	 * 
	 * @param map
	 *            수정할 정보를 가지고 있는 맵
	 * @return
	 */
	public UserVO selectUpdate(Map<String, Object> map) {
		return session.selectOne(NAME_SPACE+".selectUpdate", map);
	}

	/**
	 * 회원 삭제
	 * 
	 * @param userNum
	 *            삭제할 회원 번호
	 * @return
	 */
	public int deleteUser(int userNum) {
		return session.delete(NAME_SPACE+".deleteUser", userNum);
	}

	/**
	 * 회원수정
	 * 
	 * @param map
	 *            회원수정시 필요한 데이터를 담은 맵
	 * @return
	 */
	public int update(Map<String, Object> map) {
		return session.update(NAME_SPACE+".update", map);
	}
	
	
	
	public int updateToken(Map<String, Object> map) {
		return session.update(NAME_SPACE+".tokenupdate", map);
	}

	/**
	 * 이름 체크
	 * 
	 * @param map
	 *            검사할 이름를 담은 맵
	 * @return
	 */
	public int checkName(Map<String, Object> map) {
		return session.selectOne(NAME_SPACE+".checkUniQ", map);
	}

	/**
	 * 아이디 체크
	 * 
	 * @param map
	 * 			 검사할 아이디르 담은 맵
	 * @return
	 */
	public int checkId(Map<String, Object> map) {
		return session.selectOne(NAME_SPACE+".checkUniQ", map);
	}

	/**
	 * 비밀번호 체크
	 * 
	 * @param map
	 * @return
	 */
	public int checkPassword(Map<String, Object> map) {
		return session.selectOne(NAME_SPACE+".checkPassword", map);
	}

	/**
	 * 유저가 올린 모든 파일 검색
	 * 
	 * @param userNum
	 * 				유저의 고유 번호
	 * @return
	 */
	public List<Map<String, Object>> selectUserAllFile(int userNum) {
		return session.selectList(NAME_SPACE+".deleteUserAllFileSelect", userNum);
	}

}
