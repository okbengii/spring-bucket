package com.beng.model;

import com.alibaba.fastjson.annotation.JSONField;

public class EmployGroup {

    @JSONField(name = "EMP_NAME")
    private String empName;
    @JSONField(name = "EMP_CODE")
    private String empCode;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    @Override
    public String toString() {
        return "EmployGroup [empName=" + empName + ", empCode=" + empCode + "]";
    }

}
