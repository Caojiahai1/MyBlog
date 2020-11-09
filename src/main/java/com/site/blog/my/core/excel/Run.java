package com.site.blog.my.core.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;

/**
 * @Description
 * @Author caojiahai1
 * @Date 2020/9/9 2:03 下午
 * Version 1.0
 */
public class Run {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String fileName = "";
        String exportPath = "";
        if (args.length > 0) {
            fileName = args[0];
            if (args.length > 1) {
                exportPath = args[1];
            }
        }
        if (StringUtils.isEmpty(fileName)) {
            System.out.println("未读到excel读取路径配置，使用默认路径: D:\\excel\\target.xlsx");
            fileName = "D:\\excel\\target.xlsx";
        }
        if (StringUtils.isEmpty(exportPath)) {
            System.out.println("未读到excel导出路径配置，使用默认路径: D:\\excel\\export\\");
            exportPath = "D:\\excel\\export\\";
        }
        System.out.println("开始解析excel：" + fileName);
        EasyExcel.read(fileName, new CommonDataListener(exportPath)).sheet().doRead();
        long endTime = System.currentTimeMillis();
        System.out.println("执行时间：" + (endTime - startTime));
    }
}
