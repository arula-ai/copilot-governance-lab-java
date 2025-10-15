package com.github.copilot.governancelab.service;

import com.github.copilot.governancelab.model.AuthResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
/** 
 * Sample test for the lab instructions. The test intentionally misses
 * important assertions so learners can expand coverage later.
 */
class AuthServiceTest {

    @Test
    void encodesPasswordInResponse() {
        AuthService service = new AuthService();
        AuthResponse response = service.login("sample", "super-secret", "JUnit");
        assertThat(response.getEncodedPassword()).isNotBlank();
    }
}
