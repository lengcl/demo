package leng;

import java.io.*;
import java.net.*;

public class QiuckStart {
 static String sendGet(String url) {
  // ����һ���ַ��������洢��ҳ����
  String result = "";
  // ����һ�������ַ�������
  BufferedReader in = null;
  try {
   // ��stringת��url����
   URL realUrl = new URL(url);
   // ��ʼ��һ�����ӵ��Ǹ�url������
   URLConnection connection = realUrl.openConnection();
   // ��ʼʵ�ʵ�����
   connection.connect();
   // ��ʼ�� BufferedReader����������ȡURL����Ӧ
   in = new BufferedReader(new InputStreamReader(
     connection.getInputStream()));
   // ������ʱ�洢ץȡ����ÿһ�е�����
   String line;
   while ((line = in.readLine()) != null) {
    // ����ץȡ����ÿһ�в�����洢��result����
    result += line;
   }
  } catch (Exception e) {
   System.out.println("����GET��������쳣��" + e);
   e.printStackTrace();
  }
  // ʹ��finally���ر�������
  finally {
   try {
    if (in != null) {
     in.close();
    }
   } catch (Exception e2) {
    e2.printStackTrace();
   }
  }
  return result;
 }
 public static void main(String[] args) {
  // ���弴�����ʵ�����
  String url = "http://www.baidu.com";
  // �������Ӳ���ȡҳ������
  String result = sendGet(url);
  System.out.println(result);
 }
}