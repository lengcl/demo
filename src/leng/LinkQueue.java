package leng;

import java.util.HashSet;
import java.util.Set;

public class LinkQueue {
    //collection of used URL
    private static Set<String> visitedUrl = new HashSet<String>();
    //collection of ready-to-visit URL
    private static Queue unVisitedUrl = new Queue();
    //get queue of URL
    public static Queue getUnVisitedUrl() {
        return unVisitedUrl;
    }
    //add the visited URL
    public static void addVisitedUrl(String url) {
        visitedUrl.add(url);
    }
    //remove visited URL
    public static void removeVisitedUrl(String url) {
        visitedUrl.remove(url);
    }
    //pop unvisited URL
    public static Object unVisitedUrlDeQueue() {
        return unVisitedUrl.deQueue();
    }
    //ensure each URL only visited once
    public static void addUnvisitedUrl(String url) {
        if (url != null && !url.trim().equals("")
                && !visitedUrl.contains(url)
                && !unVisitedUrl.contains(url))
            unVisitedUrl.enQueue(url);
    }
    public static int getVisitedUrlNum() {
        return visitedUrl.size();
    }
    //judge the unvisited URL empty or not
    public static boolean unVisitedUrlIsEmpty() {
        return unVisitedUrl.isQueueEmpty();
    }
}
