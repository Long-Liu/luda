package edu.zygxy.service;

import edu.zygxy.pojo.Schedule;

import java.util.List;


public interface ScheduleService {

    void insertLeave(Schedule schedule);

    void insertBuzz(Schedule schedule);

    void deleteSchedule(long id);

    List<Schedule> listLeaves(long userId);

    List<Schedule> listLeaves();

    List<Schedule> listBuzzs(long userId);

    List<Schedule> listBuzzs();

    void acceptSchedule(long id);

    void rejectSchedule(long id);
}
