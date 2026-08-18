package com.agileboot.domain.system.post.db;

import com.agileboot.domain.system.user.db.SysUserEntity;
import com.agileboot.domain.system.user.db.SysUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author valarchie
 * @since 2022-06-16
 */
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPostEntity> implements SysPostService {

    private final SysUserMapper userMapper;

    /**
     * 校验岗位名称是否唯一
     *
     * @param postName 岗位名称
     * @return 结果
     */
    @Override
    public boolean isPostNameDuplicated(Long postId, String postName) {
        LambdaQueryWrapper<SysPostEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(postId != null, SysPostEntity::getPostId, postId)
            .eq(SysPostEntity::getPostName, postName);
        return baseMapper.exists(queryWrapper);
    }

    @Override
    public boolean isPostCodeDuplicated(Long postId, String postCode) {
        LambdaQueryWrapper<SysPostEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(postId != null, SysPostEntity::getPostId, postId)
            .eq(SysPostEntity::getPostCode, postCode);
        return baseMapper.exists(queryWrapper);
    }


    @Override
    public boolean isAssignedToUsers(Long postId) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserEntity::getPostId, postId);
        return userMapper.exists(queryWrapper);
    }


}
