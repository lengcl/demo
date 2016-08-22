package crawler.timerManager;

import java.util.TimerTask;

import system.LoadConfig;
import crawler.search.SearchBKeyword;
/**
 *  搜索定时器
 */
public class SearchTask extends TimerTask{
	public void run() {
		if(!SearchBKeyword.process){
			/**加载关键词和采集页数*/
			LoadConfig.loadDemoConfig();
			/**调用采集方法*/
			new SearchBKeyword().search();
		}
	}
}
