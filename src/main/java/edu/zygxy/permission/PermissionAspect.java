package edu.zygxy.permission;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zygxy.exception.PermissionException;
import edu.zygxy.pojo.JsonResponse;
import edu.zygxy.pojo.User;
import edu.zygxy.pojo.UserVO;
import edu.zygxy.service.AuthService;
import edu.zygxy.service.RoleService;
import edu.zygxy.utils.EncryptUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private AuthService authService;
    @Autowired
    private RoleService roleService;

    @Pointcut("execution(public * edu.zygxy.web..*.*(..)) &&!execution(public * edu.zygxy.web.AuthController.*(..)) ")
    public void checkRole() {
    }

    @Around("checkRole()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        if (token != null) {
            EncryptUtil encryptUtil = new EncryptUtil("token");
            String str = encryptUtil.decrypt(token);
            JSONObject jsonObject = JSON.parseObject(str);
            UserVO userVO = new UserVO();
            userVO.setId(jsonObject.getLong("id"));
            userVO.setName(jsonObject.getString("username"));
            edu.zygxy.pojo.Role role = roleService.getRoleById(jsonObject.getLong("roleId"));
            if (role != null)
                userVO.setRole(role.getName());
            request.setAttribute("userInfo", userVO);
        } else {
            HttpServletResponse response = attributes.getResponse();
            User user = authService.checkAuth(request);
            if (user == null) {
                response.sendRedirect("/login");
            } else {
                UserVO userVO = new UserVO();
                userVO.setId(user.getId());
                userVO.setName(user.getName());
                edu.zygxy.pojo.Role role = roleService.getRoleById(user.getRoleId());
                if (role != null)
                    userVO.setRole(role.getName());
                request.setAttribute("userInfo", userVO);
                //获取方法
                MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                Method m = methodSignature.getMethod();
                Annotation[] annotations = m.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Role) {
                        String[] strs = ((Role) annotation).value();
                        int i = 0;
                        for (String str : strs) {
                            Long roleId = Long.parseLong(str);

                            if (roleId.equals(user.getRoleId())) {
                                i++;
                            }
                        }
                        if (i == 0) {
                            PermissionException permissionException = new PermissionException();
                            return new JsonResponse(permissionException.getCode(), permissionException.getMsg());
                        }
                    }
                }
            }
        }
        return joinPoint.proceed();
    }

}
