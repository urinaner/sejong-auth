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
    void testAuthenticate_Success() throws IOException {
        // Given
        LoginReq loginReq = new LoginReq("20003210", "991027zZ!");
        authService.fetchJsessionId();

        //when
        boolean isAuthenticated = authService.authenticate(loginReq);

        // then
        assertTrue(isAuthenticated);
    }

    @Test
    void testAuthenticate_Failure() throws IOException {
        // Given
        LoginReq loginReq = new LoginReq("invalidUser", "invalidPassword");

        // When
        boolean isAuthenticated = authService.authenticate(loginReq);

        // Then
        assertFalse(isAuthenticated);
    }
}
