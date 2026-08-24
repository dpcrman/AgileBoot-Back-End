package com.agileboot.domain.system.user.command;

import com.agileboot.common.annotation.ExcelColumn;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
public class AddUserCommand {

    @ExcelColumn(name = "部门ID")
    @NotNull(message = "部门ID不能为空")
    @Positive(message = "部门ID必须大于0")
    private Long deptId;

    @ExcelColumn(name = "用户名")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 2, max = 30, message = "用户账号长度必须在 2 到 30 个字符之间")
    private String username;

    @ExcelColumn(name = "昵称")
    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 30, message = "用户昵称长度不能超过 30 个字符")
    private String nickname;

    @ExcelColumn(name = "邮件")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;

    @ExcelColumn(name = "电话号码")
    @Size(max = 20, message = "手机号码长度不能超过 20 个字符")
    private String phoneNumber;

    @ExcelColumn(name = "性别")
    private Integer sex;

    @ExcelColumn(name = "头像")
    @Size(max = 500, message = "头像地址长度不能超过 500 个字符")
    private String avatar;

    @ExcelColumn(name = "密码")
    @Size(min = 6, max = 20, message = "密码长度必须在 6 到 20 个字符之间")
    private String password;

    @ExcelColumn(name = "状态")
    private Integer status;

    @ExcelColumn(name = "角色ID")
    @PositiveOrZero(message = "角色ID必须大于等于0")
    private Long roleId;

    @ExcelColumn(name = "职位ID")
    @PositiveOrZero(message = "职位ID必须大于等于0")
    private Long postId;

    @ExcelColumn(name = "备注")
    @Size(max = 500, message = "备注长度不能超过 500 个字符")
    private String remark;


}

