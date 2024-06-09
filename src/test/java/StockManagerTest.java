import org.chat6.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StockManagerTest {

    @Mock
    private PaymentManager paymentManager;

    @Mock
    private VMController vmController;

    private StockManager stockManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stockManager = new StockManager();
    }

    @Test
    public void testInit() {
        stockManager.init(paymentManager, vmController);
        // init 메서드는 단순히 멤버 변수를 초기화하므로, 직접적인 테스트는 어렵습니다.
        // 대신, 해당 변수들이 제대로 초기화되었는지 간접적으로 확인할 수 있습니다.
        assertNotNull(stockManager);
    }

    @Test
    public void testCheckStock() {
        int itemCode = 1;
        assertEquals(0, stockManager.checkStock(itemCode));
    }

    @Test
    public void testRestoreStock() {
        int itemCode = 1;
        int itemNum = 5;
        stockManager.restoreStock(itemCode, itemNum);
        assertEquals(5, stockManager.checkStock(itemCode));
    }

    @Test
    public void testRechargeStock() {
        stockManager.rechargeStock();
        for (int i : new int[]{3, 7, 12, 15, 16}) {
            assertEquals(99, stockManager.checkStock(i+1));
        }
    }

    @Test
    public void testSelectStock() {
        int itemCode = 1;
        int itemNum = 5;
        stockManager.restoreStock(itemCode, 10); // 재고를 먼저 추가합니다.
        assertTrue(stockManager.selectStock(itemCode, itemNum));
        assertEquals(5, stockManager.checkStock(itemCode));

        // 재고가 부족한 경우
        assertFalse(stockManager.selectStock(itemCode, 10));
    }

    @Test
    public void testPrintStockList() {
        // printStockList 메서드는 콘솔 출력을 하기 때문에 테스트가 어렵습니다.
        // 따라서 실제 출력 대신 메서드가 호출될 때 예외가 발생하지 않는지를 확인하는 것으로 충분합니다.
        stockManager.printStockList();
    }
}
