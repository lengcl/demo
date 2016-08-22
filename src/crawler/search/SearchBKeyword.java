package crawler.search;

import java.net.URLEncoder;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 *  搜索关键字
 */
public class SearchBKeyword {
	
	public static Logger log4j = Logger.getLogger("");
	
	/** 连接网站，模拟浏览器登陆，避免网站识别为手机进入 */
	private static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0";
	
	/**设置超时时间 */
	private static final int timeout=50000;
	
	private static  String url="http://www.baidu.com";
	
	public static LinkedBlockingQueue<String> keywordList = new LinkedBlockingQueue<String>();
	
	public static int pagesize;
	
	public static boolean process=false;
	
	String keyword="";
	
	public void search() {
		process=true;
		int count=keywordList.size();
		for (int i = 0; i < count; i++) {
			keyword=keywordList.poll();
			for (int y = 0; y < pagesize; y++) {
				try {
					url="http://www.baidu.com/s?wd="+URLEncoder.encode(keyword, "UTF-8")+"&pn="+(y*10)+"&oq="+URLEncoder.encode(keyword, "UTF-8")+"&tn=baidu&ie=utf-8&usm=8";
					Document doc=Jsoup.connect(url).userAgent(USERAGENT).timeout(timeout).get();
					Elements elements=doc.select("#content_left .c-container");//div class="f13"
					System.out.println("111"+elements);
					if(elements!=null&&elements.size()>0){
						for (Element e:elements) {
							FileReaderWriter.writeIntoFile(e.select("h3.t a").attr("abs:href"),  
				                    "D:/爬虫.txt", true); 
							FileReaderWriter.writeIntoFile(e.select("h3.t a").text(),  
				                    "D:/爬虫.txt", true);
//							log4j.info(new String("地址："+e.select("h3.t a").attr("abs:href")));
//							log4j.info(new String("标题："+e.select("h3.t a").text()));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		}
		process=false;
	}
	public static void main(String[] args) {
		new SearchBKeyword().search();
	}
}
