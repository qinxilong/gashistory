package com.taosdata.example.springbootdemo.dao;

import com.taosdata.example.springbootdemo.domain.DeviceInfo;
import com.taosdata.example.springbootdemo.domain.GasInfo;
import com.taosdata.example.springbootdemo.domain.VarValue;
import com.taosdata.example.springbootdemo.domain.Weather;
import com.taosdata.example.springbootdemo.request.RequestBaseParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
@Mapper
public interface GasInfoMapper {

    int insert(GasInfo gasInfo);

    int insertGasInfo(@Param("gasInfo") GasInfo gasInfo);

    void createTable(GasInfo gasInfo);
    List<GasInfo> selectList(@Param("params") RequestBaseParam params);

    void createSuperTable();

    List<GasInfo> selectListLimit(@Param("params") RequestBaseParam params);
}
