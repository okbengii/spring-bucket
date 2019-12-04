package com.beng.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class Employ {

    @JSONField(name = "EMP_GROUP")
    private String empGroup;
    @JSONField(name = "EMP_ID_CODE")
    private String empIdCode;
    @JSONField(name = "EMP_MEMBERSHIP")
    private List<EmployGroup> empMemberShip;
    @JSONField(name = "EMP_STATUS")
    private String empStatus;

    public String getEmpGroup() {
        return empGroup;
    }

    public void setEmpGroup(String empGroup) {
        this.empGroup = empGroup;
    }

    public String getEmpIdCode() {
        return empIdCode;
    }

    public void setEmpIdCode(String empIdCode) {
        this.empIdCode = empIdCode;
    }

    public List<EmployGroup> getEmpMemberShip() {
        return empMemberShip;
    }

    public void setEmpMemberShip(List<EmployGroup> empMemberShip) {
        this.empMemberShip = empMemberShip;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    @Override
    public String toString() {
        return "Employ [empGroup=" + empGroup + ", empIdCode=" + empIdCode + ", empMemberShip=" + empMemberShip
                + ", empStatus=" + empStatus + "]";
    }

}
