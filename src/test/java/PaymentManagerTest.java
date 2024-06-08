import org.chat6.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PaymentManagerTest {

    @Mock
    private CardCompany cardCompany;

    @Mock
    private DisplayManager displayManager;

    private PaymentManager paymentManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentManager = new PaymentManager(cardCompany);
    }

    @Test
    public void testStartPayment() {
        int itemCode = 1;
        int itemNum = 2;
        int itemPrice = 100;
        int totalPrice = itemPrice * itemNum;

        when(displayManager.calcPrice(itemCode)).thenReturn(itemPrice);
        when(cardCompany.requestPayment(anyString(), eq(totalPrice))).thenReturn(true);

        paymentManager.init(displayManager);
        paymentManager.setCardNumber("1234-5678-9876-5432");
        boolean result = paymentManager.startPayment(itemCode, itemNum);

        assertTrue(result);
        verify(displayManager).calcPrice(itemCode);
        verify(cardCompany).requestPayment("1234-5678-9876-5432", totalPrice);
    }
}
