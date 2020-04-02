package edu.zygxy.dao;

import edu.zygxy.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface RoleMapper {

    Role getRoleById(long id);

    List<Role> listRoles();
}
