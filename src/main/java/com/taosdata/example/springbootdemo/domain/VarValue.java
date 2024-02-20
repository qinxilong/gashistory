package com.taosdata.example.springbootdemo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 变量值
 */
@Data
public class VarValue {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp ts;//时间

    private String id;//变量名+uuid

    private float vvalue;

    private String vid;//变量id
}
