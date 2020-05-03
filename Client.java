import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Thread {
    //Define a Socket object
    Socket socket = null;

    public Client(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
    }

    @Override
    public void run() {
        new sendMessThread().start();
        super.run();
        try {
            InputStream s = socket.getInputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = s.read(buf)) != -1) {
                System.out.println(new String(buf, 0, len));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class sendMessThread extends Thread{
        @Override
        public void run() {
            super.run();
            System.out.println("Please enter number");
            
            Scanner scanner=null;
            OutputStream os= null;
            try {
                scanner=new Scanner(System.in);
                os= socket.getOutputStream();
                String in="";
                do {
                    in=scanner.next();
                    try {
                        Integer.valueOf(in);
                    }catch(Exception e) {
                        System.out.println("Please enter number");
                        continue;
                    }
                    
                    
                    os.write((""+in).getBytes());
                    os.flush();
                } while (!in.equals("bye"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            scanner.close();
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        try {
            Client  client = new Client("netprog1.csit.rmit.edu.au", 61118);
            System.out.println("The client connection successful");
            client.start();
        } catch (UnknownHostException e) {
            System.err.println("connetion error");
        } catch (IOException e) {
            System.err.println("connetion error");
        }
       
    }
}
