package com.agileboot.domain.system.user.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author valarchie
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateUserCommand extends AddUserCommand {

    @NotNull(message = "用户ID不能为空")
    @Positive(message = "用户ID必须大于0")
    private Long userId;

}

