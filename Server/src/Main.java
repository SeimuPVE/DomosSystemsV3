import clientManager.ClientManager;
import msc.Server;


public class Main {
    public static void main(String[] argv) {
        Server server = new Server();
        server.run(argv);

//        ClientManager.run(argv);
    }
}
