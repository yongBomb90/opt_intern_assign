/**
 *(주)오픈잇 | http://www.openir.co.kr 
 * Copyright (c)2006-2016 openit Inc.
 * All rights reserved
 */
package sns.platform.common.view;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;
/**
 * <pre>
 * 데이터를 다운시켜주기위한 view
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
public class DownloadView extends AbstractView {

	/**
	 * 생성자 기본 타입은 "application/octet-stream" 로 지정
	 */
	public DownloadView() {
		super();
		setContentType("application/octet-stream");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String filePath = (String) model.get("filePath");
		String fileName = (String) model.get("fileName");
		String fileType = (String) model.get("fileType");
		String view = (String) model.get("view");
		String browser = req.getHeader("User-Agent");
		String disposition = "inline";
		if (view != null && view.equals("down")) {
			System.out.println("다운");
			disposition = "attachment";
		}
		OutputStream rout = null;
		FileInputStream fin = null;
		setContentType(fileType);
		boolean chrome = browser.indexOf("Chrome") > -1;//크롬의 경우
		File file = new File(filePath);
		if (!file.exists()) {
			return;
		}
		
		if (chrome) {
			fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");//크롬일때 파일이름 인코딩
		} else {
			fileName = URLEncoder.encode(fileName, "utf-8");//IE일떄 파일이름 인코딩
		}
		res.setHeader("Content-Disposition", disposition + "; filename=\"" + fileName + "\";");
		res.setHeader("Content=Transfer-Encoding", "binary");
		res.setContentLength((int) file.length());
		res.setContentType(fileType);

		try {
			rout = res.getOutputStream();
			fin = new FileInputStream(file);
			FileCopyUtils.copy(fin, rout);
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
				}
			}

		}

		rout.close();
		rout.flush();
	}

}
