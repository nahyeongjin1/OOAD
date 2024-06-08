import org.chat6.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

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
    @Test
    public void testRequestPayment_Success() {
        String cardNumber = "23232323";
        int totalPrice = 50;
        boolean result = cardCompany.requestPayment(cardNumber, totalPrice);
        assertTrue(result);
    }

    @Test
    public void testRequestPayment_InsufficientBalance() {
        String cardNumber = "02050866";
        int totalPrice = 1000;

        boolean result = cardCompany.requestPayment(cardNumber, totalPrice);

        assertFalse(result);
    }

}
