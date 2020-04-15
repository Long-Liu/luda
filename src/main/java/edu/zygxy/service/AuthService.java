package edu.zygxy.service;

import edu.zygxy.pojo.User;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public interface AuthService {

    boolean login(String username, String password, HttpServletResponse response) throws Exception;

    Map<String, Object> login(String username, String password);

    void logout(HttpServletRequest request, HttpServletResponse response);

    User checkAuth(HttpServletRequest request) throws Exception;
}
