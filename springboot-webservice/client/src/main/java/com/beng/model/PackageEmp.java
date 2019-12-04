package com.beng.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class PackageEmp {

    @JSONField(name = "packId")
    private String packId;
    @JSONField(name = "packageId")
    private String packageId;
    @JSONField(name = "mdmNameEn")
    private String mdmNameEn;
    @JSONField(name = "list")
    private List<Employ> list;

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getMdmNameEn() {
        return mdmNameEn;
    }

    public void setMdmNameEn(String mdmNameEn) {
        this.mdmNameEn = mdmNameEn;
    }

    public List<Employ> getList() {
        return list;
    }

    public void setList(List<Employ> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PackageEmp [packId=" + packId + ", packageId=" + packageId + ", mdmNameEn=" + mdmNameEn + ", list="
                + list + "]";
    }

}
