package com.taosdata.example.springbootdemo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 变量信息表
 */
@Data
public class VarInfo {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp ts;//时间
    private String id;//变量ID
    private String systemName;//系统名称 1.钢渣 2、矿渣
    private String varName;//变量名称
    private String varRemark;//变量注释
}
