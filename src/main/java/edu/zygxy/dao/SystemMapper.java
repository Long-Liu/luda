package edu.zygxy.dao;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SystemMapper {

    void initialization();

    void deleteDepartment();

    void deleteHoliday();

    void deleteSchedule();

    void deleteUser();

    void deleteWorkCheck();

    void backup();
}
