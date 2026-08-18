package com.agileboot.domain.system.dept.db;

import com.agileboot.domain.system.user.db.SysUserEntity;
import com.agileboot.domain.system.user.db.SysUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author valarchie
 * @since 2022-06-16
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDeptEntity> implements SysDeptService {

    private final SysUserMapper userMapper;


    @Override
    public boolean isDeptNameDuplicated(String deptName, Long deptId, Long parentId) {
        LambdaQueryWrapper<SysDeptEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptEntity::getDeptName, deptName)
            .ne(deptId != null, SysDeptEntity::getDeptId, deptId)
            .eq(parentId != null, SysDeptEntity::getParentId, parentId);

        return this.baseMapper.exists(queryWrapper);
    }


    @Override
    public boolean hasChildrenDept(Long deptId, Boolean enabled) {
        Integer status = Boolean.TRUE.equals(enabled) ? 1 : null;
        return this.baseMapper.hasChildrenDept(deptId, status);
    }


    @Override
    public boolean isChildOfTheDept(Long parentId, Long childId) {
        return this.baseMapper.isChildOfTheDept(parentId, childId);
    }


    @Override
    public boolean isDeptAssignedToUsers(Long deptId) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserEntity::getDeptId, deptId);
        return userMapper.exists(queryWrapper);
    }

}
