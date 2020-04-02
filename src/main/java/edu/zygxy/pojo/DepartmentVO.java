package edu.zygxy.pojo;

import java.util.List;


public class DepartmentVO {

    private Long id;
    private String name;
    private Long employeeNum;
    private List<String> leaders;
    private String workDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Long employeeNum) {
        this.employeeNum = employeeNum;
    }

    public List<String> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<String> leaders) {
        this.leaders = leaders;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }

    @Override
    public String toString() {
        return "DepartmentVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employeeNum=" + employeeNum +
                ", leaders=" + leaders +
                ", workDesc='" + workDesc + '\'' +
                '}';
    }
}
