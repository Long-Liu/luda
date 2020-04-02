package edu.zygxy.web;

import edu.zygxy.pojo.JsonResponse;
import edu.zygxy.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/api/init", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResponse insertLeave() {
        systemService.initialization();
        return new JsonResponse(null);
    }
}
