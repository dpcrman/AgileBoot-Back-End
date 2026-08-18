package com.agileboot.domain.system.dept.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author valarchie
 * @since 2022-06-16
 */
public interface SysDeptMapper extends BaseMapper<SysDeptEntity> {

    /**
     * 检查是否存在子部门
     *
     * @param deptId 部门ID
     * @param status 状态过滤（可为 null）
     * @return 是否存在
     */
    boolean hasChildrenDept(@Param("deptId") Long deptId, @Param("status") Integer status);

    /**
     * 判断 childId 是否为 parentId 的子部门
     *
     * @param parentId 父部门ID
     * @param childId  子部门ID
     * @return 是否为子部门
     */
    boolean isChildOfTheDept(@Param("parentId") Long parentId, @Param("childId") Long childId);

}
