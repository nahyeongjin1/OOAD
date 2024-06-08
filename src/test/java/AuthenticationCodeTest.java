import org.chat6.AuthenticationCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationCodeTest {
    private AuthenticationCode authenticationCode;

    @BeforeEach
    void setUp() {
        authenticationCode = new AuthenticationCode();
    }

    @Test
    void testValidateCode() {
        String validCode = "A1b2c3d4e5";
        authenticationCode.addCode(validCode, 1, 5);
        assertTrue(authenticationCode.validateCode(validCode));
        assertFalse(authenticationCode.validateCode("InvalidCode"));
    }

    @Test
    void testAddCode() {
        String code = "X9yZ8w7v6u";
        int itemCode = 4;
        int itemNum = 10;
        authenticationCode.addCode(code, itemCode, itemNum);
        assertTrue(authenticationCode.validateCode(code));
    }

    @Test
    void testDeleteCode() {
        String code = "P5qR6s7t8u";
        authenticationCode.addCode(code, 7, 3);
        assertTrue(authenticationCode.validateCode(code));
        authenticationCode.deleteCode(code);
        assertFalse(authenticationCode.validateCode(code));
    }

    @Test
    void testGenerateRandomString() {
        String randomString = authenticationCode.generateRandomString();
        assertEquals(10, randomString.length());
        assertTrue(randomString.matches("^[A-Z][a-z]\\d[A-Za-z\\d]{7}$"));
    }
}
