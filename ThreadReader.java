package Demo01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadReader implements Runnable {


    private InputStream is;
    private Server server;
    private Socket socket;
    public ThreadReader(InputStream is,Server server,Socket socket) {
        this.server=server;
        this.socket=socket;
        this.is = is;
    }

    @Override
    public void run() {

        try {
            while (true) {

                byte[] b = new byte[1024];
                int length = is.read(b);
                String message = new String(b, 0, length);
                System.out.println(Thread.currentThread().getName() + ":" + message);
                server.publish(socket,message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}