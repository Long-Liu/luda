package edu.zygxy.service;

import edu.zygxy.pojo.Department;

import java.util.List;


public interface DepartmentService {

    Department getDepartmentById(long id);

    List<Department> listDepartments();

    void insertDepartment(Department department);

    void updateDepartment(Department department);

    void deleteDepartmentById(long id);
}
