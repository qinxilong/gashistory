package com.taosdata.example.springbootdemo.controller;

import com.taosdata.example.springbootdemo.domain.*;
import com.taosdata.example.springbootdemo.service.VarInfoService;
import com.taosdata.example.springbootdemo.service.VarValueService;
import com.taosdata.example.springbootdemo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RequestMapping("/varValue")
@RestController
public class VarValueController {

    @Autowired
    private VarValueService varValueService;

//    @GetMapping("/lastOne")
//    public Weather lastOne() {
//        return weatherService.lastOne();
//    }
//
    @GetMapping("/init")
    public ApiResponse init() {
        try {
            varValueService.createSuperTable();
        }catch (Exception e){
            return ApiResponse.error(500,"初始化变量历史数据超级表失败");
        }
        return ApiResponse.success();
    }
    //暂时不需要
//    @PostMapping("/insert")
//    public ApiResponse insert(@RequestBody VarValue varValue) {
//        try {
//            Timestamp ts = new Timestamp(System.currentTimeMillis());
//            varValue.setTs(ts);
//            varValueService.insert(varValue);
//        }catch (Exception e){
//            return ApiResponse.error(500,"获取变量信息失败");
//        }
//        return ApiResponse.success();
//    }

    @PostMapping("/insertAll")
    public ApiResponse insertAll(@RequestBody VarValue varValue){
        try {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            varValue.setTs(ts);
            varValueService.insertAll(varValue);
        }catch (Exception e){
            return ApiResponse.error(500,"插入单个变量实时数据失败");
        }
        return ApiResponse.success();
    }


    @PostMapping("/query")
    public ApiResponse<List<VarValue>> query(@RequestBody VarValueRequest req) {
        try {
            List<VarValue> vvList = varValueService.query(req);
            return ApiResponse.success(vvList);
        }catch (Exception e){
            return ApiResponse.error(500,"获取变量信息历史数据失败");
        }
    }

    @PostMapping("/insertAllValue")
    public ApiResponse<List<VarValue>> InsertAllVarValue(@RequestBody AllVarValue req) {
        try {
            Timestamp ts = req.getTs();
            List<VarValue> dataList = req.getDataList();
            for(VarValue vv : dataList){
                String id = vv.getVid() +"_"+ ts.getTime();
                vv.setTs(ts);
                vv.setId(id);
                varValueService.insertAll(vv);
            }
            return ApiResponse.success();
        }catch (Exception e){
            return ApiResponse.error(500,"实时变量信息入库失败");
        }
    }

//
//    @GetMapping("/{limit}/{offset}")
//    public List<Weather> queryWeather(@PathVariable Long limit, @PathVariable Long offset) {
//        return weatherService.query(limit, offset);
//    }
//
//    @PostMapping("/{temperature}/{humidity}")
//    public int saveWeather(@PathVariable float temperature, @PathVariable float humidity) {
//        return weatherService.save(temperature, humidity);
//    }
//
//    @GetMapping("/count")
//    public int count() {
//        return weatherService.count();
//    }
//
//    @GetMapping("/subTables")
//    public List<String> getSubTables() {
//        return weatherService.getSubTables();
//    }
//
//    @GetMapping("/avg")
//    public List<Weather> avg() {
//        return weatherService.avg();
//    }
//
//    @GetMapping("/testTable")
//    public List<Weather>testTable() {
//        return weatherService.testTable();
//    }

}
