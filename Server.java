import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server extends Thread {
    int gn=0;
    int gc=0;
    ServerSocket server = null;
    Socket socket = null;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        super.run();
        try {
            socket = server.accept();
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                gc++;
                String g = new String(buf, 0, len);
                int n = Integer.valueOf(g);
                String message = "Congratulation!";
                if(gc==4) {
                    message = "numbser is:" + gn;
                    gc=0;
                    Random random = new Random();
                    gn = random.nextInt(12);
                } else if(n>gn) {
                    message = "The client’s guess number X is bigger than the generated number";
                } else if(n<gn) {
                    message = "The client’s guess number X is smaller than the generated number";
                } else if(n==gn) {
                    gc=0;
                    Random random = new Random();
                    gn = random.nextInt(12);
                }
                out.write((message).getBytes());
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        int gn = random.nextInt(12);
        Server s = new Server(61118);
        s.gn=gn;
        s.start();
    }
}
