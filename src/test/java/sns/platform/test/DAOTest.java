package sns.platform.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import sns.platform.biz.board.BoardDAO;
import sns.platform.biz.reply.ReplyDAO;
import sns.platform.biz.reply.ReplyService;
import sns.platform.biz.user.UserDAO;
import sns.platform.common.resolver.AttrMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/context/rootContext.xml","file:src/main/webapp/WEB-INF/context/servletconfig/servletContext.xml"})
@WebAppConfiguration
public class DAOTest {
	
	@Inject
	BoardDAO dao;
	
	@Inject
	UserDAO udao;
	
	@Inject
	ReplyDAO rdao;
	
	@Inject
	ReplyService rser;
	
	
	@Test
	public void MU() {
		AttrMap map = new AttrMap();
		map.put("boardNum", 1);
		map.put("replyContent","하이");
		map.put("userNum", 1);
		System.out.println(rser.createReply(map));
	}
	
	@Test
	public void DELETEONE() {
		Map<String,Object> map = new HashMap<>();
		map.put("boardNum", 1);
		map.put("userNum", 3);
		System.out.println(dao.delete(map));
	}
	
	@Test
	public void insert() {
		Map<String,Object> map = new HashMap<>();
		map.put("boardContent", "한글되니???");
		map.put("userNum", 3);
		int  boardNum = dao.insert(map);
		System.out.println(boardNum);	
		
	}
	
	@Test
	public void searchTest() {
		Map<String,Object> map = new HashMap<>();
		map.put("lastBoardNum", -1);
		map.put("pageNum", 5);
		List<Map<String, Object>> list = dao.selectList(map);	
		for (Map<String, Object> board : list) {
			System.out.println("게시글 "+board.get("userNick")+" "+board.get("boardRegDate"));
		}
	
		
	}
	
	@Test
	public void hashmaptest() {
		System.out.println(dao.selectOne(5));	
		
	}
	@Test
	public void selectPage() {
		
		int z = NumberUtils.toInt("sss", 0);
		System.out.println(z);
	}
	
	
	@Test
	public void delete() {
		Map<String,Object> map = new HashMap<>();
		map.put("fileNum", 5);
		System.out.println(dao.delete(map));
	}
	

	
	@Test
	public void getInteger() {
		int z = Integer.getInteger("111", 0);
		System.out.println(z);
	}
	
	@Test
	public void getURL() {
		String path ="main\\webapp\\resources\\board\\20161108\\230235210_default.png";
		int idx = path.lastIndexOf("resources");
		String URL = path.substring(idx);
		System.out.println(idx);
		System.out.println(URL);
		
	}
	
	@Test
	public void checkQniq() {
		Map<String,Object> map = new HashMap<>();
		map.put("userNick", "ybpark");
		System.out.println(udao.checkName(map));
		
		
	}
	
	@Test
	public void UUID() {
		for (int i = 0; i < 10; i++) {
			System.out.println(UUID.randomUUID().toString());
		}
		
	}
	
	
}
