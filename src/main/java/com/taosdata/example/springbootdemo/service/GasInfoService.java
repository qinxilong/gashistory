package com.taosdata.example.springbootdemo.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.taosdata.example.springbootdemo.constant.CommonConstant;
import com.taosdata.example.springbootdemo.dao.GasInfoMapper;
import com.taosdata.example.springbootdemo.domain.ApiResponse;
import com.taosdata.example.springbootdemo.domain.DeviceInfo;
import com.taosdata.example.springbootdemo.domain.GasAlarmHistory;
import com.taosdata.example.springbootdemo.domain.GasInfo;
import com.taosdata.example.springbootdemo.domain.constant.GlobalConstants;
import com.taosdata.example.springbootdemo.feign.GasClient;
import com.taosdata.example.springbootdemo.request.RequestBaseParam;
import com.taosdata.example.springbootdemo.util.CacheUtils;
import com.taosdata.example.springbootdemo.util.RedisUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GasInfoService {
    @Autowired
    private GasInfoMapper gasInfoMapper;
    @Autowired
    private GasClient gasClient;
    @Resource
    private RedisUtil redisUtil;

    private List<String> deviceIdList = new ArrayList<String>();
//    private Map<String,DeviceInfo> deviceInfoMap = new HashMap<String,DeviceInfo>();
//    private Map<String,String> deviceStatusMap = new HashMap<String,String>();//异常设备状态

    @PostConstruct
    public void init() {
        try{
            ApiResponse<List<DeviceInfo>> deviceInfos = gasClient.getDeviceInfoList();
            List<DeviceInfo> deviceInfoList = deviceInfos.getData();
//            System.out.println(deviceInfoList);
            if(deviceInfoList!=null){
                //deviceInfoMap.clear();
                deviceIdList.clear();
                for(DeviceInfo deviceInfo:deviceInfoList){
                    deviceIdList.add(deviceInfo.getDeviceId());
                    // deviceInfoMap.put(deviceInfo.getDeviceId(),deviceInfo);
                }
            }
//            System.out.println(deviceIdList);
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void add(GasInfo gasInfo){
        //gasInfoMapper.insert(gasInfo);
        gasInfoMapper.insertGasInfo(gasInfo);
    }

    public List<GasInfo> selectList(RequestBaseParam params){
        return gasInfoMapper.selectList(params);
    }

    public void gasInfoResolve(GasInfo gasInfo){
        //if(deviceIdList!=null&&deviceIdList.size()==0){
        //    init();
        //}
//        System.out.println(gasInfo);
        if(gasInfo!=null){
            add(gasInfo);//新增历史数据到td中
        }else{
            return;
        }
        DeviceInfo deviceInfo = new DeviceInfo(gasInfo);
        redisUtil.set(GlobalConstants.DEVICE_INFO_KEY + gasInfo.getDeviceId(),deviceInfo);
        if(!deviceIdList.contains(gasInfo.getDeviceId())){//当前设备标没有设备信息
//            DeviceInfo deviceInfo = new DeviceInfo(gasInfo.getDeviceId(),gasInfo.getRoomId(),"一氧化碳",gasInfo.getPosition(),gasInfo.getStatus(),new Date( gasInfo.getReportTime().getTime()));
//            DeviceInfo deviceInfo = new DeviceInfo(gasInfo);
            ApiResponse response = gasClient.saveDeviceInfo(deviceInfo);
            if(response.getCode()==200){
                System.out.println("设备信息入库成功");
                if(deviceInfo!=null&&deviceInfo.getDeviceId()!=null&&!deviceInfo.getDeviceId().equals("")){
                    deviceIdList.add(deviceInfo.getDeviceId());
                }
            }
            redisUtil.set(GlobalConstants.DEVICE_STATUS_KEY + gasInfo.getDeviceId(),gasInfo.getStatus());
        }else {
            if(!redisUtil.hasKey(GlobalConstants.DEVICE_STATUS_KEY + gasInfo.getDeviceId())){//redis没有缓存设备状态
                //更新设备信息（设备状态和时间）
//                DeviceInfo deviceInfo = new DeviceInfo(gasInfo);
                ApiResponse response = gasClient.updateDeviceInfo(deviceInfo);
                if (response.getCode() == 200) {
                    System.out.println("设备状态信息更新成功");
                }
            }
            redisUtil.set(GlobalConstants.DEVICE_STATUS_KEY + gasInfo.getDeviceId(),gasInfo.getStatus());
        }
       /* if(!gasInfo.getStatus().equals(CommonConstant.NORMAL)){//状态异常
            System.out.println("当前状态："+gasInfo.getStatus());
//            if(deviceStatusMap.containsKey(gasInfo.getDeviceId())){
             if(CacheUtils.containsKey(gasInfo.getDeviceId())){
                String lastStatus = (String) CacheUtils.get(gasInfo.getDeviceId());//设备之前的状态信息
                if(lastStatus.equals(gasInfo.getStatus())){
                    System.out.println("已有相同告警，不做处理");
                }else{//告警发生变化，新增告警
                    addGasAlarmHistory(gasInfo);
                }
             }else{//当前没有告警，直接告警表
                addGasAlarmHistory(gasInfo);
             }
        }else {//状态正常
            if (CacheUtils.containsKey(gasInfo.getDeviceId())) {//之前异常，现在正常
                CacheUtils.remove(gasInfo.getDeviceId());
            }
        }*/
    }

    //新增告警信息
   public  void addGasAlarmHistory(GasInfo gasInfo){
       CacheUtils.put(gasInfo.getDeviceId(),gasInfo.getStatus());
       //GasAlarmHistory gasAlarmHistory = new GasAlarmHistory();
       //gasAlarmHistory.setDeviceId(gasInfo.getDeviceId());
       //gasAlarmHistory.setRoomId(gasInfo.getRoomId());
       //gasAlarmHistory.setPosition(gasInfo.getPosition());
       //gasAlarmHistory.setAlarmType(gasInfo.getStatus());
       //gasAlarmHistory.setIsAcked(0);
       //Timestamp reportTime = gasInfo.getReportTime();
       //gasAlarmHistory.setAlarmTime(new Date(reportTime.getTime()));//修改
       GasAlarmHistory gasAlarmHistory = new GasAlarmHistory(gasInfo);//新增字段
       ApiResponse response = gasClient.saveGasAlarmHistory(gasAlarmHistory);
       if(response.getCode()==200){
           System.out.println("告警数据插入成功");
       }else{
           System.out.println("告警数据插入失败");
       }
   }

    public ApiResponse gasInfoStatistics() {
        Map<String, Object> cache = CacheUtils.getCache();
        int abnormalSize = cache.size();
        Map<Object, Long> valueStatistics = cache.values().stream()
        .collect(Collectors.groupingBy(v -> v, Collectors.counting()));

        if(!valueStatistics.containsKey("high")){
            valueStatistics.put((Object) "high", (long) 0);
        }
        if(!valueStatistics.containsKey("low")){
            valueStatistics.put((Object) "low", (long) 0);
        }
        if(!valueStatistics.containsKey("fault")){
            valueStatistics.put((Object) "fault", (long) 0);
        }

        ApiResponse<List<DeviceInfo>> deviceInfos = gasClient.getDeviceInfoList();
        if(deviceInfos!=null&&deviceInfos.getData()!=null&&(deviceInfos.getData().size()>abnormalSize)){
            valueStatistics.put((Object) "normal", (long) (deviceInfos.getData().size()-abnormalSize));
        }else{
            valueStatistics.put((Object) "normal", (long) 0);
        }

        System.out.println("Value Statistics:");
        for (Map.Entry<Object, Long> entry : valueStatistics.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
//        Map<String, Object> cache = new HashMap<>();
//        cache.put("key1", "value1");
//        cache.put("key2", "value2");
//        cache.put("key3", "value1");
//        cache.put("key4", "value3");
//        cache.put("key5", "value1");
//
//        Map<Object, Long> valueStatistics = cache.values().stream()
//                .collect(Collectors.groupingBy(v -> v, Collectors.counting()));
//
//        System.out.println("Value Statistics:");
//        for (Map.Entry<Object, Long> entry : valueStatistics.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }

        System.out.println(valueStatistics);
        System.out.println(valueStatistics);
        return ApiResponse.success(valueStatistics);
    }

    public void createSuperTable() {
        gasInfoMapper.createSuperTable();
    }

    public List<GasInfo> selectListLimit(RequestBaseParam params) {
        return gasInfoMapper.selectListLimit(params);
    }

    public void batchInsert() {
        ArrayList<GasInfo> list = new ArrayList<>();
        GasInfo gasInfo1 = new GasInfo();
        gasInfo1.setId("100");
        gasInfo1.setDeviceId("dev01");
        gasInfo1.setRoomId("room01");
        gasInfo1.setGValue(10F);
        gasInfo1.setReportTime(new Timestamp(new Date().getTime()));
        gasInfo1.setPosition("position01");
        gasInfo1.setStatus("normal");
        gasInfo1.setNumber("1");
        gasInfo1.setWorkShop("ws1");
        gasInfo1.setHost("localhost");
        gasInfo1.setChannel("c1");
        gasInfo1.setGasType("co");
        gasInfo1.setExceptStartTime(new Timestamp(new Date().getTime()));


        GasInfo gasInfo2 = new GasInfo();
        gasInfo2.setId("100");
        gasInfo2.setDeviceId("dev02");
        gasInfo2.setRoomId("room02");
        gasInfo2.setGValue(20F);
        gasInfo2.setReportTime(new Timestamp(new Date().getTime()));
        gasInfo2.setPosition("position02");
        gasInfo2.setStatus("normal2");
        gasInfo2.setNumber("2");
        gasInfo2.setWorkShop("ws2");
        gasInfo2.setHost("localhost2");
        gasInfo2.setChannel("c2");
        gasInfo2.setGasType("co2");
        gasInfo2.setExceptStartTime(new Timestamp(new Date().getTime()));

        list.add(gasInfo1);
        list.add(gasInfo2);
        gasInfoMapper.insertBatch(list);
    }
}
