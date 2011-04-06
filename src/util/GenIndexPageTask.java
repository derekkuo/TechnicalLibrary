package util;

import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

public class GenIndexPageTask extends TimerTask {
	private static boolean isRunning = false;
	private ServletContext context;
	private List<TopicHeader> allTopicHeader;
	
	public GenIndexPageTask(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		if (!isRunning) {
			isRunning = true;
			System.out.println("生成index.html........");
			allTopicHeader = TopicUtil.getAllTopicHeader( context );
		}
	}

}