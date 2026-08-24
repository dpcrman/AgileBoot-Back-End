package com.agileboot.integrationTest.db;

import com.agileboot.domain.system.notice.db.SysNoticeEntity;
import com.agileboot.domain.system.notice.db.SysNoticeService;
import com.agileboot.domain.system.notice.query.NoticeQuery;
import com.agileboot.integrationTest.IntegrationTestApplication;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest(classes = IntegrationTestApplication.class)
class SysNoticeServiceImplTest {

    @Resource
    private SysNoticeService noticeService;

    @Test
    @Rollback
    void testGetNoticeListWithPaginationAndWrapper() {
        NoticeQuery query = new NoticeQuery();
        query.setPageNum(1);
        query.setPageSize(10);

        Page<SysNoticeEntity> noticePage = noticeService.getNoticeList(query.toPage(), query.toQueryWrapper());

        Assertions.assertNotNull(noticePage);
        Assertions.assertTrue(noticePage.getTotal() >= 0);
    }

    @Test
    @Rollback
    void testGetNoticeListWithCreatorFilter() {
        NoticeQuery query = new NoticeQuery();
        query.setPageNum(1);
        query.setPageSize(5);
        query.setCreatorName("admin");

        Page<SysNoticeEntity> noticePage = noticeService.getNoticeList(query.toPage(), query.toQueryWrapper());

        Assertions.assertNotNull(noticePage);
        Assertions.assertNotNull(noticePage.getRecords());
    }

}
