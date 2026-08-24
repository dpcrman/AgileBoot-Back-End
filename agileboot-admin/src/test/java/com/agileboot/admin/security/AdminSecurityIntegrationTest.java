package com.agileboot.admin.security;

import com.agileboot.admin.customize.service.login.TokenService;
import com.agileboot.domain.common.cache.RedisCacheService;
import com.agileboot.infrastructure.user.web.SystemLoginUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AdminSecurityIntegrationTest {

    @Test
    public void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "adminPassword123";
        String encoded = encoder.encode(rawPassword);

        Assertions.assertNotNull(encoded);
        Assertions.assertNotEquals(rawPassword, encoded);
        Assertions.assertTrue(encoder.matches(rawPassword, encoded));
        Assertions.assertFalse(encoder.matches("wrongPassword", encoded));
    }

    @Test
    public void testTokenLifecycleAndLogout() {
        RedisCacheService redisCacheService = Mockito.mock(RedisCacheService.class);
        redisCacheService.loginUserCache = Mockito.mock(com.agileboot.infrastructure.cache.redis.RedisCacheTemplate.class);
        TokenService tokenService = new TokenService(redisCacheService);
        tokenService.setHeader("Authorization");
        tokenService.setSecret("testSecuritySecretKeyForAgileBootSpringBoot343JDK21Upgrade");
        tokenService.setAutoRefreshTime(20);

        SystemLoginUser loginUser = new SystemLoginUser();
        loginUser.setUserId(100L);
        loginUser.setUsername("testUser");

        String token = tokenService.createTokenAndPutUserInCache(loginUser);
        Assertions.assertNotNull(token);
        Assertions.assertNotNull(loginUser.getCachedKey());

        // Refresh token
        loginUser.setAutoRefreshCacheTime(System.currentTimeMillis() - 1000);
        tokenService.refreshToken(loginUser);
        Assertions.assertTrue(loginUser.getAutoRefreshCacheTime() > System.currentTimeMillis());
    }
}
