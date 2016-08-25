package socket;

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.Socket;  
import java.util.Scanner;
  
public class MyClient {  
  
    public static void main(String[] args) {  
        try {  
            Socket client = new Socket("localhost", 6666);  
            MyClient me = new MyClient();  
            new Thread(me.new Handler(client)).start();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    class Handler implements Runnable {  
        private BufferedReader br;  
        private DataOutputStream dos;  
        private DataInputStream dis;  
        private Socket socket;  
        private boolean flag = true; // 用于控制循环结束  
  
        public Handler(Socket s) throws IOException { 
        	
        	
        	this.br = new BufferedReader(new InputStreamReader(System.in)); // 用于从控制台接受输入的信息，再发送到服务器  
            this.socket = s;  
            this.dos = new DataOutputStream(this.socket.getOutputStream()); // 向服务器写数据的输出流  
            this.dis = new DataInputStream(this.socket.getInputStream()); // 获取服务器返回数据的输入流  
        }  
  
        @Override  
        public void run() {  
            while (flag) {  
                try {  
                    String str = br.readLine(); 
//                    String anw = dis.readUTF();
//                    System.out.println(anw);
                    if ("exit".equals(str)) { // 客户端终止发送信息标记 exit  
                        this.br.close();   
                        this.dos.writeUTF(str);  
                        this.dos.flush();  
                          
                        String res = dis.readUTF();  
                        System.out.println(res);  
  
                        this.dis.close();  
                        this.dos.close();  
                        this.flag = false;  
                    } else {  
                        this.dos.writeUTF(str);// 每读一行就发送一行  
                        this.dos.flush();  
                    }  
  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
}  