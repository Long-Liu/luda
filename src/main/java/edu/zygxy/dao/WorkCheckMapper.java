package edu.zygxy.dao;

import edu.zygxy.pojo.WorkCheck;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface WorkCheckMapper {

    int insertWorkCheck(WorkCheck workCheck);

    int updateWorkCheckEndCheck(@Param("userId") long userId, @Param("workTime") double workTime, @Param("address") String address);

    List<WorkCheck> listWorkChecksByUserId(long userId);

    List<WorkCheck> listWorkChecks();

    WorkCheck getWorkCheckByUserIdAndTime(long userId);

    void updateWorkCheckStartCheck(@Param("userId") long userId, @Param("address") String address);
}
