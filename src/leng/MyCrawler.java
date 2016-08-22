package leng;

import java.io.IOException;
import java.util.Set;
 
public class MyCrawler {
    /**
     * 使用种子初始化URL队列
     * @return
     * @param seeds 种子URL
     */
     
    private void initCrawlerWithSeeds(String[] seeds) {
        for(int i=0; i<seeds.length; i++)
            LinkQueue.addUnvisitedUrl(seeds[i]);
    }
     
    /**
     * @return
     * @param seeds
     * @throws IOException 
     */
    public void crawling(String[] seeds) throws IOException {
        //定义过滤器,提取以http://www.baidu.com开头的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if(url.startsWith("http://www.baidu.com"))
                    return true;
                else
                    return false;
            }
        };
         
        //初始化URL队列
        initCrawlerWithSeeds(seeds);
        //循环条件: 待抓去的链接不空且抓去的网页不多于1000
        while(!LinkQueue.unVisitedUrlIsEmpty() && LinkQueue.getVisitedUrlNum() <= 1000) {
            //队头URL出队列
            String visitUrl = (String)LinkQueue.unVisitedUrlDeQueue();
            if(visitUrl == null)
                continue;
            DownLoadFile downLoader = new DownLoadFile();
            //下载网页
            downLoader.downloadFile(visitUrl);
            System.out.println("下载的网页为: " + visitUrl);
            //该URL放入已访问的URL中
            LinkQueue.addVisitedUrl(visitUrl);
            //提取下载网页中的URL
            Set<String> links = HtmlParserTool.extractLinks(visitUrl, filter);
            //新的未访问的URL入队
            for(String link:links) {
                LinkQueue.addUnvisitedUrl(link);
            }
        }
    }
     
    //main入口方法
    public static void main(String args[]) throws IOException {
        MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[]{"http://www.baidu.com"});
        System.out.println("爬完了\n");
    }
}
