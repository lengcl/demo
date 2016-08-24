package zhihu;

import java.util.ArrayList;

public class main {
 public static void main(String[] args) {
  // 定义即将访问的链接
  String url = "http://www.baidu.com";
  // 访问链接并获取页面内容
  String content = Spider.SendGet(url);
  System.out.println(content);
  // 获取编辑推荐
  ArrayList<Zhihu> myZhihu = Spider.GetRecommendations(content);
  // 打印结果
  //System.out.println(myZhihu);
 }
}
