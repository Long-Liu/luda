package edu.zygxy.dao;

import edu.zygxy.pojo.Config;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ConfigMapper {

    Config getConfig();

    int deleteConfig();

    int insertConfig(Config config);
}
