import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Ruoxu Sun on 2017/2/19.
 */
public class SendMsg extends Thread implements Runnable {
    DatagramSocket ServerSocket;
    DatagramPacket clientPacket;

    String msg;
    SendMsg(DatagramPacket p,DatagramSocket s,String m){
        this.clientPacket = p;
        this.ServerSocket = s;
        this.msg = m;
    }
    @Override
    public void run() {
        byte[] message = new byte[0];
        message = msg.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(message,message.length,clientPacket.getAddress(),clientPacket.getPort());
        try {
            ServerSocket.send(responsePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
