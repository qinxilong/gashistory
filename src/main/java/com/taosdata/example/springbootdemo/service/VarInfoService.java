package com.taosdata.example.springbootdemo.service;

import com.taosdata.example.springbootdemo.dao.VarInfoMapper;
import com.taosdata.example.springbootdemo.dao.WeatherMapper;
import com.taosdata.example.springbootdemo.domain.VarInfo;
import com.taosdata.example.springbootdemo.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class VarInfoService {

    @Autowired
    private VarInfoMapper varInfoMapper;

    public void init() {
        varInfoMapper.createSuperTable();
    }

    public List<VarInfo> query() {
        return varInfoMapper.select();
    }

    public void insert(VarInfo varInfo) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        varInfo.setTs(ts);
        varInfoMapper.insert(varInfo);
    }

    public List<VarInfo> getVarInfoList(String name, Long limit, Long offset) {
        return varInfoMapper.getVarInfoList(name,limit,offset);
    }

//    private Random random = new Random(System.currentTimeMillis());
//    private String[] locations = {"北京", "上海", "广州", "深圳", "天津"};
//
//    public int init() {
//        varInfoMapper.dropDB();
//        varInfoMapper.createDB();
//        varInfoMapper.createSuperTable();
//        long ts = System.currentTimeMillis();
//        long thirtySec = 1000 * 30;
//        int count = 0;
//        for (int i = 0; i < 20; i++) {
//            Weather weather = new Weather(new Timestamp(ts + (thirtySec * i)), 30 * random.nextFloat(), random.nextInt(100));
//            weather.setLocation(locations[random.nextInt(locations.length)]);
//            weather.setGroupId(i % locations.length);
//            weather.setNote("note-" + i);
//            weather.setBytes(locations[random.nextInt(locations.length)].getBytes(StandardCharsets.UTF_8));
//            varInfoMapper.createTable(weather);
//            count += varInfoMapper.insert(weather);
//        }
//        return count;
//    }
//
//    public List<Weather> query(Long limit, Long offset) {
//        return weatherMapper.select(limit, offset);
//    }
//
//    public int save(float temperature, float humidity) {
//        Weather weather = new Weather();
//        weather.setTemperature(temperature);
//        weather.setHumidity(humidity);
//
//        return weatherMapper.insert(weather);
//    }
//
//    public int count() {
//        return weatherMapper.count();
//    }
//
//    public List<String> getSubTables() {
//        return weatherMapper.getSubTables();
//    }
//
//    public List<Weather> avg() {
//        return weatherMapper.avg();
//    }
//
//    public Weather lastOne() {
//        Map<String, Object> result = weatherMapper.lastOne();
//
//        long ts = (long) result.get("ts");
//        float temperature = (float) result.get("temperature");
//        float humidity = (float) result.get("humidity");
//        String note = (String) result.get("note");
//        int groupId = (int) result.get("groupid");
//        String location = (String) result.get("location");
//
//        Weather weather = new Weather(new Timestamp(ts), temperature, humidity);
//        weather.setNote(note);
//        weather.setGroupId(groupId);
//        weather.setLocation(location);
//        return weather;
//    }
//
//    public List<Weather> testTable() {
//        return weatherMapper.testTable("weather");
//    }
}
