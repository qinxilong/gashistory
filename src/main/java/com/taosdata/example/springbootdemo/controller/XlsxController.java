package com.taosdata.example.springbootdemo.controller;

import com.taosdata.example.springbootdemo.domain.VarInfo;
import com.taosdata.example.springbootdemo.service.VarInfoService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/xlsx")
public class XlsxController {

    @Autowired
    private VarInfoService varInfoService;

    @GetMapping("/varInfoInit")
    public int readXlsx() {
        //String filePath = "C:\\Users\\qinxiyun\\Desktop\\燃气\\历史数据查询\\钢渣变量\\钢渣主厂房及水处理1.xlsx";
        ArrayList<String> gzfileList = new ArrayList<>();
        gzfileList.add("files/gz/布袋除尘.xlsx");
        gzfileList.add("files/gz/钢渣主厂房及水处理1.xlsx");
        gzfileList.add("files/gz/钢渣主厂房及水处理2.xlsx");
        gzfileList.add("files/gz/钢渣主厂房及水处理3.xlsx");
        gzfileList.add("files/gz/钢渣主厂房及水处理4.xlsx");
        gzfileList.add("files/gz/辊压除尘.xlsx");
        gzfileList.add("files/gz/热风炉.xlsx");

        ArrayList<String> kzfileList = new ArrayList<String>();
        kzfileList.add("files/kz/矿渣.xlsx");

        int i = 0;
        for(String filePath : gzfileList){
            try (FileInputStream fis = new FileInputStream(filePath);
                 Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
                boolean header = true;
                for (Row row : sheet) {
                    List<String> rowData = new ArrayList<>();
                    if(header){
                        header = false;
                        continue;
                    }
                    VarInfo varInfo = new VarInfo();
                    varInfo.setSystemName("钢渣系统");
                    varInfo.setTs(new Timestamp(System.currentTimeMillis()));
                    String id ="";
                    if(row.getRowNum()>0){
                        Cell cell = row.getCell(0);
                        if(cell!=null){
                            id = row.getCell(0).getStringCellValue();
                        }else{
                            continue;
                        }
                        if(id.equals("")){
                            continue;
                        }
                    }
                    String varName = "";
                    if(row.getRowNum()>1){
                        Cell cell = row.getCell(1);
                        if(cell!=null){
                            varName = row.getCell(1).getStringCellValue();
                        }
                    }
                    String varRemark = "";
                    if(row.getRowNum()>2){
                        Cell cell = row.getCell(2);
                        if(cell!=null){
                            varRemark =cell.getStringCellValue();
                        }
                    }
                    if(id==null||id.equals("")){
                        continue;
                    }
                    System.out.println(id+":"+varName+":"+varRemark);
                    varInfo.setId(id);
                    varInfo.setVarName(varName);
                    varInfo.setVarRemark(varRemark);
                    //  System.out.println(varInfo.toString());
                    varInfoService.insert(varInfo);
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       // List<VarInfo> list = new ArrayList<>();
        for(String filePath : kzfileList){
            try (FileInputStream fis = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
                boolean header = true;
                for (Row row : sheet) {
                    List<String> rowData = new ArrayList<>();
                    if(header){
                        header = false;
                        continue;
                    }
                    VarInfo varInfo = new VarInfo();
                    varInfo.setSystemName("矿渣系统");
                    varInfo.setTs(new Timestamp(System.currentTimeMillis()));
                    String id ="";
                    if(row.getRowNum()>0){
                        Cell cell = row.getCell(0);
                        if(cell!=null){
                            id = row.getCell(0).getStringCellValue();
                        }else{
                            continue;
                        }
                        if(id.equals("")){
                            continue;
                        }
                    }
                    String varName = "";
                    if(row.getRowNum()>1){
                        Cell cell = row.getCell(1);
                        if(cell!=null){
                            varName = row.getCell(1).getStringCellValue();
                        }
                    }
                    String varRemark = "";
                    if(row.getRowNum()>2){
                        Cell cell = row.getCell(2);
                        if(cell!=null){
                            varRemark =cell.getStringCellValue();
                        }
                    }
                    if(id==null||id.equals("")){
                        continue;
                    }
                    System.out.println(id+":"+varName+":"+varRemark);
                    varInfo.setId(id);
                    varInfo.setVarName(varName);
                    varInfo.setVarRemark(varRemark);
                  //  System.out.println(varInfo.toString());
                    varInfoService.insert(varInfo);
                    i++;
                    //list.add(varInfo);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       // System.out.println(list);
        System.out.println("总的变量数量："+ i);
        return i;
    }
}