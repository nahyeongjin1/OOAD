import org.chat6.Network;
import org.chat6.PrepaymentManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NetworkTest {

    @Mock
    private PrepaymentManager prepaymentManager;

    @InjectMocks
    private Network network;

    @Test
    public void testServerConnection() throws IOException {
        // ServerSocket을 mock으로 만들어서 실제 네트워크 연결을 피합니다.
        ServerSocket serverSocket = mock(ServerSocket.class);
        Socket clientSocket = mock(Socket.class);

        when(serverSocket.accept()).thenReturn(clientSocket);

        // Network 클래스의 run 메서드를 스레드가 아닌 직접 호출하여 테스트합니다.
        new Thread(() -> {
            try {
                network.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 테스트를 위한 딜레이 추가
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verify(serverSocket, atLeastOnce()).accept();
        verify(clientSocket, atLeastOnce()).close();
    }
}