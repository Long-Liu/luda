package edu.zygxy.service.impl;

import com.alibaba.fastjson.JSON;
import edu.zygxy.dao.UserMapper;
import edu.zygxy.pojo.User;
import edu.zygxy.service.AuthService;
import edu.zygxy.utils.EncryptUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean login(String username, String password, HttpServletResponse response) throws Exception {
        User user = userMapper.getUserByEmail(username);
        if (user != null) {
            if (EncryptUtil.getMD5(password).equals(user.getPassword())) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", user.getId());
                jsonObject.put("username", user.getEmail());
                jsonObject.put("password", user.getPassword());
                jsonObject.put("roleId", user.getRoleId());
                jsonObject.put("timestamp", System.currentTimeMillis() / 1000);
                EncryptUtil encryptUtil = new EncryptUtil("token");
                Cookie cookie = new Cookie("token", encryptUtil.encrypt(jsonObject.toString()));
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 2);
                response.addCookie(cookie);
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        User user = userMapper.getUserByEmail(username);
        if (user != null) {
            try {
                if (EncryptUtil.getMD5(password).equals(user.getPassword())) {
                    Map<String, Object> jsonObject = new HashMap<>();
                    jsonObject.put("id", user.getId());
                    jsonObject.put("username", user.getEmail());
                    jsonObject.put("password", user.getPassword());
                    jsonObject.put("phone", user.getPhone());
                    jsonObject.put("name", user.getName());
                    jsonObject.put("roleId", user.getRoleId());
                    jsonObject.put("timestamp", System.currentTimeMillis() / 1000);
                    EncryptUtil encryptUtil = new EncryptUtil("token");
                    jsonObject.put("token", encryptUtil.encrypt(JSON.toJSONString(jsonObject)));
                    return jsonObject;
                }
            } catch (Exception e) {
                //
            }
        }
        return null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
    }

    @Override
    public User checkAuth(HttpServletRequest request) throws Exception {
        User user = new User();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    if (token != null && !"".equals(token)) {
                        EncryptUtil encryptUtil = new EncryptUtil("token");
                        String str = encryptUtil.decrypt(token);
                        JSONObject jsonObject = new JSONObject(str);
                        user.setId(jsonObject.getLong("id"));
                        user.setEmail(jsonObject.getString("username"));
                        user.setPassword(jsonObject.getString("password"));
                        user.setRoleId(jsonObject.getLong("roleId"));
                        return user;
                    }
                }
            }
        }
        return null;
    }

}
