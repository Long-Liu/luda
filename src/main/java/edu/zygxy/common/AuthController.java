package edu.zygxy.common;

import edu.zygxy.pojo.JsonResponse;
import edu.zygxy.pojo.User;
import edu.zygxy.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class AuthController {

    @Autowired
    AuthService authService;

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResponse login(@RequestBody User user, HttpServletResponse httpServletResponse) throws Exception {
        if (authService.login(user.getEmail(), user.getPassword(), httpServletResponse)) {
            return new JsonResponse(null);
        } else {
            return new JsonResponse(400, "登录失败，请检查用户名密码");
        }

    }

    @RequestMapping(value = "/api/logout", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public JsonResponse logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return new JsonResponse(null);
    }

    @RequestMapping(value = "/system")
    public String system() {
        return "system";
    }

}
