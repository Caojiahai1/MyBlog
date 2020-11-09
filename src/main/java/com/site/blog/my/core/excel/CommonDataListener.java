package com.site.blog.my.core.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description
 * @Author caojiahai1
 * @Date 2020/9/9 9:32 上午
 * Version 1.0
 */
public class CommonDataListener extends AnalysisEventListener<Map<Integer, String >> {

    private Map<String, Integer> countMap = new LinkedHashMap<>(1000);

    private String exportPath;

    public CommonDataListener(String exportPath) {
        this.exportPath = exportPath;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("统计行数：" + context.readSheetHolder().getApproximateTotalRowNumber());
        System.out.println("表头" + headMap);
        for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
            count(entry.getValue());
        }
    }

    @Override
    public void invoke(Map<Integer, String > map, AnalysisContext analysisContext) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            count(entry.getValue());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        List<WriteData> writeDataList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            WriteData writeData = new WriteData();
            writeData.setStr(entry.getKey());
            writeData.setCount(entry.getValue());
            writeDataList.add(writeData);
        }
        WriteDataComparator comparator = new WriteDataComparator();
        Collections.sort(writeDataList, comparator);
        File file = new File(exportPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
        String fileName = exportPath + "result.xlsx";
        EasyExcel.write(fileName, WriteData.class).sheet("Sheet1").doWrite(writeDataList);
        System.out.println("统计结束,导出excel：" + fileName);
    }

    private void count(Object key) {
        if (key == null) {
            return;
        }
        Integer lastCount = countMap.get(key);
        lastCount = lastCount == null ? 0 : lastCount;
        countMap.put(key.toString(), lastCount + 1);
    }
}
