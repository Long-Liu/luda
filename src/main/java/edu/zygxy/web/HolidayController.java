package edu.zygxy.web;

import edu.zygxy.permission.Role;
import edu.zygxy.pojo.*;
import edu.zygxy.service.WorkService;
import edu.zygxy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class HolidayController {

    @Autowired
    private WorkService workService;

    @RequestMapping("/work")
    public String work(ModelMap modelMap, HttpServletRequest request) {
        List<Holiday> holidays = workService.listHolidays();
        for (Holiday holiday : holidays) {
            holiday.setTimeStr(DateUtil.longToString(holiday.getTime().getTime()));
        }
        Config config = workService.getConfig();
        if (config != null) {
            config.setStartStr(config.getStart().toString());
            config.setEndStr(config.getEnd().toString());
        } else {
            config = new Config();
            config.setStartStr("未设置");
            config.setEndStr("未设置");
        }
        UserVO userVO = (UserVO) request.getAttribute("userInfo");
        if (userVO != null) {
            List<WorkCheck> workChecks = workService.listWorkChecksByUserId(userVO.getId());
            for (WorkCheck workCheck : workChecks) {
                workCheck.setTimeStr(DateUtil.longToString2(workCheck.getTime().getTime()));
                workCheck.setStartCheckStr(workCheck.getStartCheck().toString());
                if (workCheck.getEndCheck() != null)
                    workCheck.setEndCheckStr(workCheck.getEndCheck().toString());
                else
                    workCheck.setEndCheckStr("");
                if (workCheck.getType() == 0) {
                    workCheck.setTypeStr("节假日");
                } else {
                    workCheck.setTypeStr("工作日");
                }
                String remark1 = "";
                String remark2 = "";
                if (workCheck.getStartCheck().getTime() > workCheck.getStart().getTime()) {
                    remark1 = "迟到";
                }
                if (workCheck.getEndCheck().getTime() < workCheck.getEnd().getTime()) {
                    remark2 = "早退";
                }
                workCheck.setRemark(remark1 + remark2);
            }
            modelMap.addAttribute("works", workChecks);
        }
        modelMap.addAttribute("config", config);
        modelMap.addAttribute("holidays", holidays);

        return "work";
    }

    @Role({"1", "2", "3", "4"})
    @RequestMapping(value = "/api/work_checks/start", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResponse startWork(HttpServletRequest request) {
        UserVO userVO = (UserVO) request.getAttribute("userInfo");
        WorkCheck workCheck = new WorkCheck();
        if (userVO != null) {
            workCheck.setUserId(userVO.getId());
        }
        if (workService.insertWorkCheck(workCheck)) {
            return new JsonResponse(null);
        }
        return new JsonResponse(404, "今天已经打过卡");
    }

    @Role({"1", "2", "3", "4"})
    @RequestMapping(value = "/api/work_checks/end", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResponse endWork(HttpServletRequest request) {
        UserVO userVO = (UserVO) request.getAttribute("userInfo");
        if (userVO != null) {
            if (workService.offWork(userVO.getId(), o.getAddress())) {
                return new JsonResponse(null);
            }
        }

        return new JsonResponse(400, "今日还未签到");
    }

    @Role({"1"})
    @RequestMapping(value = "/api/holidays", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResponse insertHoliday(@RequestBody Holiday holiday) {
        workService.insertHoliday(holiday);
        return new JsonResponse(null);
    }

    @Role({"1"})
    @RequestMapping(value = "/api/config", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResponse insertConfig(@RequestBody Config config) {
        workService.insertConfig(config);
        return new JsonResponse(null);
    }

    @Role({"1"})
    @RequestMapping(value = "/api/holidays/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public JsonResponse deleteHoliday(@PathVariable long id) {
        workService.deleteHolidayById(id);
        return new JsonResponse(null);
    }
}
