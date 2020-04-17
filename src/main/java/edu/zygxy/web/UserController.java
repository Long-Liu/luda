package edu.zygxy.web;

import edu.zygxy.dao.CompanyLocationMapper;
import edu.zygxy.pojo.*;
import edu.zygxy.service.*;
import edu.zygxy.utils.LocationUtil;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyLocationMapper companyLocationMapper;
    @Resource
    private WorkService workService;
    @Resource
    private AuthService authService;

    @edu.zygxy.permission.Role({"1", "2", "3", "4"})
    @PostMapping("/api/users/punching")
    @ResponseBody
    public JsonResponse punching(@RequestBody PunchingBO o) {
        List<CompanyLocation> cls = companyLocationMapper.selectList();
        GlobalCoordinates source = new GlobalCoordinates(o.getLat(), o.getLng());
        for (CompanyLocation cl : cls) {
            double d = LocationUtil.getDistanceMeter(source, new GlobalCoordinates(cl.getLatitude(),
                    cl.getLongitude()), Ellipsoid.Sphere);
            if (d <= 1000.D) {
                Integer type = o.getType();
                /*打卡上班*/
                if (type == 1) {
                    workService.startWork(o.getUserId());
                } else {
                    /*打卡下班*/
                    workService.offWork(o.getUserId());
                }
                return new JsonResponse(200, o.getType() == 1 ? "打卡下班成功" : "打卡上班成功");
            }
        }
        return new JsonResponse(400, "距离任何分公司都距离过长！");
    }

    @RequestMapping("/employee")
    public String employee(@RequestParam(defaultValue = "0") long departmentId, ModelMap modelMap) {
        List<User> users;
        if (departmentId != 0) {
            users = userService.listUsersByDepartmentId(departmentId);
        } else {
            users = userService.listUsers();
        }

        List<UserVO> userVOList = new ArrayList<UserVO>();
        for (User user : users) {
            UserVO userVO = new UserVO();
            userVO.setName(user.getName());
            userVO.setId(user.getId());
            Department department = departmentService.getDepartmentById(user.getDepartmentId());
            if (department != null)
                userVO.setDepartment(department.getName());
            userVO.setEmail(user.getEmail());
            userVO.setPhone(user.getPhone());
            Role role = roleService.getRoleById(user.getRoleId());
            if (role != null)
                userVO.setRole(role.getName());
            userVOList.add(userVO);
        }
        List<Department> departments = departmentService.listDepartments();
        modelMap.addAttribute("users", userVOList);
        modelMap.addAttribute("departments", departments);
        return "employee";
    }

    @RequestMapping("/employee/add")
    public String addEmployee(ModelMap modelMap) {
        List<Role> roles = roleService.listRoles();
        List<Department> departments = departmentService.listDepartments();

        modelMap.addAttribute("roles", roles);
        modelMap.addAttribute("departments", departments);
        return "employee_add";
    }

    @RequestMapping("/employee/update")
    public String updateEmployee(@RequestParam long userId, ModelMap modelMap) {
        List<Role> roles = roleService.listRoles();
        List<Department> departments = departmentService.listDepartments();
        User user = userService.getUserById(userId);
        modelMap.addAttribute("roles", roles);
        modelMap.addAttribute("departments", departments);
        modelMap.addAttribute("user", user);
        return "employee_update";
    }

    @edu.zygxy.permission.Role({"1", "2", "3"})
    @RequestMapping(value = "/api/users", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResponse insertUser(@RequestBody User user) throws Exception {
        userService.insertUser(user);
        return new JsonResponse(null);
    }

    @edu.zygxy.permission.Role({"1", "2", "3"})
    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public JsonResponse updateUser(@PathVariable long id, @RequestBody User user) throws Exception {
        user.setId(id);
        userService.updateUser(user);
        return new JsonResponse(null);
    }

    @edu.zygxy.permission.Role({"1", "2", "3"})
    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public JsonResponse deleteUser(@PathVariable long id) throws Exception {
        userService.deleteUserById(id);
        return new JsonResponse(null);
    }
}
