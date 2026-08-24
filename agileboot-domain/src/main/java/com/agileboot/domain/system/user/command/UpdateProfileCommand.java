package com.agileboot.domain.system.user.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
public class UpdateProfileCommand {

    @Positive(message = "用户ID必须大于0")
    private Long userId;

    private Integer sex;

    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 30, message = "用户昵称长度不能超过 30 个字符")
    private String nickName;

    @Size(max = 20, message = "手机号码长度不能超过 20 个字符")
    private String phoneNumber;

    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;

}

