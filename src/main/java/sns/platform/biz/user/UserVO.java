/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.user;

import java.util.HashMap;
import java.util.Map;

import sns.platform.common.constant.ConstantDefined;

/**
 * <pre>
 * 유저에 대한 VO
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
public class UserVO {
	
	/**
	 * 유저 아이디 유저 이름 유저 파일 유저 번호
	 */
	private String userId, userNick, userFile,userType,pnsToken;


	

	private int userNum;

	/**
	 * 생성자
	 */
	public UserVO() {
		super();
	}

	/**
	 * equal비교
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserVO other = (UserVO) obj;
		if (userNum != other.userNum)
			return false;
		return true;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserFile() {
		return userFile;
	}

	public void setUserFile(String userFile) {
		this.userFile = userFile;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getPnsToken() {
		return pnsToken;
	}

	public void setPnsToken(String pnsToken) {
		this.pnsToken = pnsToken;
	}
	
	
	/**
	 * 유저정보를 맵형태로 반환
	 * @return
	 */
	public Map<String,Object> getUserDTO() {
		Map<String,Object> map = new HashMap<>();
		map.put("userId", this.userId);
		map.put("userNick", this.userNick);
		map.put("userNum", this.userNum);
		map.put("userId", this.userId);
		map.put("userType", this.userType);
		map.put("pnsToken", this.pnsToken);
		
		String url = ConstantDefined.HOST_ADDRESS+"/user/"+this.userNum+"/image";
		map.put("imageUrl", url);
		return map;
	}

	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", userNick=" + userNick + ", userFile=" + userFile + ", userNum="
				+ userNum + "]";
	}

}
