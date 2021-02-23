/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.biz.user;

import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import sns.platform.common.constant.ConstantDefined;
import sns.platform.common.resolver.AttrMap;

/**
 * <pre>
 * 유저에 대한 서비스
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
@Service
public class UserService {

	/**
	 * 필요한 DAO 인젝트
	 */
	@Inject
	private UserDAO dao;

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	/**
	 * 생성자
	 */
	public UserService() {
		super();
	}

	/**
	 * 유저 등록
	 * 
	 * @param map
	 *            등록 정보를 가진 맵
	 * @return
	 */
	public int createUser(AttrMap map) {
		LOG.info("파일의 유무 " + (map.getUserFile() != null));
		map.put("userFile", ConstantDefined.PROFILE_DEFAULT);
		if (map.getUserFile() != null) {
			String physicalPath = null;
			try {
				physicalPath = createProfile(map.getUserFile());
			} catch (IOException e1) {
				e1.printStackTrace();
				return 0;
			}
			map.put("userFile", physicalPath);
		}
		dao.insert(map.getMap());
		return 1;
	}

	/**
	 * 유조조회
	 * 
	 * @param userNum
	 *            조회할 유저번호
	 * @return
	 */
	public Map<String, Object> search(int userNum) {
		Map<String, Object> user = dao.selectOne(userNum);
		if (user == null) {
			return null;
		}
		user.remove("userFile");
		String Url = ConstantDefined.HOST_ADDRESS + "/user/" + userNum + "/image";
		user.put("imageUrl", Url);
		return user;
	}

	/**
	 * 유저 수정
	 * 
	 * @param map
	 *            수정할 정보를 담은 맵
	 * @return
	 */
	public UserVO modifyUser(AttrMap map) {
		String filePath = null;
		String oldFilePath = (String) map.get("userFile");
		if (map.getUserFile() != null && !map.getUserFile().isEmpty()) {
			try {
				filePath = createProfile(map.getUserFile());
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}
			map.put("userFile", filePath);
		}

		int cnt = dao.update(map.getMap());
		UserVO vo = null;
		if (cnt > 0) {// 성공
			vo = dao.selectUpdate(map.getMap());
		}
		if (map.getUserFile() != null && !map.getUserFile().isEmpty()) {
				if (oldFilePath != null && oldFilePath != ConstantDefined.PROFILE_DEFAULT) {
					File oldFile = new File(ConstantDefined.LOCAL_FILE_PATH + oldFilePath);
					oldFile.delete();
			}
		}
		return vo;
		
	}

	/**
	 * 유저 삭제
	 * 
	 * @param map
	 *            현재 사용자를 담은 맵
	 * @return
	 */
	public int removeUser(AttrMap map) {
		int userNum = (int) map.get("userNum");
		String userFile = (String) map.get("userFile");
		List<Map<String, Object>> files = new ArrayList<>();
		List<Map<String, Object>> filelist = dao.selectUserAllFile(userNum);
		if (filelist != null && !filelist.isEmpty()) {
			files.addAll(filelist);
		}
		if (userFile != ConstantDefined.PROFILE_DEFAULT) {
			map.put("filePath", userFile);
			files.add(map.getMap());
		}
		int result = dao.deleteUser(userNum);// 유저지우기
		if (result <= 0) {
			return 0;
		}

		if (files != null && !files.isEmpty()) {
			for (int i = 0, len = files.size(); i < len; i++) {
				if (files.get(i) != null) {

					String path = (String) files.get(i).get("filePath");
					if (path != null && path != "") {
						File fileBinary = new File(ConstantDefined.LOCAL_FILE_PATH + path);
						fileBinary.delete();
					}
				}
			}
		}
		return 1;
	}

	/**
	 * 로그인
	 * 
	 * @param map
	 *            로그인 정보
	 * @return
	 */
	public UserVO login(AttrMap map) {
		UserVO vo = dao.login(map.getMap());
		if(vo != null) {
			map.setUser(vo);
			dao.updateToken(map.getMap());
			return dao.login(map.getMap());
		} else {
			return null;
		}
	}

	/**
	 * 유저 아이디 체크
	 * 
	 * @param userId
	 *            검색할 아이디
	 * @return
	 */
	public int checkId(String userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		int cnt = dao.checkId(map);
		if (cnt > 0) {
			return 0;
		}
		return 1;
	}

	/**
	 * 유저 이름 체크
	 * 
	 * @param userNick
	 *            검색할 유저 번호
	 * @return
	 */
	public int checkNick(String userNick) {
		Map<String, Object> map = new HashMap<>();
		map.put("userNick", userNick);
		int cnt = dao.checkName(map);
		if (cnt > 0) {
			return 0;
		}
		return 1;
	}

	/**
	 * 유저 비밀번호 체크
	 * 
	 * @param map
	 *            현재 유저의 정보
	 * @return
	 */
	public int checkPass(AttrMap map) {
		int cnt = dao.checkPassword(map.getMap());
		if (cnt > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * 유저 프로파일 이미지 업로드
	 * 
	 * @param userFile
	 *            프로파일 이미지
	 * @return
	 * @throws IOException
	 */
	public String createProfile(MultipartFile userFile) throws IOException {
		String physicalPath = ConstantDefined.PROFILE_DIR + UUID.randomUUID().toString() + "_"
				+ userFile.getOriginalFilename();
		File file = new File(ConstantDefined.LOCAL_FILE_PATH + physicalPath);
		BufferedImage img = ImageIO.read(userFile.getInputStream());
		BufferedImage scalrImage = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		int idx = userFile.getOriginalFilename().lastIndexOf(".");
		String content = userFile.getOriginalFilename().substring(idx + 1);
		ImageIO.write(scalrImage, content, file);
		return physicalPath;
	}

}
