package leng;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
 
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
 
 
 
public class HtmlParserTool {
    //获取一个网站上的链接,filter用来过滤链接
    public static Set<String> extractLinks(String url, LinkFilter filter) {
        Set<String> links = new HashSet<String>();
        try {
            Parser parser = new Parser(url);
            parser.setEncoding("utf-8");
            //过滤<frame>标签的filter,用来提取frame标签里的src属性
            NodeFilter frameFilter = new NodeFilter() {
                /**
                 * 
                 */
                private static final long serialVersionUID = 1L;
 
                public boolean accept(Node node) {
                    if (node.getText().startsWith("frame src=")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            };
             
            OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class), frameFilter);
            //得到所有经过过滤的标签
            NodeList list = parser.extractAllNodesThatMatch(linkFilter);
            for (int i=0; i<list.size(); i++) {
                Node tag = list.elementAt(i);
                if (tag instanceof LinkTag) {
                    LinkTag link = (LinkTag)tag;
                    String linkUrl = link.getLink();
                    if (filter.accept(linkUrl))
                        links.add(linkUrl);
                }
                else {
                    String frame = tag.getText();
                    int start = frame.indexOf("src=");
                    frame = frame.substring(start);
                    int end = frame.indexOf(" ");
                    if (end == -1)
                        end = frame.indexOf(">");
                    String frameUrl = frame.substring(5, end-1);
                    if (filter.accept(frameUrl))
                        links.add(frameUrl);
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return links;
    }
     
    public static void main(String args[]) {
        System.out.println("This is a test for main function!");
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if(url.startsWith("http://www.lietu.com"))
                    return true;
                else
                    return false;
            }
        };
        Set<String> links = HtmlParserTool.extractLinks("http://www.lietu.com", filter);
         
        Iterator<String> it = links.iterator();
        while (it.hasNext()) {
            String str = it.next();
            System.out.println(str);
        } 
    }
}
