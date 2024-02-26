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

/*       insert into gas.${gasInfo.deviceId} using gas.gasinfo (groupId) TAGS (2)
        (ts, id, deviceid, roomid, gvalue,position,status,reporttime,number,workshop,host,channel,gastype)
        values
                (
                        #{gasInfo.ts},
         #{gasInfo.id},
         #{gasInfo.deviceId},
         #{gasInfo.roomId},
         #{gasInfo.gValue},
         #{gasInfo.position},
         #{gasInfo.status},
         #{gasInfo.reportTime},
         #{gasInfo.number},
         #{gasInfo.workShop},
         #{gasInfo.host},
         #{gasInfo.channel},
         #{gasInfo.gasType}
         )*/

//        INSERT INTO d1001 VALUES (ts1, 10.3, 219, 0.31) (ts2, 12.6, 218, 0.33) d1002 VALUES (ts3, 12.3, 221, 0.31);

        List<GasInfo> gasInfoList = (List<GasInfo>) map.get("list");

        StringBuilder sb = new StringBuilder("INSERT INTO ");
        for (GasInfo gasInfo : gasInfoList) {
            sb.append("gas." + gasInfo.getDeviceId()).append(" using gas.gasinfo (groupId) TAGS (2)")
                    .append("(ts, id, deviceid, roomid, gvalue,position,status,reporttime,number,workshop,host,channel,gastype)")
                    .append(" VALUES(")
                    .append('\'').append(gasInfo.getTs()).append('\'').append(",") // ts
                    .append(gasInfo.getId()).append(",") // id
                    .append(gasInfo.getDeviceId()).append(",") // deviceId
                    .append(gasInfo.getRoomId()).append(",") // roomId
                    .append(gasInfo.getGValue()).append(",") // gValue
                    .append(gasInfo.getPosition()).append(",") // position
                    .append(gasInfo.getStatus()).append(",") // status
                    .append(gasInfo.getReportTime()).append(",") // reportTime
                    .append(gasInfo.getNumber()).append(",") // number
                    .append(gasInfo.getWorkShop()).append(",") // workShop
                    .append(gasInfo.getHost()).append(",") // host
                    .append(gasInfo.getChannel()).append(",") // channel
                    .append(gasInfo.getGasType()).append(") "); // gasType
        }
        return sb.toString();
    }
}