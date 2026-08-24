package com.agileboot.admin.customize.service.login;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.math.Calculator;
import cn.hutool.core.util.StrUtil;
import com.agileboot.admin.customize.service.login.dto.CaptchaDTO;
import com.agileboot.common.enums.common.ConfigKeyEnum;
import com.agileboot.domain.common.cache.GuavaCacheService;
import com.agileboot.domain.common.cache.RedisCacheService;
import com.agileboot.domain.system.config.db.SysConfigService;
import com.agileboot.domain.system.dept.db.SysDeptService;
import com.agileboot.infrastructure.config.captcha.CaptchaMathTextCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;

public class CaptchaTest {

    @Test
    public void testHutoolLineCaptchaGeneration() {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(160, 60, 4, 2);
        lineCaptcha.createCode();

        String imageBase64 = lineCaptcha.getImageBase64Data();
        Assertions.assertNotNull(imageBase64);
        Assertions.assertTrue(imageBase64.startsWith("data:image/png;base64,") || !imageBase64.isEmpty());
    }

    @Test
    public void testCaptchaMathTextCreatorCalculations() {
        CaptchaMathTextCreator creator = new CaptchaMathTextCreator();
        for (int i = 0; i < 20; i++) {
            String text = creator.getText();
            String[] parts = text.split("@");
            Assertions.assertEquals(2, parts.length);
            String expression = StrUtil.removeSuffix(parts[0], "=?");
            int expected = Convert.toInt(Calculator.conversion(expression));
            int actual = Integer.parseInt(parts[1]);
            Assertions.assertEquals(expected, actual);
        }
    }

    @Test
    public void testLoginServiceGenerateCaptchaWhenDisabled() {
        TokenService tokenService = Mockito.mock(TokenService.class);
        RedisCacheService redisCache = Mockito.mock(RedisCacheService.class);
        SysConfigService configService = Mockito.mock(SysConfigService.class);
        SysDeptService deptService = Mockito.mock(SysDeptService.class);
        Mockito.when(configService.getConfigValueByKey(ConfigKeyEnum.CAPTCHA.getValue())).thenReturn("false");
        GuavaCacheService guavaCache = new GuavaCacheService(configService, deptService);
        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);

        LoginService loginService = new LoginService(tokenService, redisCache, guavaCache, authenticationManager);
        CaptchaDTO dto = loginService.generateCaptchaImg();

        Assertions.assertNotNull(dto);
        Assertions.assertFalse(dto.getIsCaptchaOn());
    }
}
