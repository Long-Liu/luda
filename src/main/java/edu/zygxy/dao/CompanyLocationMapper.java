package edu.zygxy.dao;

import edu.zygxy.pojo.CompanyLocation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyLocationMapper {
    List<CompanyLocation> selectList();

    void deleteById(Integer id);
}
