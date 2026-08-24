package com.agileboot.domain.system.role.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author valarchie
 */
@Data
@NoArgsConstructor
public class UpdateStatusCommand {

    @NotNull(message = "角色ID不能为空")
    @Positive(message = "角色ID必须大于0")
    private Long roleId;

    @NotNull(message = "状态不能为空")
    private Integer status;

}

