package edu.zygxy.service.impl;

import edu.zygxy.dao.ConfigMapper;
import edu.zygxy.dao.HolidayMapper;
import edu.zygxy.dao.ScheduleMapper;
import edu.zygxy.dao.WorkCheckMapper;
import edu.zygxy.pojo.Config;
import edu.zygxy.pojo.Holiday;
import edu.zygxy.pojo.WorkCheck;
import edu.zygxy.service.WorkService;
import edu.zygxy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private HolidayMapper holidayMapper;
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private WorkCheckMapper workCheckMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public Holiday getHolidayById(long id) {
        return holidayMapper.getHolidayById(id);
    }

    @Override
    public List<Holiday> listHolidays() {
        return holidayMapper.listHolidays();
    }

    @Override
    public void insertHoliday(Holiday holiday) {
        if (holiday != null) {
            holidayMapper.insertHoliday(holiday);
        }
    }

    @Override
    public void deleteHolidayById(long id) {
        holidayMapper.deleteHolidayById(id);
    }

    @Override
    public void insertConfig(Config config) {
        configMapper.deleteConfig();
        if (config != null) {
            configMapper.insertConfig(config);
        }
    }

    @Override
    public Config getConfig() {
        return configMapper.getConfig();
    }

    @Override
    public boolean insertWorkCheck(WorkCheck workCheck) {
        if (workCheck != null) {
            WorkCheck workCheck1 = workCheckMapper.getWorkCheckByUserIdAndTime(workCheck.getUserId());
            if (workCheck1 == null) {

                Config config = configMapper.getConfig();
                if (config != null) {
                    workCheck.setStart(config.getStart());
                    workCheck.setEnd(config.getEnd());
                }
                //判断是否为假期
                List<Holiday> holidays = holidayMapper.listHolidays();
                workCheck.setType(1);
                for (Holiday holiday : holidays) {
                    String hTime = DateUtil.longToString2(holiday.getTime().getTime());
                    String current = DateUtil.longToString2(System.currentTimeMillis() / 1000);
                    if (hTime.equals(current)) {
                        workCheck.setType(0);
                    }
                }
                workCheckMapper.insertWorkCheck(workCheck);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean offWork(long userId) {
        WorkCheck workCheck1 = workCheckMapper.getWorkCheckByUserIdAndTime(userId);
        if (workCheck1 != null) {
            workCheckMapper.updateWorkCheckEndCheck(userId, 0);
            WorkCheck workCheck2 = workCheckMapper.getWorkCheckByUserIdAndTime(userId);
            Time start = workCheck2.getStartCheck();
            Time end = workCheck2.getEndCheck();
            double workTime = ((double) (end.getTime() - start.getTime())) / 3600000;
            workCheckMapper.updateWorkCheckEndCheck(userId, workTime);
            return true;
        }
        return false;
    }

    @Override
    public List<WorkCheck> listWorkChecksByUserId(long userId) {
        return workCheckMapper.listWorkChecksByUserId(userId);
    }

    @Override
    public List<WorkCheck> listWorkChecks() {
        return workCheckMapper.listWorkChecks();
    }

    @Override
    public void startWork(long userId) {
        WorkCheck workCheck = workCheckMapper.getWorkCheckByUserIdAndTime(userId);
        /*fist time*/
        if (workCheck == null) {
            WorkCheck fw = new WorkCheck();
            fw.setStart(new Time(System.currentTimeMillis()));
            fw.setEnd(new Time(System.currentTimeMillis()));
            fw.setType(1);
            fw.setUserId(userId);
            workCheckMapper.insertWorkCheck(fw);
        } else {
            /*more than once update start time*/
            workCheckMapper.updateWorkCheckStartCheck(userId);
        }
    }
}
