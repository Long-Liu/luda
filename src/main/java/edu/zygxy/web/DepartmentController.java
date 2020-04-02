package edu.zygxy.web;

import edu.zygxy.permission.Role;
import edu.zygxy.pojo.Department;
import edu.zygxy.pojo.DepartmentVO;
import edu.zygxy.pojo.JsonResponse;
import edu.zygxy.service.DepartmentService;
import edu.zygxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    @RequestMapping("/department")
    public String department(ModelMap modelMap) {
        List<Department> departments = departmentService.listDepartments();
        List<DepartmentVO> departmentVOList = new ArrayList<DepartmentVO>();
        for (Department department : departments) {
            DepartmentVO departmentVO = new DepartmentVO();
            departmentVO.setId(department.getId());
            departmentVO.setName(department.getName());
            departmentVO.setWorkDesc(department.getDesc());
            departmentVO.setLeaders(userService.listUserNamesByRoleIdAndDepartmentId(3, department.getId()));
            departmentVO.setEmployeeNum(userService.countUserByDepartmentId(department.getId()));
            departmentVOList.add(departmentVO);
        }
        modelMap.addAttribute("departments", departmentVOList);
        return "department";
    }


    @RequestMapping("/department/add")
    public String addDepartment() {
        return "department_add";
    }

    @Role({"1","2"})
    @RequestMapping(value = "/api/departments", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResponse insertDepartment(@RequestBody Department department) {
        departmentService.insertDepartment(department);

        return new JsonResponse(null);
    }

    @Role({"1","2"})
    @RequestMapping(value = "/api/departments/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public JsonResponse deleteDepartment(@PathVariable long id) {
        departmentService.deleteDepartmentById(id);
        return new JsonResponse(null);
    }

}
