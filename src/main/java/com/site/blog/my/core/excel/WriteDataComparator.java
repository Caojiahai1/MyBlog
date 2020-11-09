package com.site.blog.my.core.excel;

import java.text.Collator;
import java.util.Comparator;

/**
 * @Description
 * @Author caojiahai1
 * @Date 2020/9/9 11:08 上午
 * Version 1.0
 */
public class WriteDataComparator implements Comparator<WriteData> {

    Collator cmp = Collator.getInstance(java.util.Locale.CHINA);

    @Override
    public int compare(WriteData o1, WriteData o2) {
        return cmp.compare(o1.getStr(), o2.getStr());
    }
}
