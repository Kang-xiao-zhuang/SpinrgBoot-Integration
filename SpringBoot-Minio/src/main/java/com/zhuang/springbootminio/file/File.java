package com.zhuang.springbootminio.file;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@HeadRowHeight(20)
@ColumnWidth(20)
public class File {

    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    // 对象<==>json串 进行json序列化和反序列的
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm") // jackson
    // 如果前端传递的参数是字串，而不是json时使用这个注解进行如期转换
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date date;

    @ExcelProperty("数字标题")
    private Double doubleData;

    @ExcelIgnore
    private String ignore;
}

