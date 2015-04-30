package android.com.smshelper.manager;

/**
 * @author yxf
 * @date 4/30/15
 * @time 9:12 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 过滤器的管理类
 */
public class ClassifyManager {
	private static ClassifyManager instance;


	public synchronized static ClassifyManager getInstance() {
		if (instance == null) {
			instance = new ClassifyManager();
		}
		return instance;
	}

	private ClassifyManager() {

	}

	public void classify() {

	}
}
