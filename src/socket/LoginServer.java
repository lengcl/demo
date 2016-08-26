package socket;

import java.io.BufferedReader;  
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.io.PrintWriter;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
  



public class LoginServer {  
	
	private static int TOTAL_R_NUM = 0;
	
    public static void main(String[] args) {  
    	String[] TOTAL_R = {"201601011212","201601011213", "201601011214",
    			"201601011215","201601011216","201601011217",
    			"201601011218","201601011219","201601011220","201601011221"};
    	String[] BASE_R = {"201601011212","201601011213", "201601011214",
    			"201601011215","201601011216","201601011217",
    			"201601011218","201601011219","201601011220","201601011221"};
    	String[] a = null;
    	List<String> list = new ArrayList<String>();
    	String reply = null;
        BufferedReader base;
    	try {  
            //1.建立一个服务器Socket(ServerSocket)绑定指定端口  
            ServerSocket serverSocket=new ServerSocket(8800);  
            //2.使用accept()方法阻止等待监听，获得新连接  
            Socket socket=serverSocket.accept();  
            //3.获得输入流  
            InputStream is=socket.getInputStream();  
            BufferedReader br=new BufferedReader(new InputStreamReader(is));  
            //获得输出流  
            OutputStream os=socket.getOutputStream();  
            PrintWriter pw=new PrintWriter(os);  
            //4.读取用户输入信息  
            String info=null;  
            while(!((info=br.readLine())==null)){ 
            	a = info.split(":");
            	if(a[0].equals("TOTAL_R")){
            		for(int i = 0 ;i<TOTAL_R.length;i++){
            			if(TOTAL_R[i].toString().compareTo(a[1])>0)
            			TOTAL_R_NUM++;
            			reply=a[0]+":"+TOTAL_R_NUM;
            			//System.out.println(TOTAL_R_NUM);
            		}
            	}else if(a[0].equals("AutoCheck")){
            		reply = info;
            	}else if(a[0].equals("METER_TEST")){
            		reply = info;
            	}else if(a[0].equals("METER_READ")){
            		reply = info;
            	}else if(a[0].equals("BASE_R")){
            		if(a[0].equals("BASE_R")){
            			for(int i = 0;i<BASE_R.length;i++){
            				if(BASE_R[i].toString().compareTo(a[1])>0){
            					list.add(BASE_R[i]);
            				}
            			}
            		}
            		if(list.size()>0){
            			File file = new File("D:/BASE_TEMP.txt");
            			FileWriter fw = null;
            	        BufferedWriter bw = null;
            	        Iterator<String> iter = list.iterator();
            	        try {
            	            fw = new FileWriter(file);
            	            bw = new BufferedWriter(fw);
            	            while(iter.hasNext()) {
            	                bw.write(iter.next());
            	                bw.newLine();
            	            }
            	            bw.flush();
            	        } catch (Exception e) {
            	            e.printStackTrace();
            	        } finally {
            	            try {
            	                bw.close();
            	                fw.close();
            	            } catch (Exception e) {
            	                e.printStackTrace();
            	            }
            	        }
            			reply = "BASE_R:"+list.get(0).toString();
            		}else{
            			reply = "LAST_D:01";
            		}
            		
            		
            	}else if(a[0].equals("REP_D")){
            		File file = new File("D:/BASE_TEMP.txt");
            		FileWriter fw = null;
        	        BufferedWriter bw = null;
            		FileReader fr = null;
                    BufferedReader br1 = null;
                    try {
                        fr = new FileReader(file);
                        br1 = new BufferedReader(fr);
                        String line = "";
                        while((line = br1.readLine()) != null) {
                            list.add(line);
                        }
                        if(list.size()==0){
                        	reply = "LAST_D:01";
                        }else{
                        	list.remove(0);
                        	Iterator<String> iter = list.iterator();
							try {
                	            fw = new FileWriter(file);
                	            bw = new BufferedWriter(fw);
                	            while(iter.hasNext()) {
                	                bw.write(iter.next());
                	                bw.newLine();
                	            }
                	            bw.flush();
                	        } catch (Exception e) {
                	            e.printStackTrace();
                	        } finally {
                	            try {
                	                bw.close();
                	                fw.close();
                	            } catch (Exception e) {
                	                e.printStackTrace();
                	            }
                	        }
							reply = "BASE_R:"+list.get(0).toString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            br1.close();
                            fr.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            		
            	}
                System.out.println("我是服务器，用户信息为："+info);  
            } 
            //给客户一个响应  
            pw.write(reply);  
            pw.flush();  
            //5.关闭资源  
            pw.close();  
            os.close();  
            br.close();  
            is.close();  
            socket.close();  
            serverSocket.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }      
    }  
}  
