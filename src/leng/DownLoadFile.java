package leng;

import java.io.IOException;
import java.net.UnknownHostException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.File;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.config.RequestConfig;

public class DownLoadFile {
 
    //根据URL和网页类型生成需要保存的网页的文件名，去除URL中的非文件名字符
    public String getFileNameByUrl(String url, String contentType) {
        //移除http://
        url=url.substring(7);   //返回从第7个到最后一个字符之间的子串
        //text/html类型
        if (contentType.indexOf("html") != -1) {    //如果是html类型的文本
            url=url.replaceAll("[\\?/:*|<>\"]", "_")+".html";
            return url;
        }
        else {  //如果不是html类型的文本
            return url.replaceAll("[\\?/:*|<>\"]","_")+"."+
                contentType.substring(contentType.lastIndexOf("/")+1);
        }
    }
    //保存网页字节数到本地文件,filepath为要保存文件的相对地址
    private void saveToLocal(HttpEntity entity, String filePath) {
        try {
             
            if(filePath.indexOf("JPG") != -1 || filePath.indexOf("png") != -1
                     || filePath.indexOf("jpeg") != -1) {
                File storeFile = new File(filePath);
                FileOutputStream output = new FileOutputStream(storeFile);
 
                // 得到网络资源的字节数组,并写入文件
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    byte b[] = new byte[1024];
                    int j = 0;
                    while( (j = instream.read(b))!=-1){
                        output.write(b,0,j);
                        }
                    }
                output.flush();
                output.close();
                return;
            }
             
             if (entity != null) {
                    InputStream input = entity.getContent();
                    DataOutputStream output = new DataOutputStream(
                            new FileOutputStream(new File(filePath)));
      
                    int tempByte=-1;
                    while ((tempByte=input.read())>0) {
                        output.write(tempByte);
                    }
      
                    if (input != null) {
                        input.close();
                    }
      
                    if (output != null) {
                        output.close();
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //下载URL指定的网页
    public String downloadFile(String url) throws IOException {
        String filePath = null;
         
        //生成CloseableHttpClient对象并设置参数
        CloseableHttpClient httpclient = HttpClients.createDefault();
         
 
         
        //执行请求
        try {
             
            //生成GetMethod并设置参数
            HttpGet httpget = new HttpGet(url);
             
            //设置请求时间5秒钟
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                .setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
             
            httpget.setConfig(requestConfig);
             
            CloseableHttpResponse response = httpclient.execute(httpget);
            //判断返回状态
            int statusCode = response.getStatusLine().getStatusCode();
             
            //System.out.println("得到的结果:" + response.getStatusLine().getStatusCode());//得到请求结果  
            HttpEntity entity = response.getEntity();//得到请求回来的数据
             
             
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + response.getStatusLine());
                //System.err.println("Method failed: " + getMethod.getStatusLine());
                filePath = null;
            }
            //处理HTTP响应内容
            // read byte array
             
            filePath = "D:\\temp\\"
                    + getFileNameByUrl(url, entity.getContentType().getValue());
 
            saveToLocal(entity, filePath);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal URL!");
        }
         
        catch (UnknownHostException e) {
            // fatal error
            System.out.println("Please check your provided http address!");
        } catch (IOException e) {
            // web error
            e.printStackTrace();
        } finally {
            // realease connection
            httpclient.close();
        }
        return filePath;
    }
    public static void main(String[] args) throws IOException {
        DownLoadFile a = new DownLoadFile();
        String tmp=null;
        tmp = a.downloadFile("http://www.lietu.com");
        System.out.println(tmp);
    }
}
