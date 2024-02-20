package com.taosdata.example.springbootdemo.controller;

import com.taosdata.example.springbootdemo.domain.ApiResponse;
import com.taosdata.example.springbootdemo.domain.VarInfo;
import com.taosdata.example.springbootdemo.domain.Weather;
import com.taosdata.example.springbootdemo.service.VarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/varInfo")
@RestController
public class VarInfoController {

    @Autowired
    private VarInfoService varInfoService;

    @GetMapping("/init")
    public ApiResponse init() {
        try {
            varInfoService.init();
        }catch (Exception e){
            return ApiResponse.error(500,"变量信息表初始化失败");
        }
        return ApiResponse.success();
    }

    @GetMapping("/query")
    public ApiResponse<List<VarInfo>> query() {
        List<VarInfo> data;
        try {
            data = varInfoService.query();
        }catch (Exception e){
            return ApiResponse.error(500,"获取变量信息失败");
        }
        return ApiResponse.success(data);
    }

    @GetMapping("/queryList")
    public ApiResponse<List<VarInfo>> getVarInfoList(@RequestParam("name") String name,@RequestParam("limit") Long limit,@RequestParam("offset") Long offset) {
        List<VarInfo> data;
        try {
            data = varInfoService.getVarInfoList(name,limit, offset);
        }catch (Exception e){
            return ApiResponse.error(500,"获取变量信息失败");
        }
        return ApiResponse.success(data);
    }

//    @PostMapping("/insert")
//    public void insert(@RequestBody VarInfo varInfo) {
//         varInfoService.insert(varInfo);
//    }
//
//    @GetMapping("/initTableInfo")
//    public void initTable() {}





//    @GetMapping("/lastOne")
//    public Weather lastOne() {
//        return varInfoService.lastOne();
//    }
//
//    @GetMapping("/init")
//    public int init() {
//        return varInfoService.init();
//    }
//
//    @GetMapping("/{limit}/{offset}")
//    public List<Weather> queryWeather(@PathVariable Long limit, @PathVariable Long offset) {
//        return varInfoService.query(limit, offset);
//    }
//
//    @PostMapping("/{temperature}/{humidity}")
//    public int saveWeather(@PathVariable float temperature, @PathVariable float humidity) {
//        return varInfoService.save(temperature, humidity);
//    }
//
//    @GetMapping("/count")
//    public int count() {
//        return varInfoService.count();
//    }
//
//    @GetMapping("/subTables")
//    public List<String> getSubTables() {
//        return varInfoService.getSubTables();
//    }
//
//    @GetMapping("/avg")
//    public List<Weather> avg() {
//        return varInfoService.avg();
//    }
//
//    @GetMapping("/testTable")
//    public List<Weather>testTable() {
//        return varInfoService.testTable();
//    }

}
