package edu.zygxy.service;

import edu.zygxy.pojo.Role;

import java.util.List;


public interface RoleService {

    Role getRoleById(long id);

    List<Role> listRoles();
}
