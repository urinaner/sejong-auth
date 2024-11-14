package org.yj.sejongauth.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
    }

    @Test
    @Disabled
    void testAuthenticate_Success() throws IOException {
        // Given
        authService.fetchJsessionId();

        //when
        boolean isAuthenticated = authService.authenticate("학번", "비번");

        // then
        assertTrue(isAuthenticated);
    }

    @Test
    void testAuthenticate_Failure() throws IOException {
        // Given
        LoginReq loginReq = new LoginReq("invalidUser", "invalidPassword");

        // When
        boolean isAuthenticated = authService.authenticate("userId", "password");

        // Then
        assertFalse(isAuthenticated);
    }
}
