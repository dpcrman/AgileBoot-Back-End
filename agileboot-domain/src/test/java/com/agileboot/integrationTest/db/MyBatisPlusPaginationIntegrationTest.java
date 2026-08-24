package com.agileboot.integrationTest.db;

import com.agileboot.domain.system.role.db.SysRoleEntity;
import com.agileboot.domain.system.role.db.SysRoleService;
import com.agileboot.domain.system.user.db.SysUserEntity;
import com.agileboot.domain.system.user.db.SysUserService;
import com.agileboot.integrationTest.IntegrationTestApplication;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest(classes = IntegrationTestApplication.class)
public class MyBatisPlusPaginationIntegrationTest {

    @Resource
    private SysUserService userService;

    @Resource
    private SysRoleService roleService;

    @Test
    @Rollback
    public void testMyBatisPlusUserPagination() {
        Page<SysUserEntity> page = new Page<>(1, 2);
        Page<SysUserEntity> result = userService.page(page);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getCurrent());
        Assertions.assertEquals(2, result.getSize());
        Assertions.assertTrue(result.getTotal() >= 3);
        Assertions.assertEquals(2, result.getRecords().size());
    }

    @Test
    @Rollback
    public void testMyBatisPlusRolePaginationWithCondition() {
        Page<SysRoleEntity> page = new Page<>(1, 10);
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleEntity::getStatus, 1);

        Page<SysRoleEntity> result = roleService.page(page, queryWrapper);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getTotal() >= 2);
        Assertions.assertFalse(result.getRecords().isEmpty());
    }
}
