import org.chat6.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class CardCompanyTest {

    private CardCompany cardCompany;

    @BeforeEach
    public void setUp() throws IOException {

        cardCompany = new CardCompany();
    }


    @Test
    public void testRequestValidCard_Valid() {
        int result = cardCompany.requestValidCard("23232323");
        assertEquals(1, result);
    }

    @Test
    public void testRequestValidCard_Invalid() {
        int result = cardCompany.requestValidCard("00000000");
        assertEquals(-1, result);
    }
}
