package com.agileboot.domain.system.menu.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author valarchie
 * @since 2022-06-16
 */
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    /**
     * 根据用户查询出所有菜单
     *
     * @param userId 用户id
     * @return 菜单列表
     */
    List<SysMenuEntity> selectMenuListByUserId(@Param("userId") Long userId);


    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

}
