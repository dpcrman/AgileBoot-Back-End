package com.agileboot.domain.system.user.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
public class ChangeStatusCommand {

    @NotNull(message = "用户ID不能为空")
    @Positive(message = "用户ID必须大于0")
    private Long userId;

    @NotBlank(message = "状态不能为空")
    private String status;

}

