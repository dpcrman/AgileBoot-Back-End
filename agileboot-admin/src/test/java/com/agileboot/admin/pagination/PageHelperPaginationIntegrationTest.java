package com.agileboot.admin.pagination;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PageHelperPaginationIntegrationTest {

    @Test
    public void testPageHelperPageInfo() {
        Page<String> page = new Page<>(1, 5);
        page.setTotal(12);
        page.add("item1");
        page.add("item2");
        page.add("item3");

        PageInfo<String> pageInfo = new PageInfo<>(page);
        Assertions.assertNotNull(pageInfo);
        Assertions.assertEquals(1, pageInfo.getPageNum());
        Assertions.assertEquals(5, pageInfo.getPageSize());
        Assertions.assertEquals(3, pageInfo.getSize());
        Assertions.assertEquals(12, pageInfo.getTotal());
        Assertions.assertEquals(3, pageInfo.getPages());
    }
}
