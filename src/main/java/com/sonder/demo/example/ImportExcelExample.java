package com.sonder.demo.example;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sonder.demo.util.ExcelUtils;
import com.sonder.demo.vo.ImportExcelVO;

import java.io.File;
import java.util.List;

/**
 * @Author likun
 * @Date
 **/
public class ImportExcelExample {

    public static void main(String[] args) {
        String path = ImportExcelExample.class.getResource("/").getPath();
        String filePath = path + "/excel/imporetExcelModel.xlsx";
        System.out.println(filePath);

        List<ImportExcelVO> data = ExcelUtils.importExcel(new File(filePath), ImportExcelVO.class, new ImportParams());
        System.out.println(JSONObject.toJSON(data));
        data.stream().forEach(vo -> System.out.println(vo));
    }

}
