package com.sonder.demo.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Author likun
 * @Date
 **/
@Data
@ExcelTarget("ImportExcelVO")
public class ImportExcelVO {

    @Excel(name = "出发日期")
    private LocalDate startDate;

    @Excel(name = "出发机场")
    private String sourceAirportCode;

    @Excel(name = "抵达机场")
    private String destinationAirportCode;

    @Excel(name = "机型")
    private String model;

    @Excel(name = "座位数")
    private Integer seatNumber;

    @Excel(name = "是否设置为今日特惠")
    private Boolean todayDiscount;

    @Excel(name = "市场价(万)")
    private BigDecimal marketPrice;

    @Excel(name = "优惠价(万)")
    private BigDecimal salePrice;

}
