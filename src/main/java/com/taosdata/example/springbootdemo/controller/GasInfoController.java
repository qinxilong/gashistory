package com.taosdata.example.springbootdemo.controller;

import com.taosdata.example.springbootdemo.domain.*;
import com.taosdata.example.springbootdemo.feign.GasClient;
import com.taosdata.example.springbootdemo.request.RequestBaseParam;
import com.taosdata.example.springbootdemo.service.GasInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import javax.annotation.Resource;
//import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/qy/gas")
@RestController
public class GasInfoController {

    @Autowired
    private GasInfoService gasInfoService;


//    @GetMapping("/gasInfo/list")
//    public ApiResponse<List<GasInfo>> selectList( RequestBaseParam params) {
//        List<GasInfo> gasInfoList = new ArrayList<>();
//        try{
//            gasInfoList = gasInfoService.selectList(params);
//            if(gasInfoList==null){
//                return ApiResponse.error(500,"获取燃气历史信息失败");
//            }else{
//                return  ApiResponse.success(gasInfoList);
//            }
//        }catch (Exception e){
//            System.out.println(e);
//            if(e.getMessage().contains("Table does not exist")){
//                return  ApiResponse.success(gasInfoList);
//            }
//            return ApiResponse.error(500,"获取燃气历史信息失败");
//        }
//    }

    @GetMapping("/gasInfo/list")
    public ApiResponse<List<GasInfo>> selectListLimit(RequestBaseParam params) {
        Timestamp startTime = params.getStartTime();
        Timestamp endTime = params.getEndTime();
        long duration = (endTime.getTime() - startTime.getTime())/(3600*1000);//小时
        int interval = 3;//3s
        if(duration>24){
            long hourNum = 30000 / duration;//每个小时的条数
            if(hourNum<1200){//进行采样
                interval= (int) ((1200/hourNum)*3);
            }
        }
        params.setInterval(interval+"s");
        List<GasInfo> gasInfoList = new ArrayList<>();
        try{
            gasInfoList = gasInfoService.selectListLimit(params);
            if(gasInfoList==null){
                return ApiResponse.error(500,"获取燃气历史信息失败");
            }else{
                return  ApiResponse.success(gasInfoList);
            }
        }catch (Exception e){
            System.out.println(e);
            if(e.getMessage().contains("Table does not exist")){
                return  ApiResponse.success(gasInfoList);
            }
            return ApiResponse.error(500,"获取燃气历史信息失败");
        }

    }


    @PostMapping("/gasInfo/add")
    public ApiResponse add(@RequestBody GasInfo gasInfo) {
        try {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            gasInfo.setTs(ts);
            gasInfoService.add(gasInfo);
            return ApiResponse.success();
        }catch (Exception e){
            System.out.println(e);
            return ApiResponse.error(500,"实时变量信息入库失败");
        }
    }

    @GetMapping("/gasInfo/statistics")
    public ApiResponse gasInfoStatistics() {
        try {
            return  gasInfoService.gasInfoStatistics();
//            return ApiResponse.success();
        }catch (Exception e){
            System.out.println(e);
            return ApiResponse.error(500,"设备状态统计异常");
        }
    }

    @GetMapping("/gasInfo/init")
    public ApiResponse init() {
        try {
            gasInfoService.createSuperTable();
        }catch (Exception e){
            return ApiResponse.error(500,"初始化燃气历史数据超级表失败");
        }
        return ApiResponse.success();
    }

    @PostMapping("/gasInfo/batchInsert")
    public ApiResponse batchInsert() {
        try {
            gasInfoService.batchInsert();
        }catch (Exception e){
            return ApiResponse.error(500,"初始化燃气历史数据超级表失败");
        }
        return ApiResponse.success();
    }


}