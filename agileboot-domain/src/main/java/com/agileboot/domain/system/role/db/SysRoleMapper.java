package com.agileboot.domain.system.role.db;

import com.agileboot.domain.system.menu.db.SysMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author valarchie
 * @since 2022-06-16
 */
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    /**
     * 根据角色ID查询对应的菜单权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<SysMenuEntity> getMenuListByRoleId(Long roleId);

}
