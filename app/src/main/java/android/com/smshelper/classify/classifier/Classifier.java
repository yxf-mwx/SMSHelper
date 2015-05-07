package android.com.smshelper.classify.classifier;

/**
 * @author yxf
 * @date 4/30/15
 * @time 9:13 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 过滤器通用接口
 */
public interface Classifier {
	/**
	 * @param body    短信内容
	 * @param address 短信的发送号码
	 * @return 分类的特征码
	 */
	public int classify(String body, String address);
}
