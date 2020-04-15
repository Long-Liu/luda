package edu.zygxy.service.impl;

import edu.zygxy.dao.RoleMapper;
import edu.zygxy.dao.ScheduleMapper;
import edu.zygxy.dao.UserMapper;
import edu.zygxy.pojo.Role;
import edu.zygxy.pojo.Schedule;
import edu.zygxy.pojo.User;
import edu.zygxy.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public void insertLeave(Schedule schedule) {
        if (schedule != null) {
            schedule.setType(0);
            scheduleMapper.insertSchedule(schedule);
        }
    }

    @Override
    public void insertBuzz(Schedule schedule) {
        if (schedule != null) {
            schedule.setType(1);
            scheduleMapper.insertSchedule(schedule);
        }
    }

    @Override
    public void deleteSchedule(long id) {
        scheduleMapper.deleteSchedule(id);
    }

    @Override
    public List<Schedule> listLeaves(long userId) {
        List<Schedule> schedules = scheduleMapper.listSchedulesByUserIdAndType(userId, 0);
        User usr = userMapper.getUserById(userId);
        Role r = roleMapper.getRoleById(usr.getRoleId());
        if (!"员工".equals(r.getName())) {
            schedules.addAll(scheduleMapper.listButUserId(userId));
        }
        return schedules;
    }

    @Override
    public List<Schedule> listLeaves() {
        return scheduleMapper.listShcedulesByType(0);
    }

    @Override
    public List<Schedule> listBuzzs(long userId) {
        return scheduleMapper.listSchedulesByUserIdAndType(userId, 1);
    }

    @Override
    public List<Schedule> listBuzzs() {
        return scheduleMapper.listShcedulesByType(1);
    }

    @Override
    public void acceptSchedule(long id) {
        scheduleMapper.updateScheduleStatus(id, 1);
    }

    @Override
    public void rejectSchedule(long id) {
        scheduleMapper.updateScheduleStatus(id, -1);
    }
}
