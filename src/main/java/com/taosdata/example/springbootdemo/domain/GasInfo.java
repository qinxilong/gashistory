package com.taosdata.example.springbootdemo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasInfo {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ts = new Timestamp(new Date().getTime());
    private String id="";
    private String deviceId;
    private String roomId;
    @JsonProperty("gValue")
    private Float gValue;
   // private String  state;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp reportTime;
    //private String location;//位置
    private String position;//位置8
    private String status;
    //新增
    private String number;
    private String workShop;
    private String host;
    private String channel;
    private String gasType;//气体类型
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp exceptStartTime;//告警首次发生时间
}
