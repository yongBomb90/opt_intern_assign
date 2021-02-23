/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.common.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import sns.platform.biz.user.UserVO;

/**
 * <pre>
 *파라미터를 맵형식으로 가지고 이으며 현재 사용자정보 접속 URI를 넣어서 사용하는 객체
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 01
 * @updated 2016. 11. 01
 */
public class AttrMap {

	private Map<String, Object> map = new HashMap<String, Object>();
	private UserVO user;
	private String uri;
	private Map<String, List<MultipartFile>> fileMap = new HashMap<>();

	/**
	 * 생성자
	 */
	public AttrMap() {
		super();
	}

	/**
	 * 접속된 유저정보 제공
	 * 
	 * @return user - 현재 접속된 유저정보를 return
	 */
	public UserVO getUser() {
		return user;
	}

	/**
	 * user를 설정합니다
	 * 
	 * @param user
	 *            - 현재 접속된 유저정보
	 */
	public void setUser(UserVO user) {
		this.map.put("userNum", user.getUserNum());
		this.map.put("userId", user.getUserId());
		this.map.put("userNick", user.getUserNick());
		this.map.put("userFile", user.getUserFile());
		this.user = user;
	}

	/**
	 * 접속한 URI를 return
	 */
	public String getURI() {
		return uri;
	}

	/**
	 * URI를 설정합니다
	 * 
	 * @param uRI
	 *            - 현재 접속한 URI
	 */
	public void setURI(String uRI) {
		uri = uRI;
	}

	/**
	 * 원하는 파라마터를 제공
	 * 
	 * @param key
	 *            - 필요한 파라미터의 key값
	 * @return map.get(key) - 필요한 파라미터값
	 */
	public Object get(String key) {
		return map.get(key);
	}

	/**
	 * 파라미터값 저장
	 * 
	 * @param key
	 *            - 파라미터 키값
	 * @param value
	 *            - 파라미터값
	 */
	public void put(String key, Object value) {
		map.put(key, value);
	}

	/**
	 * 파라미터값 삭제
	 * 
	 * @param key
	 *            - 삭제 파라미터 키값
	 * @return map.remove(key) - 지워진 파라미터값
	 */
	public Object remove(String key) {
		return map.remove(key);
	}

	/**
	 * 파라미터 맵을 초기화합니다
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * 파라미터 유무 확인
	 * 
	 * @return map.isEmpty() - true 파라미터 있음 false 파라미터 없음
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * 파라미터맵에 맵을 넣어줌
	 * 
	 * @param m
	 *            - <String,Object>로된 맵전체를 파라미터맵에 넣어줌
	 */
	public void putAll(Map<? extends String, ? extends Object> m) {
		map.putAll(m);
	}

	/**
	 * 파라미터 맵을 얻음
	 * 
	 * @return map - 파라미터 맵
	 */
	public Map<String, Object> getMap() {
		return map;
	}

	/**
	 * 객체 tostring
	 */
	@Override
	public String toString() {
		String str = "유저 아이디 ";
		if (this.user != null) {
			str += this.user.getUserId() + " URI " + this.getURI() + " 파라미터 [";
		} else {
			str += "유저 없음 " + " URI " + this.getURI() + " 파라미터 [";
		}
		for (String mapkey : this.map.keySet()) {
			str += "| " + mapkey + " : " + map.get(mapkey) + " |";
		}
		str += " ]";
		return str;
	}

	/**
	 * 파라미터 맵을 "맵 키값 [|키:값||....|]" 형태로 보여줌
	 * 
	 * @return str - 키값 문자열
	 */
	public String toMapString() {
		String str = "맵 키값  [";
		for (String mapkey : this.map.keySet()) {
			str += "| " + mapkey + " : " + map.get(mapkey) + " |";
		}
		str += " ]";
		return str;
	}

	/**
	 * 파라미터 존재 확인
	 * 
	 * @param params
	 *            - 파라미터 키값 배열
	 * @return - true 모두존재 false 하나이상 없음
	 */
	public boolean checkParam(String... params) {
		for (String key : params) {
			if (map.get(key) == null || map.get(key) == "") {
				return false;
			}
		}
		return true;
	}

	public void setUserFile(MultipartFile userFile) {
		List<MultipartFile> file = new ArrayList<>();
		file.add(userFile);
		fileMap.put("userFile", file);

	}

	public MultipartFile getUserFile() {
		if (fileMap.get("userFile") == null) {
			return null;
		}
		return fileMap.get("userFile").get(0);
	}

	public void setFiles(List<MultipartFile> files) {
		fileMap.put("files", files);

	}

	public List<MultipartFile> getFiles() {
		if (fileMap.get("files") == null) {
			return null;
		}
		return fileMap.get("files");
	}

	public String toFileString() {
		String str = "파일갯수  [  ";
		if (fileMap.get("userFile") != null) {
			str += " userFile " + fileMap.get("userFile").get(0).getOriginalFilename() + " ";
		}
		if (fileMap.get("files") != null) {
			str += " files " + fileMap.get("files").size() + " ";
		}
		str += "]";
		return str;
	}

}
