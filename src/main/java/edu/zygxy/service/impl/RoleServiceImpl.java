package edu.zygxy.service.impl;

import edu.zygxy.dao.RoleMapper;
import edu.zygxy.pojo.Role;
import edu.zygxy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getRoleById(long id) {
        return roleMapper.getRoleById(id);
    }

    @Override
    public List<Role> listRoles() {
        return roleMapper.listRoles();
    }
}
