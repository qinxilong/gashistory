package com.taosdata.example.springbootdemo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasAlarmHistory {
    private String id;
    private String deviceId;
    private String roomId;
    private String position;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date alarmTime;
    private String alarmType;
    private int isAcked;
    //新增
    private Float gValue;//气体值
    private String workShop;//车间
    private String host;//主机
    private String channel;//通道号
    private String number;//厂家设备编号
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //private Timestamp exceptStartTime;//告警首次发生时间
    public GasAlarmHistory(GasInfo gasInfo) {
        Timestamp reportTime = gasInfo.getReportTime();
        this.id = gasInfo.getId();
        this.deviceId = gasInfo.getDeviceId();
        this.roomId = gasInfo.getRoomId();
        this.position = gasInfo.getPosition();
        this.alarmType = gasInfo.getStatus();
        this.isAcked =0;
        this.alarmTime = new Date(gasInfo.getReportTime().getTime());
        this.gValue = gasInfo.getGValue();
        this.workShop = gasInfo.getWorkShop();
        this.host = gasInfo.getHost();
        this.channel = gasInfo.getChannel();
        this.number = gasInfo.getNumber();
        //this.exceptStartTime=gasInfo.getExceptStartTime();
    }
}
