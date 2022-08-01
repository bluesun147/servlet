package hello.servlet.web.frontController.v1;

import hello.servlet.web.frontController.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontController.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontController.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*") // 하위 모든 url. 아무거나 모두
public class FrontControllerServletV1 extends HttpServlet {
    private Map<String, ControllerV1> controllerMap = new HashMap<>(); // key: 매핑 url, value: 호출될 컨트롤러

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1()); // 이 주소로 가면 다음 컨트롤러 실행
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI(); // "/front-controller/v1/members/new-form" 같은 주소 얻을 수 있음.

        // "/front-controller/v1/members" 주소일 때 controller = MemberListControllerV1());
        ControllerV1 controller = controllerMap.get(requestURI);

        if (controller == null) { // 없을 때 예외 처리
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            return;
        }

        controller.process(request, response);
    }
}
