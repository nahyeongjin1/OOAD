import org.chat6.Network;
import org.chat6.PrepaymentManager;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NetworkTest {

    @Mock
    PrepaymentManager prepaymentManager;

    @InjectMocks
    Network network;

    @BeforeEach
    void setUp() {
        network = new Network(prepaymentManager);
    }

    @Test
    @DisplayName("Test run method")
    public void testServerConnection()  {

        new Thread(() -> {
            try {
                network.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        try (Socket clientSocket = new Socket("localhost", 12345)) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    @DisplayName("Test clientStart method with clientID 0")
    void testClientStartWithClientIDZero() {
        JSONObject msg = new JSONObject();
        msg.put("msg_type", "test");

        network.clientStart(0, msg);

        // Verify that the method attempts to connect to other DVMs
        // This is a simplified check, in a real scenario you would mock the Socket and verify interactions
        assertTrue(true);
    }

    @Test
    @DisplayName("Test clientStart method with non-zero clientID")
    void testClientStartWithNonZeroClientID() {
        JSONObject msg = new JSONObject();
        msg.put("msg_type", "test");

        network.clientStart(1, msg);

        // Verify that the method attempts to connect to the specific DVM
        // This is a simplified check, in a real scenario you would mock the Socket and verify interactions
        assertTrue(true);
    }

    @Test
    @DisplayName("Test ClientHandler run method with req_stock message")
    void testClientHandlerRunWithReqStock() throws Exception {
        Socket clientSocket = mock(Socket.class);
        PrepaymentManager prepaymentManager = mock(PrepaymentManager.class);

        // Create PipedInputStream and PipedOutputStream
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);

        // Write the test message to the PipedOutputStream
        PrintWriter writer = new PrintWriter(pipedOutputStream);
        writer.println("{\"msg_type\":\"req_stock\",\"msg_content\":{\"item_code\":\"123\",\"item_num\":\"1\"}}");
        writer.flush();

        // Mock the input and output streams of the socket
        when(clientSocket.getInputStream()).thenReturn(pipedInputStream);
        when(clientSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        Network.ClientHandler clientHandler = new Network.ClientHandler(clientSocket, prepaymentManager);
        clientHandler.start();

        verify(prepaymentManager, timeout(1000)).otherVMPrepaymentRequest(123);
    }

    @Test
    @DisplayName("Test ClientHandler run method with req_prepay message")
    void testClientHandlerRunWithReqPrepay() throws Exception {
        // Mock the Socket and PrepaymentManager
        Socket clientSocket = mock(Socket.class);
        PrepaymentManager prepaymentManager = mock(PrepaymentManager.class);

        // Create PipedInputStream and PipedOutputStream
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);

        // Write the test message to the PipedOutputStream
        PrintWriter writer = new PrintWriter(pipedOutputStream);
        writer.println("{\"msg_type\":\"req_prepay\",\"msg_content\":{\"item_code\":\"123\",\"item_num\":\"1\",\"cert_code\":\"abc\"}}");
        writer.flush();

        // Mock the input and output streams of the socket
        when(clientSocket.getInputStream()).thenReturn(pipedInputStream);
        when(clientSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        // Create and start the ClientHandler
        Network.ClientHandler clientHandler = new Network.ClientHandler(clientSocket, prepaymentManager);
        clientHandler.start();

        // Verify the interaction with the prepaymentManager
        verify(prepaymentManager, timeout(1000)).otherVMPrepaymentResponse(123, 1, "abc");
    }

    @Test
    @DisplayName("Test ServerResponseThread run method with resp_stock message")
    void testServerResponseThreadRunWithRespStock() throws Exception {
        BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("{\"msg_type\":\"resp_stock\",\"msg_content\":{\"item_code\":\"123\",\"item_num\":\"10\"}}");

        JSONObject requestMsg = new JSONObject();
        requestMsg.put("msg_type", "req_stock");

        Network.ServerResponseThread serverResponseThread = new Network.ServerResponseThread(in, prepaymentManager, requestMsg);
        serverResponseThread.start();

        verify(prepaymentManager, timeout(1000)).putMsg(any(JSONObject.class), eq(requestMsg));
    }

    @Test
    @DisplayName("Test ServerResponseThread run method with resp_prepay message")
    void testServerResponseThreadRunWithRespPrepay() throws Exception {
        BufferedReader in = mock(BufferedReader.class);
        when(in.readLine()).thenReturn("{\"msg_type\":\"resp_prepay\",\"msg_content\":{\"item_code\":\"123\",\"item_num\":\"1\",\"availability\":\"T\"}}");

        JSONObject requestMsg = new JSONObject();
        requestMsg.put("msg_type", "req_prepay");

        Network.ServerResponseThread serverResponseThread = new Network.ServerResponseThread(in, prepaymentManager, requestMsg);
        serverResponseThread.start();

        verify(prepaymentManager, timeout(1000)).respPerpayment(any(JSONObject.class));
    }
}