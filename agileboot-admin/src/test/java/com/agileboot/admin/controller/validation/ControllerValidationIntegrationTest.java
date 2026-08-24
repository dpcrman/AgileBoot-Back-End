package com.agileboot.admin.controller.validation;

import com.agileboot.admin.controller.system.SysConfigController;
import com.agileboot.admin.controller.system.SysDeptController;
import com.agileboot.admin.controller.system.SysMenuController;
import com.agileboot.admin.controller.system.SysNoticeController;
import com.agileboot.admin.controller.system.SysPostController;
import com.agileboot.admin.controller.system.SysRoleController;
import com.agileboot.admin.controller.system.SysUserController;
import com.agileboot.common.exception.error.ErrorCode;
import com.agileboot.domain.system.config.ConfigApplicationService;
import com.agileboot.domain.system.dept.DeptApplicationService;
import com.agileboot.domain.system.menu.MenuApplicationService;
import com.agileboot.domain.system.notice.NoticeApplicationService;
import com.agileboot.domain.system.post.PostApplicationService;
import com.agileboot.domain.system.role.RoleApplicationService;
import com.agileboot.domain.system.user.UserApplicationService;
import com.agileboot.infrastructure.exception.GlobalExceptionInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControllerValidationIntegrationTest {

    private MockMvc mockMvcUser;
    private MockMvc mockMvcPost;
    private MockMvc mockMvcDept;
    private MockMvc mockMvcMenu;
    private MockMvc mockMvcNotice;
    private MockMvc mockMvcRole;
    private MockMvc mockMvcConfig;

    @BeforeEach
    public void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        GlobalExceptionInterceptor exceptionInterceptor = new GlobalExceptionInterceptor();

        UserApplicationService userService = Mockito.mock(UserApplicationService.class);
        mockMvcUser = MockMvcBuilders.standaloneSetup(new SysUserController(userService))
            .setControllerAdvice(exceptionInterceptor)
            .setValidator(validator)
            .build();

        PostApplicationService postService = Mockito.mock(PostApplicationService.class);
        mockMvcPost = MockMvcBuilders.standaloneSetup(new SysPostController(postService))
            .setControllerAdvice(exceptionInterceptor)
            .setValidator(validator)
            .build();

        DeptApplicationService deptService = Mockito.mock(DeptApplicationService.class);
        mockMvcDept = MockMvcBuilders.standaloneSetup(new SysDeptController(deptService))
            .setControllerAdvice(exceptionInterceptor)
            .setValidator(validator)
            .build();

        MenuApplicationService menuService = Mockito.mock(MenuApplicationService.class);
        mockMvcMenu = MockMvcBuilders.standaloneSetup(new SysMenuController(menuService))
            .setControllerAdvice(exceptionInterceptor)
            .setValidator(validator)
            .build();

        NoticeApplicationService noticeService = Mockito.mock(NoticeApplicationService.class);
        mockMvcNotice = MockMvcBuilders.standaloneSetup(new SysNoticeController(noticeService))
            .setControllerAdvice(exceptionInterceptor)
            .setValidator(validator)
            .build();

        RoleApplicationService roleService = Mockito.mock(RoleApplicationService.class);
        mockMvcRole = MockMvcBuilders.standaloneSetup(new SysRoleController(roleService))
            .setControllerAdvice(exceptionInterceptor)
            .setValidator(validator)
            .build();

        ConfigApplicationService configService = Mockito.mock(ConfigApplicationService.class);
        mockMvcConfig = MockMvcBuilders.standaloneSetup(new SysConfigController(configService))
            .setControllerAdvice(exceptionInterceptor)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("SysUserController: 新增用户入参为空时返回400业务错误码")
    void testAddUserInvalidParams() throws Exception {
        mockMvcUser.perform(post("/system/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.Client.COMMON_REQUEST_PARAMETERS_INVALID.code()));
    }

    @Test
    @DisplayName("SysPostController: 新增岗位入参为空时返回400业务错误码")
    void testAddPostInvalidParams() throws Exception {
        mockMvcPost.perform(post("/system/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.Client.COMMON_REQUEST_PARAMETERS_INVALID.code()));
    }

    @Test
    @DisplayName("SysDeptController: 新增部门入参为空时返回400业务错误码")
    void testAddDeptInvalidParams() throws Exception {
        mockMvcDept.perform(post("/system/dept")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.Client.COMMON_REQUEST_PARAMETERS_INVALID.code()));
    }

    @Test
    @DisplayName("SysMenuController: 新增菜单入参为空时返回400业务错误码")
    void testAddMenuInvalidParams() throws Exception {
        mockMvcMenu.perform(post("/system/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.Client.COMMON_REQUEST_PARAMETERS_INVALID.code()));
    }

    @Test
    @DisplayName("SysNoticeController: 新增公告入参为空时返回400业务错误码")
    void testAddNoticeInvalidParams() throws Exception {
        mockMvcNotice.perform(post("/system/notices")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.Client.COMMON_REQUEST_PARAMETERS_INVALID.code()));
    }

    @Test
    @DisplayName("SysRoleController: 新增角色入参为空时返回400业务错误码")
    void testAddRoleInvalidParams() throws Exception {
        mockMvcRole.perform(post("/system/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.Client.COMMON_REQUEST_PARAMETERS_INVALID.code()));
    }

    @Test
    @DisplayName("SysConfigController: 修改配置入参为空时返回400业务错误码")
    void testEditConfigInvalidParams() throws Exception {
        mockMvcConfig.perform(put("/system/config/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.Client.COMMON_REQUEST_PARAMETERS_INVALID.code()));
    }

    @Test
    @DisplayName("SysMenuController: 修改菜单Body不含menuId时应正常通过参数校验")
    void testEditMenuWithoutMenuIdInBody() throws Exception {
        mockMvcMenu.perform(put("/system/menus/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"parentId\":0,\"menuName\":\"AgileBoot官网\",\"isButton\":false}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.SUCCESS.code()));
    }

    @Test
    @DisplayName("SysDeptController: 修改部门Body不含deptId时应正常通过参数校验")
    void testEditDeptWithoutDeptIdInBody() throws Exception {
        mockMvcDept.perform(put("/system/dept/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"parentId\":0,\"deptName\":\"研发部\",\"orderNum\":1}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.SUCCESS.code()));
    }

    @Test
    @DisplayName("SysConfigController: 修改配置Body不含configId时应正常通过参数校验")
    void testEditConfigWithoutConfigIdInBody() throws Exception {
        mockMvcConfig.perform(put("/system/config/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"configValue\":\"true\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.SUCCESS.code()));
    }

    @Test
    @DisplayName("SysNoticeController: 修改公告Body不含noticeId时应正常通过参数校验")
    void testEditNoticeWithoutNoticeIdInBody() throws Exception {
        mockMvcNotice.perform(put("/system/notices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"noticeTitle\":\"系统公告\",\"noticeType\":1,\"noticeContent\":\"公告内容\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.SUCCESS.code()));
    }

    @Test
    @DisplayName("SysUserController: 修改用户Body不含userId时应正常通过参数校验")
    void testEditUserWithoutUserIdInBody() throws Exception {
        mockMvcUser.perform(put("/system/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"deptId\":1,\"username\":\"admin\",\"nickname\":\"管理员\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(ErrorCode.SUCCESS.code()));
    }
}
