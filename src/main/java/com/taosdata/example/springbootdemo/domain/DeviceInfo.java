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
public class DeviceInfo {
    private String id;
    private String deviceId;//设备id
    private String roomId;//车间
    private String gasType;//其他类型
    private String position;//位置
    private String alarmType;//告警类型
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //private Date alarmTime;//
    private Date reportTime;//数据上报时间
    //新增状态
    private String workShop;//车间
    private String host;//主机
    private String channel;//通道号
    private Float gValue;//气体实时值
    private String number;//厂家设备编号
    private int  alarmLowThreshold = 24;//低报阈值 24
    private int  alarmHighThreshold = 100;//高报阈值 100    private String host;//主机
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date alarmTime;//告警首次发生时间

    public DeviceInfo(GasInfo gasInfo) {
        this.deviceId = gasInfo.getDeviceId();
        this.roomId = gasInfo.getRoomId();
        this.gasType = gasInfo.getGasType();
        this.position = gasInfo.getPosition();
        this.alarmType = gasInfo.getStatus();
        this.reportTime = new Date( gasInfo.getReportTime().getTime());
        //新增
        this.gValue = gasInfo.getGValue();
        this.workShop = gasInfo.getWorkShop();
        this.host = gasInfo.getHost();
        this.channel = gasInfo.getChannel();
        this.number = gasInfo.getNumber();
        if(gasInfo.getExceptStartTime()!=null){
            this.alarmTime = new Date( gasInfo.getExceptStartTime().getTime());
        }
    }

}
