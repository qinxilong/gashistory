package com.taosdata.example.springbootdemo.dao;

import com.taosdata.example.springbootdemo.domain.GasInfo;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GasInfoProvider {
    /**
     * 批量导入
     */

    public String insertBatch(Map map) {
        List<GasInfo> gasInfoList = (List<GasInfo>) map.get("list");
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        for (GasInfo gasInfo : gasInfoList) {
            sb.append("gas." + gasInfo.getDeviceId()).append(" using gas.gasinfo (groupId) TAGS (2)")
                    .append("(ts, id, deviceid, roomid, gvalue,position,status,reporttime,number,workshop,host,channel,gastype)")
//                    .append("(ts, id, deviceid, roomid, gvalue,position,status,reporttime)")
                    .append(" VALUES(")
                    .append('\'').append(gasInfo.getTs()).append('\'').append(",") // ts
                    .append('\'').append(gasInfo.getId()).append('\'').append(",") //id
                    .append('\'').append(gasInfo.getDeviceId()).append('\'').append(",") //deviceid
                    .append('\'').append(gasInfo.getRoomId()).append('\'').append(",") //roomid
                    .append('\'').append(gasInfo.getGValue()).append('\'').append(",") //gvalue
                    .append('\'').append(gasInfo.getPosition()).append('\'').append(",") //position
                    .append('\'') .append(gasInfo.getStatus()).append('\'').append(",") //status
                    .append('\'').append(gasInfo.getReportTime()).append('\'').append(",") //reporttime
                    .append('\'').append(gasInfo.getNumber()).append('\'').append(",") //number
                    .append('\'').append(gasInfo.getWorkShop()).append('\'').append(",") //workshop
                    .append('\'').append(gasInfo.getHost()).append('\'').append(",") //host
                    .append('\'').append(gasInfo.getChannel()).append('\'').append(",") //channel
                    .append('\'').append(gasInfo.getGasType()).append('\'').append(")"); //gastype

        }
        return sb.toString();
    }
}