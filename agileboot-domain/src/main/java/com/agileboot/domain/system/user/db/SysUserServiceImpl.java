package com.agileboot.domain.system.user.db;

import com.agileboot.common.core.page.AbstractPageQuery;
import com.agileboot.domain.system.post.db.SysPostEntity;
import com.agileboot.domain.system.role.db.SysRoleEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author valarchie
 * @since 2022-06-16
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {


    @Override
    public boolean isUserNameDuplicated(String username) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserEntity::getUsername, username);
        return this.baseMapper.exists(queryWrapper);
    }


    @Override
    public boolean isPhoneDuplicated(String phone, Long userId) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(userId != null, SysUserEntity::getUserId, userId)
            .eq(SysUserEntity::getPhoneNumber, phone);
        return baseMapper.exists(queryWrapper);
    }


    @Override
    public boolean isEmailDuplicated(String email, Long userId) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(userId != null, SysUserEntity::getUserId, userId)
            .eq(SysUserEntity::getEmail, email);
        return baseMapper.exists(queryWrapper);
    }


    @Override
    public SysRoleEntity getRoleOfUser(Long userId) {
        List<SysRoleEntity> list = baseMapper.getRolesByUserId(userId);
        return list.isEmpty() ? null : list.get(0);
    }


    @Override
    public SysPostEntity getPostOfUser(Long userId) {
        List<SysPostEntity> list = baseMapper.getPostsByUserId(userId);
        return list.isEmpty() ? null : list.get(0);
    }


    @Override
    public Set<String> getMenuPermissionsForUser(Long userId) {
        return baseMapper.getMenuPermsByUserId(userId);
    }


    @Override
    public SysUserEntity getUserByUserName(String userName) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserEntity::getUsername, userName);
        return this.getOne(queryWrapper);
    }


    @Override
    public Page<SysUserEntity> getUserListByRole(AbstractPageQuery<SysUserEntity> query) {
        return baseMapper.getUserListByRole(query.toPage(), query.toQueryWrapper());
    }


    @Override
    public Page<SearchUserDO> getUserList(AbstractPageQuery<SearchUserDO> query) {
        return baseMapper.getUserList(query.toPage(), query.toQueryWrapper());
    }

}
