import org.chat6.AdminManager;
import org.chat6.StockManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class AdminManagerTest {

    private AdminManager adminManager;
    private StockManager stockManager;

    @BeforeEach
    public void setUp() {
        stockManager = mock(StockManager.class);
        adminManager = new AdminManager(10, 5, stockManager);
    }

    @Test
    public void testCheckPW_CorrectCredentials() {
        int id = 10;
        int pw = 5;

        adminManager.checkPW(id, pw);
        verify(stockManager).rechargeStock();
    }

    @Test
    public void testCheckPW_IncorrectId() {
        int id = 9;
        int pw = 5;

        adminManager.checkPW(id, pw);
        verify(stockManager, never()).rechargeStock();
    }

    @Test
    public void testCheckPW_IncorrectPw() {
        int id = 10;
        int pw = 9;
        adminManager.checkPW(id, pw);
        verify(stockManager, never()).rechargeStock();
    }
}

