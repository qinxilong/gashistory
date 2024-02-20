package com.taosdata.example.springbootdemo.feign;

import com.taosdata.example.springbootdemo.domain.ApiResponse;
import com.taosdata.example.springbootdemo.domain.DeviceInfo;
import com.taosdata.example.springbootdemo.domain.GasAlarmHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 客户端
 * @author
 */
@FeignClient(name = "gasClient", url ="${gas.url}")
public interface GasClient {

    /**
     * 获取设备列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "${gas.deviceInfoListPath}",consumes = "application/json")
    ApiResponse<List<DeviceInfo>> getDeviceInfoList();

    /**
     * 新增设备信息
     * @param
     */
    @RequestMapping(method = RequestMethod.POST, path = "${gas.deviceInfoAddPath}", consumes = "application/json")
    ApiResponse saveDeviceInfo(DeviceInfo deviceInfodeviceInfo);

    /**
     * 新增加告警信息
     * @param
     */
    @RequestMapping(method = RequestMethod.POST, path = "${gas.alarmHistoryAddPath}", consumes = "application/json")
    ApiResponse saveGasAlarmHistory(GasAlarmHistory gasAlarmHistory);


    /**
     * 更新设备信息
     * @param deviceInfo
     */
    @RequestMapping(method = RequestMethod.PUT, path = "${gas.deviceInfoUpdatePath}", consumes = "application/json")
    ApiResponse updateDeviceInfo(DeviceInfo deviceInfo);

    ///**
    // * 获取当前是否有故障设备（高报和低报）
    // * @return
    // */
//    @RequestMapping(method = RequestMethod.GET, path = "${gas.abnormalDeviceExist}",consumes = "application/json")
//    ApiResponse<Boolean> abnormalDeviceInfoExist(@SpringQueryMap DeviceInfo params);
}

