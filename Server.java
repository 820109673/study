package Demo01;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;


public class Server {
    private int duankouhao=5000;//端口号
    private ServerSocket server;//自定义服务器
    private static Socket socket;

    private Server(){
        try{
            init();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void  init()throws IOException{
        ServerSocket server = new ServerSocket(duankouhao);
        System.out.println("-----服务器已经准备就绪");
        while (true){
            socket=server.accept();
            add(socket);
            hands(socket);
        }
    }
    private void hands(Socket socket) throws IOException {
        Thread threadReader = new Thread(new ThreadReader(socket.getInputStream(),this,socket));
        Thread threadWriter = new Thread(new ThreadWriter(socket.getOutputStream()));
        threadReader.start();
        threadWriter.start();
    }

    Vector<Socket> vector = new Vector<Socket>();

    public void add(Socket cs){
        vector.add(cs);
    }

    public void publish(Socket cs, String msg) throws IOException {
        for (int i = 0; i < vector.size(); i++) {
            Socket s = vector.get(i);
            if(s!=cs){
                byte[] bytes = msg.getBytes();
                s.getOutputStream().write(bytes);
            }
        }

        /*for (int i = 0; i < vector.size(); i++) {
            Socket csTemp = vector.get(i);
            if (!cs.equals(csTemp)) {
                csTemp.out(msg+"\n");//不用发送给自己。
            }
        }*/
    }
    public static void main(String[] args) {
        Server server = new Server();
    }
}
