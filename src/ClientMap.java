import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;

/**
 * Created by Ruoxu Sun on 2017/2/19.
 */
public class ClientMap {
    public HashMap<String,DatagramPacket> map = new HashMap<>();/* name ----> map ------>include{ip address, port } */


    public void addClient(String Name,DatagramPacket packet){
        map.put(Name,packet);
    }

    public DatagramPacket getPacketByName(String name){
        return map.get(name);
    }
}
