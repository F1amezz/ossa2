package com.ossjk.qlh.system.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class TestPOI {


    public static void main(String[] args) {
        //解析excel文件
        ImportParams params = new ImportParams();
       // tet1();
        try {
            List<Stu> list = ExcelImportUtil.importExcel(new FileInputStream("D:/imgs/学生信息.xls"), Stu.class,params);
            //ExcelExportUtil

            for(Stu s: list){
                System.out.println(s.getName()+","+s.getMobile());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void tet1() {
        try {
            //Workbook 是Poi对Excel的程序抽象
            Workbook excel = new HSSFWorkbook(new FileInputStream("D:/imgs/学生信息.xls"));
            //Sheet
            Sheet sheet =  excel.getSheet("Sheet1");

            Row row = sheet.getRow(0);

            Cell cell = row.getCell(0);

            System.out.println( cell.getStringCellValue()  );

            int rownum =1;
            int rowMax = sheet.getLastRowNum()+1;


            while(  rownum<rowMax  ){

                row = sheet.getRow(rownum);

                System.out.println("姓名："+row.getCell(0).getStringCellValue());
                if(row.getCell(1).getCellType() == CellType.NUMERIC){
                    System.out.println("电话："+row.getCell(1).getNumericCellValue());
                }else if (row.getCell(1).getCellType() == CellType.STRING){
                    System.out.println("电话："+row.getCell(1).getStringCellValue());
                }


                rownum++;
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
