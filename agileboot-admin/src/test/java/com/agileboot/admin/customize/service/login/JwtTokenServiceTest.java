package com.agileboot.admin.customize.service.login;

import cn.hutool.core.util.IdUtil;
import com.agileboot.common.constant.Constants.Token;
import com.agileboot.common.exception.ApiException;
import com.agileboot.domain.common.cache.RedisCacheService;
import com.agileboot.infrastructure.user.web.SystemLoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtTokenServiceTest {

    private TokenService tokenService;
    private RedisCacheService redisCacheService;

    @BeforeEach
    public void setup() {
        redisCacheService = Mockito.mock(RedisCacheService.class);
        redisCacheService.loginUserCache = Mockito.mock(com.agileboot.infrastructure.cache.redis.RedisCacheTemplate.class);
        tokenService = new TokenService(redisCacheService);
        tokenService.setHeader("Authorization");
        tokenService.setSecret("abcdefghijklmnopqrstuvwxyz1234567890");
        tokenService.setAutoRefreshTime(20);
    }

    @Test
    public void testCreateTokenAndParseClaims() {
        SystemLoginUser loginUser = new SystemLoginUser();
        loginUser.setUserId(1L);
        loginUser.setUsername("admin");

        String token = tokenService.createTokenAndPutUserInCache(loginUser);
        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isEmpty());

        // Parse claims using same secret
        byte[] keyBytes;
        try {
            keyBytes = MessageDigest.getInstance("SHA-512")
                .digest("abcdefghijklmnopqrstuvwxyz1234567890".getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        Assertions.assertEquals(loginUser.getCachedKey(), claims.get(Token.LOGIN_USER_KEY));
    }

    @Test
    public void testGetLoginUserWithInvalidTokenThrowsException() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer invalid.jwt.token");

        Assertions.assertThrows(ApiException.class, () -> {
            tokenService.getLoginUser(request);
        });
    }

    @Test
    public void testGetLoginUserWithEmptyHeaderReturnsNull() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("Authorization")).thenReturn(null);

        SystemLoginUser user = tokenService.getLoginUser(request);
        Assertions.assertNull(user);
    }

    @Test
    public void testRefreshToken() {
        SystemLoginUser loginUser = new SystemLoginUser();
        loginUser.setCachedKey(IdUtil.fastUUID());
        loginUser.setAutoRefreshCacheTime(System.currentTimeMillis() - 1000);

        tokenService.refreshToken(loginUser);

        Assertions.assertTrue(loginUser.getAutoRefreshCacheTime() > System.currentTimeMillis());
    }
}
