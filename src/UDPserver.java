import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ruoxu Sun on 2017/2/18.
 */
public class UDPserver {
    private static final ClientMap clientMap = new ClientMap();
    JSONControler jsonControler;
    DatagramSocket ServerSocket;

    public void startUDPserver() {
        try {
            jsonControler = new JSONControler("");
            ServerSocket = new DatagramSocket(8080);
            byte[] data = new byte[1024];

            DatagramPacket packet = new DatagramPacket(data, data.length);
            System.out.println("服务器已经启动");
            ArrayBlockingQueue queue = new ArrayBlockingQueue(10);
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 600000, TimeUnit.DAYS, queue);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (threadPoolExecutor.getActiveCount() == 0) {
                        threadPoolExecutor.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ServerSocket.receive(packet);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                String info = new String(data, 0, packet.getLength());
                                System.out.println(info);
                                jsonControler.setUnKnowString(info);
                                HashMap<String, String> map = jsonControler.getMap();
                                clientMap.addClient(map.get("name"),packet);
                                threadPoolExecutor.execute(new SendMsg(clientMap.getPacketByName(map.get("name")),ServerSocket,map.get("msg")));
                            }
                        });
                    }
                    System.out.println(threadPoolExecutor.getActiveCount());
                }
            }, 3000, 3000);
        } catch (IOException e) {

        }
    }

    public static void main(String args[]) {
        UDPserver server = new UDPserver();
        server.startUDPserver();
        while (true) {

        }


               /* String info = new String(data, 0, packet.getLength());
                System.out.println("client: " + info);

                InetAddress address = packet.getAddress();
                int clientPort = packet.getPort();
                System.out.println(packet.getPort());
                byte[] responseInfo = "hello".getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseInfo, responseInfo.length, address, clientPort);
                socket.send(responsePacket);*/

    }
}
