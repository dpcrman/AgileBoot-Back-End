package com.agileboot.domain.system.dept.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author valarchie
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateDeptCommand extends AddDeptCommand {

    @NotNull(message = "部门ID不能为空")
    @Positive(message = "部门ID必须大于0")
    private Long deptId;

}

