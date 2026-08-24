package com.agileboot.domain.system.role.command;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
public class UpdateDataScopeCommand {

    @Positive(message = "角色ID必须大于0")
    private Long roleId;

    @NotNull
    @NotEmpty
    private List<Long> deptIds;

    private Integer dataScope;


}
