/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.reply;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import sns.platform.biz.board.BoardDAO;
import sns.platform.common.resolver.AttrMap;


/**
 * <pre>
 * 댓글에 대한 서비스
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Service
public class ReplyService {
	
	/**
	 * 필요한 DAO 인젝트
	 */
	@Inject
	private ReplyDAO dao;
	@Inject
	private BoardDAO bdao;

	/**
	 * 생성자 함수
	 */
	public ReplyService() {
		super();
	}

	/**
	 * 댓글 입력
	 * 
	 * @param map
	 * 				입력한 정보
	 * @return
	 * 				성공시 1 실패시 0
	 */
	public int createReply(AttrMap map) {
		Map<String, Object> boardNum = bdao.selectOne((int)map.get("boardNum"));//등록 가능한 게시글이 있는지 검색
		if(boardNum == null){
			return  0;//게시글 번호가 없을시 실패
		}
		dao.insert(map.getMap());//댓글 입력
		bdao.replyCnt(map.getMap());//게시글 댓글수 증가
		return 1;
	}

	/**
	 * 게시글에 대한 댓글 목록
	 * 
	 * @param Amap 
	 * 				조회 데이터
	 * @return
	 * 				댓글 리스트
	 */
	public List<Map<String, Object>> searchReplys(AttrMap Amap) {
		return dao.selectList(Amap.getMap()); 
	}

	/**
	 * 댓글 수정
	 * 
	 * @param map
	 * 			  수정할 정보
	 * @return
	 * 			 수정된 갯수
	 */
	public int modifyReply(AttrMap map) {
		return dao.update(map.getMap());
	}

	/**
	 * 댓글 삭제 
	 * 
	 * @param map
	 * 			  삭제시 필요한 정보맵(replyNum,boardNum,userNum)
	 * @return
	 */
	public int removeReply(AttrMap map) {
		int z = dao.delete(map.getMap());
		bdao.replyCnt(map.getMap());
		return  z;
	}
	
	

}
