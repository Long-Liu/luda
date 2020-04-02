package edu.zygxy.web;

import edu.zygxy.pojo.Schedule;
import edu.zygxy.pojo.User;
import edu.zygxy.pojo.WorkCheck;
import edu.zygxy.service.ScheduleService;
import edu.zygxy.service.UserService;
import edu.zygxy.service.WorkService;
import edu.zygxy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class StatController {

    @Autowired
    private WorkService workService;
    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping("/stat")
    public String stat(HttpServletRequest request, ModelMap modelMap) {

        List<WorkCheck> workChecks = workService.listWorkChecks();
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
            if (userService.getUserById(workCheck.getUserId()) != null)
                workCheck.setName(userService.getUserById(workCheck.getUserId()).getName());
        }

        List<Schedule> leaves = scheduleService.listLeaves();
        for (Schedule schedule : leaves) {
            if (schedule.getStatus() == 0) {
                schedule.setStatusStr("审核中");
            } else if (schedule.getStatus() == -1) {
                schedule.setStatusStr("未批准");
            } else if (schedule.getStatus() == 1) {
                schedule.setStatusStr("已批准");
            }
            if (userService.getUserById(schedule.getUserId()) != null)
                schedule.setName(userService.getUserById(schedule.getUserId()).getName());
            schedule.setStartStr(DateUtil.longToString(schedule.getStart().getTime()));
            schedule.setEndStr(DateUtil.longToString(schedule.getEnd().getTime()));
        }
        modelMap.addAttribute("leaves", leaves);

        List<Schedule> buzzs = scheduleService.listBuzzs();
        for (Schedule schedule : buzzs) {
            if (schedule.getStatus() == 0) {
                schedule.setStatusStr("审核中");
            } else if (schedule.getStatus() == -1) {
                schedule.setStatusStr("未批准");
            } else if (schedule.getStatus() == 1) {
                schedule.setStatusStr("已批准");
            }
            schedule.setStartStr(DateUtil.longToString(schedule.getStart().getTime()));
            schedule.setEndStr(DateUtil.longToString(schedule.getEnd().getTime()));
            if (userService.getUserById(schedule.getUserId()) != null)
                schedule.setName(userService.getUserById(schedule.getUserId()).getName());
        }
        modelMap.addAttribute("buzzs", buzzs);

        List<User> users = userService.listUsers();
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("works", workChecks);

        return "stat";
    }
}
