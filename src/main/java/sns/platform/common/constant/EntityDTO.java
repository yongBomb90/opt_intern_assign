/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *response body 구조를 만들어주는 객체
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 01
 * @updated 2016. 11. 01
 */
public class EntityDTO {

	private Map<String, Object> map;

	/**
	 * 생성자
	 */
	public EntityDTO() {
		super();
		map = new HashMap<>();// map 생성
		map.put("code", 0);// "code"키로 초기값 1 설정
		map.put("message", "실패");// "message"키로 초기값 "실패" 설정
	}

	/**
	 * 코드값 제공
	 * 
	 * @return 코드값
	 */
	public int getCode() {
		return (int) map.get("code");
	}

	/**
	 * 코드값 설정
	 * 
	 * @param code
	 *            - coder값 0 : 성공 , 1 : 실패
	 */
	public void setCode(int code) {
		if (code == 1) {
			map.put("message", "성공"); // 코드가 0이면 message "성공"으로 적용
		}
		map.put("code", code); // code값 설정
	}

	/**
	 * 메시지 제공
	 * 
	 * @return - message 리턴
	 */
	public String getMessage() {
		return (String) map.get("message");
	}

	/**
	 * 메시지 설정
	 * 
	 * @param message
	 *            - 메시지
	 */
	public void setmessage(String message) {
		map.put("message", message);
	}

	/**
	 * data설정
	 * 
	 * @param data
	 *            - 보내고싶은 실질적 data
	 */
	public void setData(Object data) {
		map.put("data", data);
	}

	/**
	 * data 제공
	 * 
	 * @return data - data
	 */
	public Object getData() {
		return map.get("data");
	}

	/**
	 * 리스폰스 구조체 제공
	 * 
	 * @return - response 구조 {code : 0 , message : "성공" , data : data} 형식 리턴
	 */
	public Map<String, Object> getDTO() {
		return this.map;
	}

	/**
	 * toString
	 */
	@Override
	public String toString() {
		if (map.get("data") == null) {
			return "EntityDTO [code=" + map.get("code") + " message " + map.get("message") + "]";
		}
		return "EntityDTO [code=" + map.get("code") + " message " + map.get("message") + " data " + map.get("data")
				+ "]";
	}

}
