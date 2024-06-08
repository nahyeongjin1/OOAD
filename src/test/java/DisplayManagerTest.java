import org.chat6.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.swing.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class DisplayManagerTest {

    @Mock
    private AuthenticationCode authenticationCode;

    @Mock
    private CardCompany cardCompany;

    @Mock
    private StockManager stockManager;

    @Mock
    private AdminManager adminManager;

    @Mock
    private PrepaymentManager prepaymentManager;

    @Mock
    private PaymentManager paymentManager;

    @Mock
    private VMController vmController;

    private DisplayManager displayManager;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        SwingUtilities.invokeAndWait(() -> displayManager = new DisplayManager(authenticationCode, cardCompany, stockManager, adminManager, vmController, prepaymentManager, paymentManager));
    }

    @Test
    public void testPrintMainScene() throws Exception {
        SwingUtilities.invokeAndWait(() -> displayManager.printMainScene());
        assertEquals(1, displayManager.getContentPane().getComponentCount());
        assertTrue(displayManager.getContentPane().getComponent(0) instanceof JPanel);
    }

    @Test
    public void testClickInputCard() throws Exception {
        SwingUtilities.invokeAndWait(() -> displayManager.clickInputCard());
        assertEquals(1, displayManager.getContentPane().getComponentCount());
        assertTrue(displayManager.getContentPane().getComponent(0) instanceof JPanel);
    }

    @Test
    public void testClickInputCode() throws Exception {
        SwingUtilities.invokeAndWait(() -> displayManager.clickInputCode());
        assertEquals(1, displayManager.getContentPane().getComponentCount());
        assertTrue(displayManager.getContentPane().getComponent(0) instanceof JPanel);
    }

    @Test
    public void testClickInputAdmin() throws Exception {
        SwingUtilities.invokeAndWait(() -> displayManager.clickInputAdmin());
        assertEquals(1, displayManager.getContentPane().getComponentCount());
        assertTrue(displayManager.getContentPane().getComponent(0) instanceof JPanel);
    }

    @Test
    public void testPrintMsgAndMainScene() throws Exception {
        SwingUtilities.invokeAndWait(() -> displayManager.printMsgAndMainScene("test message"));
        assertEquals(1, displayManager.getContentPane().getComponentCount());
        assertTrue(displayManager.getContentPane().getComponent(0) instanceof JPanel);
    }

    @Test
    public void testShowErrorMessage() throws Exception {
        JLabel label = new JLabel();
        SwingUtilities.invokeAndWait(() -> displayManager.showErrorMessage(label));
        assertTrue(label.isVisible());
    }

    @Test
    public void testShowItems() throws Exception {
        SwingUtilities.invokeAndWait(() -> displayManager.showItems());
        assertEquals(1, displayManager.getContentPane().getComponentCount());
        assertTrue(displayManager.getContentPane().getComponent(0) instanceof JPanel);
    }
}
