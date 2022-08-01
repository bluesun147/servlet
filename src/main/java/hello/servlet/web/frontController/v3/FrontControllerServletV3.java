package hello.servlet.web.frontController.v3;

import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.MyView;
import hello.servlet.web.frontController.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*") // 하위 모든 url. 아무거나 모두
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String, ControllerV3> controllerMap = new HashMap<>(); // key: 매핑 url, value: 호출될 컨트롤러

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3()); // 이 주소로 가면 다음 컨트롤러 실행
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI(); // "/front-controller/v1/members/new-form" 같은 주소 얻을 수 있음.

        // "/front-controller/v1/members" 주소일 때 controller = MemberListControllerV1());
        ControllerV3 controller = controllerMap.get(requestURI);

        if (controller == null) { // 없을 때 예외 처리
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName(); // 논리이름 new-form

        MyView view = viewResolver(viewName); // ctrl + alt + m 으로 메서드 빼기

        view.render(mv.getModel(), request, response);
    }

    // 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) { // 파라미터 모두 뽑기
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator() // 모든 파라미터 이름 다 가져오고 돌리면서
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName))); // 이름, 값
        return paramMap;
    }
}
