package sns.platform.common.resolver;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import sns.platform.common.constant.EntityDTO;
/**
 * <pre>
 * 오류 에 대한 리졸버
 * </pre>
 * 
 * @author ybpark
 * @version 0.1
 * @since 0.1
 * @created 2016. 11. 15
 * @updated 2016. 11. 15
 */
public class ExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		EntityDTO dto = new EntityDTO();//dto 생성
		dto.setmessage(" 오류 :: "+ex.getClass().getSimpleName());//코드 0에 메시지 "오류 :: [오류의 클래스 이름]" 
		return new ModelAndView("jsonView", dto.getDTO());//제이슨 형태로 리턴
	}

}
