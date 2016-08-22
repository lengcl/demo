package crawler.timerManager;

import java.util.Timer;

/**
 *  定时任务
 */
public class TimerManager {
	
	public void run() {
		/**10秒后执行，20分钟执行一次*/
		Timer timer = new Timer();
		timer.schedule(new SearchTask(),10*1000l, 1200 * 1000l);
	}
	
}