package edu.zygxy.pojo;

import java.sql.Time;
import java.sql.Timestamp;


public class WorkCheck {

    private Long id;
    private Timestamp time;
    private String timeStr;
    private Integer type; //0假期 1工作日 2出差 3请假
    private String typeStr;
    private Time start;
    private Time end;
    private Time startCheck;
    private String startCheckStr;
    private Time endCheck;
    private String endCheckStr;
    private Integer workTime;
    private String remark;
    private Long userId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String name;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getStartCheckStr() {
        return startCheckStr;
    }

    public void setStartCheckStr(String startCheckStr) {
        this.startCheckStr = startCheckStr;
    }

    public String getEndCheckStr() {
        return endCheckStr;
    }

    public void setEndCheckStr(String endCheckStr) {
        this.endCheckStr = endCheckStr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public Time getStartCheck() {
        return startCheck;
    }

    public void setStartCheck(Time startCheck) {
        this.startCheck = startCheck;
    }

    public Time getEndCheck() {
        return endCheck;
    }

    public void setEndCheck(Time endCheck) {
        this.endCheck = endCheck;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    @Override
    public String toString() {
        return "WorkCheck{" +
                "id=" + id +
                ", time=" + time +
                ", type=" + type +
                ", start=" + start +
                ", end=" + end +
                ", startCheck=" + startCheck +
                ", endCheck=" + endCheck +
                ", workTime=" + workTime +
                ", remark='" + remark + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
