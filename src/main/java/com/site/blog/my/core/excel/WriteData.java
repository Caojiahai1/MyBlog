package com.site.blog.my.core.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @Description
 * @Author caojiahai1
 * @Date 2020/9/9 10:45 上午
 * Version 1.0
 */
@Data
public class WriteData {

    @ColumnWidth(60)
    @ExcelProperty("活动名称")
    private String str;

    @ColumnWidth(20)
    @ExcelProperty("活动数量")
    private Integer count;

}
