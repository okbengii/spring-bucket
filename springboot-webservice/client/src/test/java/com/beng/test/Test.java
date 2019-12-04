package com.beng.test;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.beng.model.Employ;
import com.beng.model.EmployGroup;
import com.beng.model.PackageEmp;

public class Test {

    private static String json = "{\n" + "    \"packId\":\"sadasdadas\",\n" + "    \"packageId\":\"sadasdasdas\",\n"
            + "    \"mdmNameEn\":\"dasdlkaslda\",\n" + "    \"list\":[\n" + "        {\n"
            + "            \"EMP_GROUP\":\"ASDASDAS\",\n" + "            \"EMP_ID_CODE\":\"SDASDSA\",\n"
            + "            \"EMP_MEMBERSHIP\":[\n" + "                {\n"
            + "                    \"EMP_NAME\":\"ADSDASDA\",\n" + "                    \"EMP_CODE\":\"ASDASDASD\"\n"
            + "                }\n" + "            ],\n" + "            \"EMP_STATUS\":\"ASDADASDA\"\n" + "        }\n"
            + "    ]\n" + "}";

    public static void test() {
        JSONObject jsonObject = JSONObject.parseObject(json);
        PackageEmp pack = JSONObject.toJavaObject(jsonObject, PackageEmp.class);

        System.out.println(pack.toString());
        List<Employ> list = pack.getList();
        list.stream().forEach(e -> System.out.println(e.toString()));
        List<EmployGroup> empGroup = list.get(0).getEmpMemberShip();
        empGroup.stream().forEach(e -> System.out.println(e.toString()));

    }

    public static void main(String[] args) {
        test();
    }
}
