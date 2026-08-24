package com.agileboot.domain.system.common.validation;

import com.agileboot.domain.system.config.command.ConfigUpdateCommand;
import com.agileboot.domain.system.dept.command.AddDeptCommand;
import com.agileboot.domain.system.dept.command.UpdateDeptCommand;
import com.agileboot.domain.system.menu.command.AddMenuCommand;
import com.agileboot.domain.system.menu.command.UpdateMenuCommand;
import com.agileboot.domain.system.notice.command.NoticeAddCommand;
import com.agileboot.domain.system.notice.command.NoticeUpdateCommand;
import com.agileboot.domain.system.post.command.AddPostCommand;
import com.agileboot.domain.system.post.command.UpdatePostCommand;
import com.agileboot.domain.system.role.command.AddRoleCommand;
import com.agileboot.domain.system.role.command.UpdateDataScopeCommand;
import com.agileboot.domain.system.role.command.UpdateRoleCommand;
import com.agileboot.domain.system.role.command.UpdateStatusCommand;
import com.agileboot.domain.system.user.command.AddUserCommand;
import com.agileboot.domain.system.user.command.ChangeStatusCommand;
import com.agileboot.domain.system.user.command.ResetPasswordCommand;
import com.agileboot.domain.system.user.command.UpdateProfileCommand;
import com.agileboot.domain.system.user.command.UpdateUserCommand;
import com.agileboot.domain.system.user.command.UpdateUserPasswordCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommandValidationTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("AddUserCommand: 空字段和非法格式应被校验拦截")
    void testAddUserCommandValidation() {
        AddUserCommand command = new AddUserCommand();
        Set<ConstraintViolation<AddUserCommand>> violations = validator.validate(command);
        Assertions.assertFalse(violations.isEmpty());

        // 验证必填项：deptId, username, nickname
        Assertions.assertTrue(violations.stream().anyMatch(v -> "deptId".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "username".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "nickname".equals(v.getPropertyPath().toString())));

        // 验证非法邮箱
        command.setDeptId(1L);
        command.setUsername("admin");
        command.setNickname("管理员");
        command.setEmail("invalid-email");
        violations = validator.validate(command);
        Assertions.assertTrue(violations.stream().anyMatch(v -> "email".equals(v.getPropertyPath().toString())));

        // 正常填充
        command.setEmail("admin@example.com");
        command.setPhoneNumber("13800138000");
        command.setPassword("123456");
        violations = validator.validate(command);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("UpdateUserCommand: 缺少userId应被拦截")
    void testUpdateUserCommandValidation() {
        UpdateUserCommand command = new UpdateUserCommand();
        command.setDeptId(1L);
        command.setUsername("admin");
        command.setNickname("管理员");
        command.setUserId(null);

        Set<ConstraintViolation<UpdateUserCommand>> violations = validator.validate(command);
        Assertions.assertTrue(violations.stream().anyMatch(v -> "userId".equals(v.getPropertyPath().toString())));

        command.setUserId(1L);
        violations = validator.validate(command);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("ResetPasswordCommand: 非法字段应被拦截")
    void testResetPasswordCommandValidation() {
        ResetPasswordCommand command = new ResetPasswordCommand();
        Set<ConstraintViolation<ResetPasswordCommand>> violations = validator.validate(command);
        Assertions.assertFalse(violations.isEmpty());

        command.setUserId(1L);
        command.setPassword("short"); // 长度小于6
        violations = validator.validate(command);
        Assertions.assertTrue(violations.stream().anyMatch(v -> "password".equals(v.getPropertyPath().toString())));

        command.setPassword("123456");
        violations = validator.validate(command);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("ChangeStatusCommand: 校验状态和userId")
    void testChangeStatusCommandValidation() {
        ChangeStatusCommand command = new ChangeStatusCommand();
        Set<ConstraintViolation<ChangeStatusCommand>> violations = validator.validate(command);
        Assertions.assertFalse(violations.isEmpty());

        command.setUserId(1L);
        command.setStatus("1");
        violations = validator.validate(command);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("UpdateProfileCommand: 校验用户昵称与邮箱格式")
    void testUpdateProfileCommandValidation() {
        UpdateProfileCommand command = new UpdateProfileCommand();
        Set<ConstraintViolation<UpdateProfileCommand>> violations = validator.validate(command);
        Assertions.assertFalse(violations.isEmpty());

        command.setUserId(1L);
        command.setNickName("张三");
        command.setEmail("not-email");
        violations = validator.validate(command);
        Assertions.assertTrue(violations.stream().anyMatch(v -> "email".equals(v.getPropertyPath().toString())));

        command.setEmail("zhangsan@example.com");
        violations = validator.validate(command);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("UpdateUserPasswordCommand: 校验密码字段")
    void testUpdateUserPasswordCommandValidation() {
        UpdateUserPasswordCommand command = new UpdateUserPasswordCommand();
        Set<ConstraintViolation<UpdateUserPasswordCommand>> violations = validator.validate(command);
        Assertions.assertFalse(violations.isEmpty());

        command.setUserId(1L);
        command.setOldPassword("oldPass123");
        command.setNewPassword("newPass123");
        violations = validator.validate(command);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("PostCommands: AddPostCommand & UpdatePostCommand 校验")
    void testPostCommandsValidation() {
        AddPostCommand addCommand = new AddPostCommand();
        Set<ConstraintViolation<AddPostCommand>> violations = validator.validate(addCommand);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> "postCode".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "postName".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "postSort".equals(v.getPropertyPath().toString())));

        addCommand.setPostCode("CEO");
        addCommand.setPostName("首席执行官");
        addCommand.setPostSort(1);
        violations = validator.validate(addCommand);
        Assertions.assertTrue(violations.isEmpty());

        UpdatePostCommand updateCommand = new UpdatePostCommand();
        updateCommand.setPostCode("CEO");
        updateCommand.setPostName("首席执行官");
        updateCommand.setPostSort(1);
        updateCommand.setPostId(null);
        Set<ConstraintViolation<UpdatePostCommand>> updateViolations = validator.validate(updateCommand);
        Assertions.assertTrue(updateViolations.stream().anyMatch(v -> "postId".equals(v.getPropertyPath().toString())));

        updateCommand.setPostId(1L);
        updateViolations = validator.validate(updateCommand);
        Assertions.assertTrue(updateViolations.isEmpty());
    }

    @Test
    @DisplayName("DeptCommands: AddDeptCommand & UpdateDeptCommand 校验")
    void testDeptCommandsValidation() {
        AddDeptCommand addCommand = new AddDeptCommand();
        Set<ConstraintViolation<AddDeptCommand>> violations = validator.validate(addCommand);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> "deptName".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "parentId".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "orderNum".equals(v.getPropertyPath().toString())));

        addCommand.setDeptName("研发部");
        addCommand.setParentId(0L);
        addCommand.setOrderNum(1);
        violations = validator.validate(addCommand);
        Assertions.assertTrue(violations.isEmpty());

        UpdateDeptCommand updateCommand = new UpdateDeptCommand();
        updateCommand.setDeptName("研发部");
        updateCommand.setParentId(0L);
        updateCommand.setOrderNum(1);
        updateCommand.setDeptId(null);
        Set<ConstraintViolation<UpdateDeptCommand>> updateViolations = validator.validate(updateCommand);
        Assertions.assertTrue(updateViolations.stream().anyMatch(v -> "deptId".equals(v.getPropertyPath().toString())));

        updateCommand.setDeptId(1L);
        updateViolations = validator.validate(updateCommand);
        Assertions.assertTrue(updateViolations.isEmpty());
    }

    @Test
    @DisplayName("RoleCommands: AddRoleCommand & UpdateRoleCommand & UpdateStatusCommand 校验")
    void testRoleCommandsValidation() {
        AddRoleCommand addCommand = new AddRoleCommand();
        Set<ConstraintViolation<AddRoleCommand>> violations = validator.validate(addCommand);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> "roleName".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "roleKey".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "roleSort".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "menuIds".equals(v.getPropertyPath().toString())));

        addCommand.setRoleName("管理员");
        addCommand.setRoleKey("admin");
        addCommand.setRoleSort(1);
        addCommand.setMenuIds(List.of(1L, 2L));
        violations = validator.validate(addCommand);
        Assertions.assertTrue(violations.isEmpty());

        UpdateRoleCommand updateCommand = new UpdateRoleCommand();
        updateCommand.setRoleName("管理员");
        updateCommand.setRoleKey("admin");
        updateCommand.setRoleSort(1);
        updateCommand.setMenuIds(List.of(1L, 2L));
        updateCommand.setRoleId(null);
        Set<ConstraintViolation<UpdateRoleCommand>> updateViolations = validator.validate(updateCommand);
        Assertions.assertTrue(updateViolations.stream().anyMatch(v -> "roleId".equals(v.getPropertyPath().toString())));

        updateCommand.setRoleId(1L);
        updateViolations = validator.validate(updateCommand);
        Assertions.assertTrue(updateViolations.isEmpty());

        UpdateStatusCommand statusCommand = new UpdateStatusCommand();
        Set<ConstraintViolation<UpdateStatusCommand>> statusViolations = validator.validate(statusCommand);
        Assertions.assertTrue(statusViolations.stream().anyMatch(v -> "roleId".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(statusViolations.stream().anyMatch(v -> "status".equals(v.getPropertyPath().toString())));

        statusCommand.setRoleId(1L);
        statusCommand.setStatus(1);
        statusViolations = validator.validate(statusCommand);
        Assertions.assertTrue(statusViolations.isEmpty());

        UpdateDataScopeCommand dataScopeCommand = new UpdateDataScopeCommand();
        Set<ConstraintViolation<UpdateDataScopeCommand>> scopeViolations = validator.validate(dataScopeCommand);
        Assertions.assertTrue(scopeViolations.stream().anyMatch(v -> "roleId".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(scopeViolations.stream().anyMatch(v -> "deptIds".equals(v.getPropertyPath().toString())));

        dataScopeCommand.setRoleId(1L);
        dataScopeCommand.setDeptIds(List.of(1L));
        scopeViolations = validator.validate(dataScopeCommand);
        Assertions.assertTrue(scopeViolations.isEmpty());
    }

    @Test
    @DisplayName("MenuCommands: AddMenuCommand & UpdateMenuCommand 校验")
    void testMenuCommandsValidation() {
        AddMenuCommand addCommand = new AddMenuCommand();
        Set<ConstraintViolation<AddMenuCommand>> violations = validator.validate(addCommand);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> "menuName".equals(v.getPropertyPath().toString())));

        addCommand.setMenuName("用户管理");
        violations = validator.validate(addCommand);
        Assertions.assertTrue(violations.isEmpty());

        UpdateMenuCommand updateCommand = new UpdateMenuCommand();
        updateCommand.setMenuName("用户管理");
        updateCommand.setMenuId(null);
        Set<ConstraintViolation<UpdateMenuCommand>> updateViolations = validator.validate(updateCommand);
        Assertions.assertTrue(updateViolations.stream().anyMatch(v -> "menuId".equals(v.getPropertyPath().toString())));

        updateCommand.setMenuId(1L);
        updateViolations = validator.validate(updateCommand);
        Assertions.assertTrue(updateViolations.isEmpty());
    }

    @Test
    @DisplayName("NoticeCommands: NoticeAddCommand & NoticeUpdateCommand 校验")
    void testNoticeCommandsValidation() {
        NoticeAddCommand addCommand = new NoticeAddCommand();
        Set<ConstraintViolation<NoticeAddCommand>> violations = validator.validate(addCommand);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> "noticeTitle".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "noticeType".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "noticeContent".equals(v.getPropertyPath().toString())));

        addCommand.setNoticeTitle("系统维护公告");
        addCommand.setNoticeType("1");
        addCommand.setNoticeContent("系统将于今晚维护升级");
        violations = validator.validate(addCommand);
        Assertions.assertTrue(violations.isEmpty());

        NoticeUpdateCommand updateCommand = new NoticeUpdateCommand();
        updateCommand.setNoticeTitle("系统维护公告");
        updateCommand.setNoticeType("1");
        updateCommand.setNoticeContent("系统将于今晚维护升级");
        updateCommand.setNoticeId(null);
        Set<ConstraintViolation<NoticeUpdateCommand>> updateViolations = validator.validate(updateCommand);
        Assertions.assertTrue(updateViolations.stream().anyMatch(v -> "noticeId".equals(v.getPropertyPath().toString())));

        updateCommand.setNoticeId(1L);
        updateViolations = validator.validate(updateCommand);
        Assertions.assertTrue(updateViolations.isEmpty());
    }

    @Test
    @DisplayName("ConfigCommands: ConfigUpdateCommand 校验")
    void testConfigCommandsValidation() {
        ConfigUpdateCommand command = new ConfigUpdateCommand();
        Set<ConstraintViolation<ConfigUpdateCommand>> violations = validator.validate(command);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> "configId".equals(v.getPropertyPath().toString())));
        Assertions.assertTrue(violations.stream().anyMatch(v -> "configValue".equals(v.getPropertyPath().toString())));

        command.setConfigId(1L);
        command.setConfigValue("true");
        violations = validator.validate(command);
        Assertions.assertTrue(violations.isEmpty());
    }
}
