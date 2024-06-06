import org.chat6.Admin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnitTest {
    @Test
    void test1(){
        int result = Admin.testPlus(1, 2);
        assertEquals(3, result);
    }

    @Test
    void errorTest1() {
        int result = Admin.testPlus(1, 2);
        assertEquals(4, result);
    }
}
