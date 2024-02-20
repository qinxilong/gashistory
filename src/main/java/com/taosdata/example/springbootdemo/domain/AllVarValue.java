package com.taosdata.example.springbootdemo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
@Data
public class AllVarValue {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp ts;//时间
    private List<VarValue> dataList;
}
