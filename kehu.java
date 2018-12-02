package Demo01;


import sun.plugin2.message.Message;
import sun.security.util.Length;

import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.net.Socket;
import java.util.Scanner;

public class kehu {
    private int port = 5000;
    private String ip = "127.0.0.1";
    private static Socket socket;
    private String KehuName;

    public kehu() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception {
        System.out.println("-----客户端已进入-----");
        System.out.println("请输入姓名：");
        Scanner sc = new Scanner(System.in);
        KehuName = sc.next();
        socket = new Socket(ip, port);
        OutputStream out = socket.getOutputStream();
        byte[] bytes = KehuName.getBytes();
        out.write(bytes);
        out.flush();

        hands();
        writer();
    }

    public void writer() throws IOException {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println(KehuName+"说：");
            String Message= sc.next();
            OutputStream out = socket.getOutputStream();
            byte[] bytes = Message.getBytes();
            out.write(bytes);
            out.flush();
        }
    }

    public void hands() throws Exception {
        Thread threadReader = new Thread(new Client_Thread_Reader(socket.getInputStream()), Thread.currentThread().getName());
        threadReader.start();
    }

    public static void main(String[] args) throws Exception {
        kehu kehu = new kehu();
        kehu.hands();
    }
}
