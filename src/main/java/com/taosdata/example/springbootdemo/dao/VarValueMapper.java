package com.taosdata.example.springbootdemo.dao;

import com.taosdata.example.springbootdemo.domain.AllVarValue;
import com.taosdata.example.springbootdemo.domain.VarValue;
import com.taosdata.example.springbootdemo.domain.VarValueRequest;
import com.taosdata.example.springbootdemo.domain.Weather;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface VarValueMapper {

    Map<String, Object> lastOne();

    void dropDB();

    void createDB();

    void createSuperTable();

    void createTable(VarValue varValue);

    List<VarValue> select(@Param("limit") Long limit, @Param("offset") Long offset);

    int insert(@Param("tableName")String table,@Param("varValue")VarValue varValue);

    int count();

    List<String> getSubTables();

    List<VarValue> avg();

    List<VarValue> testTable(@Param("tableName")String table);

    int insertAll(@Param("varValue")VarValue varValue);

    List<VarValue> query(VarValueRequest req);
    void insertList(AllVarValue req);
}
