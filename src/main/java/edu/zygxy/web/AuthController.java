package edu.zygxy.web;

import edu.zygxy.pojo.JsonResponse;
import edu.zygxy.pojo.User;
import edu.zygxy.service.AuthService;
import edu.zygxy.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
public class AuthController {

    @Autowired
    AuthService authService;
    @Resource
    UserService userService;

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/api/app/user")
    @ResponseBody
    public JsonResponse register(@RequestBody User user) throws Exception {
        userService.insertUser(user);
        return new JsonResponse(true);
    }

    @RequestMapping(value = "/api/login/app", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResponse login(@RequestBody User user) {
        Map<String, Object> login = authService.login(user.getEmail(), user.getPassword());
        if (login == null) {
            return new JsonResponse(400, "用户名或密码错误");
        }
        return new JsonResponse(login);
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
