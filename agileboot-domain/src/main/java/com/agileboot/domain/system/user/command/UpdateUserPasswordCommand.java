package com.agileboot.domain.system.user.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
public class UpdateUserPasswordCommand {

    @Positive(message = "用户ID必须大于0")
    private Long userId;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在 6 到 20 个字符之间")
    private String newPassword;

    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

}

