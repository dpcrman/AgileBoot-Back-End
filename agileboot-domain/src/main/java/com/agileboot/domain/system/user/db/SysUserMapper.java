package com.agileboot.domain.system.user.db;

import com.agileboot.domain.system.post.db.SysPostEntity;
import com.agileboot.domain.system.role.db.SysRoleEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author valarchie
 * @since 2022-06-16
 */
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户名
     * @return 角色列表
     */
    List<SysRoleEntity> getRolesByUserId(Long userId);

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户名
     * @return 结果
     */
    List<SysPostEntity> getPostsByUserId(Long userId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> getMenuPermsByUserId(Long userId);

    /**
     * 根据条件分页查询角色关联的用户列表
     *
     * @param page         分页对象
     * @param queryWrapper 条件选择器
     * @return 分页处理后的用户列表
     */
    Page<SysUserEntity> getUserListByRole(Page<SysUserEntity> page,
        @Param(Constants.WRAPPER) Wrapper<SysUserEntity> queryWrapper);

    /**
     * 根据条件分页查询用户列表
     * @param page 页码对象
     * @param queryWrapper 查询对象
     * @return 用户信息集合信息
     */
    Page<SearchUserDO> getUserList(Page<SearchUserDO> page,
        @Param(Constants.WRAPPER) Wrapper<SearchUserDO> queryWrapper);

}
