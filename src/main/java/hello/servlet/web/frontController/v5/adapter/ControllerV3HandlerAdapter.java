package hello.servlet.web.frontController.v5.adapter;

import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.v3.ControllerV3;
import hello.servlet.web.frontController.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
         return (handler instanceof ControllerV3); // 핸들러가 v3의 인스턴스인지 체크
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler;

        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) { // 파라미터 모두 뽑기
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator() // 모든 파라미터 이름 다 가져오고 돌리면서
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName))); // 이름, 값
        return paramMap;
    }
}
