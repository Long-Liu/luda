package edu.zygxy.dao;

import edu.zygxy.pojo.CompanyLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyLocationMapper {
    List<CompanyLocation> selectList();

    void deleteById(Integer id);

    void insert(@Param("o") CompanyLocation o);

    void updateById(@Param("id") Integer id, @Param("o") CompanyLocation o);

    CompanyLocation selectById(Integer id);
}
