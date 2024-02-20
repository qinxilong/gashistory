package com.taosdata.example.springbootdemo.dao;

import com.taosdata.example.springbootdemo.domain.VarInfo;
import com.taosdata.example.springbootdemo.domain.Weather;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface VarInfoMapper {

    Map<String, Object> lastOne();

    void dropDB();

    void createDB();

    void createSuperTable();

    void createTable(VarInfo varInfo);

    List<VarInfo> select(@Param("limit") Long limit, @Param("offset") Long offset);

    int insert(VarInfo varInfo);

    int count();

    List<String> getSubTables();

    List<VarInfo> avg();

    List<VarInfo> testTable(@Param("tableName")String table);

    List<VarInfo> select();

    List<VarInfo> getVarInfoList(@Param("name") String name, @Param("limit") Long limit, @Param("offset") Long offset);
}
