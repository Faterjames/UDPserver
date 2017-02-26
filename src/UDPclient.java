import java.io.IOException;
import java.net.*;

/**
 * Created by Ruoxu Sun on 2017/2/18.
 */
public class UDPclient {
    public static void main(String args[]) throws IOException {
        InetAddress address = InetAddress.getByName("192.168.1.143");
        int port = 8080;
        String s = "{\n" +
                "\"message\": [\n" +
                "{ \"name\":\"John\" , \"msg\":\"你好\" },\n" +
                "]\n" +
                "}";
        byte[] data = s.getBytes();
        DatagramSocket clientSocket = new DatagramSocket();
        DatagramPacket clientPacket = new DatagramPacket(data,data.length,address,port);
        clientSocket.send(clientPacket);

        byte[] data2 = new byte[1024];
        DatagramPacket serverPacket = new DatagramPacket(data2,data2.length);
        clientSocket.receive(serverPacket);
        String serverInfo = new String(data2,0,serverPacket.getLength());
        System.out.println("server: " + serverInfo);

        clientSocket.close();
    }
}
