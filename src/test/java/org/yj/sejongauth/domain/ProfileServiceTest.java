package org.yj.sejongauth.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceTest {

    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        profileService = new ProfileService();
    }

    @Test
    @Disabled
    void testFetchUserProfile_Success() {
        // Given
        String jsessionId = "valid-session-id";

        // When
        SjProfile profile = profileService.fetchUserProfile(jsessionId);

        // Then
        assertNotNull(profile);
        assertEquals("testMajor", profile.getMajor());
    }

    @Test
    void testFetchUserProfile_InvalidSession() {
        // Given
        String jsessionId = "invalid-session-id";

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            profileService.fetchUserProfile(jsessionId);
        });
    }
}