import org.chat6.*;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrepaymentManagerTest {

    private PrepaymentManager prepaymentManager;
    private Network network;
    private StockManager stockManager;
    private DisplayManager displayManager;
    private PaymentManager paymentManager;
    private AuthenticationCode authenticationCode;

    @BeforeEach
    void setUp() {
        stockManager = mock(StockManager.class);
        authenticationCode = mock(AuthenticationCode.class);
        prepaymentManager = new PrepaymentManager(stockManager, authenticationCode);
        network = mock(Network.class);
        displayManager = mock(DisplayManager.class);
        paymentManager = mock(PaymentManager.class);
        prepaymentManager.init(network, displayManager, paymentManager);
    }

    @Test
    @DisplayName("Test askStockRequest method")
    void testAskStockRequest() {
        int itemCode = 1;
        int itemNum = 10;
        prepaymentManager.askStockRequest(itemCode, itemNum);

        verify(network, times(1)).clientStart(eq(0), any(JSONObject.class));
    }

    @Test
    @DisplayName("Test putMsg method")
    void testPutMsg() {
        JSONObject msgResStock = new JSONObject();
        JSONObject msgReqStock = new JSONObject();
        JSONObject msgResStockContent = new JSONObject();
        JSONObject msgReqStockContent = new JSONObject();

        msgResStockContent.put("item_num", 10);
        msgResStockContent.put("coor_x", 1);
        msgResStockContent.put("coor_y", 1);
        msgResStock.put("msg_content", msgResStockContent);
        msgResStock.put("src_id", "DVM1");

        msgReqStockContent.put("item_num", 5);
        msgReqStock.put("msg_content", msgReqStockContent);

        prepaymentManager.putMsg(msgResStock, msgReqStock);

        List<DVM> dvmList = prepaymentManager.getDvmList();
        assertEquals(1, dvmList.size());
        assertEquals("DVM1", dvmList.get(0).getDvm_id());
    }

    @Test
    @DisplayName("Test sendAskPrepaymentMsg method")
    void testSendAskPrepaymentMsg() {
        prepaymentManager.generateDVMList(1, 1, "Team1");
        int itemCode = 1;
        int itemNum = 10;
        String certCode = "CERT123";

        prepaymentManager.sendAskPrepaymentMsg(itemCode, itemNum, certCode);

        verify(network, times(1)).clientStart(eq(0), any(JSONObject.class));
    }

    @Test
    @DisplayName("Test respPerpayment method")
    void testRespPerpayment() throws InterruptedException {
        JSONObject msgResPrepayment = new JSONObject();
        JSONObject msgResPrepaymentContent = new JSONObject();

        msgResPrepaymentContent.put("availability", "T");
        msgResPrepaymentContent.put("item_code", 1);
        msgResPrepaymentContent.put("item_num", 10);
        msgResPrepayment.put("msg_content", msgResPrepaymentContent);
        msgResPrepayment.put("dst_id", "DVM1");

        prepaymentManager.generateDVMList(1, 1, "DVM1");

        when(paymentManager.startPayment(anyInt(), anyInt())).thenReturn(true);

        prepaymentManager.respPerpayment(msgResPrepayment);

        verify(displayManager, times(1)).printMsgAndMainScene(anyString());
    }

    @Test
    @DisplayName("Test otherVMPrepaymentRequest method")
    void testOtherVMPrepaymentRequest() {
        int itemCode = 1;
        when(stockManager.checkStock(itemCode)).thenReturn(10);

        int stock = prepaymentManager.otherVMPrepaymentRequest(itemCode);

        assertEquals(10, stock);
    }

    @Test
    @DisplayName("Test otherVMPrepaymentResponse method")
    void testOtherVMPrepaymentResponse() {
        int itemCode = 1;
        int itemNum = 10;
        String certCode = "CERT123";

        when(stockManager.selectStock(itemCode, itemNum)).thenReturn(true);

        boolean result = prepaymentManager.otherVMPrepaymentResponse(itemCode, itemNum, certCode);

        assertTrue(result);
        verify(authenticationCode, times(1)).addCode(certCode, itemCode, itemNum);
    }

    @Test
    @DisplayName("Test nearestDVMcoordinate method")
    void testNearestDVMcoordinate() {
        prepaymentManager.generateDVMList(1, 1, "DVM1");
        prepaymentManager.generateDVMList(2, 2, "DVM2");

        String coordinates = prepaymentManager.nearestDVMcoordinate();

        assertEquals("(x : 2, y : 2)", coordinates);
    }
}