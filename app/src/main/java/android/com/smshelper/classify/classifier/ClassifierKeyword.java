package android.com.smshelper.classify.classifier;

import android.com.smshelper.classify.ClassifyCenter;
import android.com.smshelper.manager.KeyWordManager;

import java.util.List;

/**
 * @author yxf
 * @date 5/3/15
 * @time 3:40 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 关键字过滤
 */
public class ClassifierKeyword implements Classifier {

	@Override
	public int classify(String body, String address) {
		List<String> list = KeyWordManager.getInstance().getList();
		for (String s : list) {
			if (checkKeyword(body, s)) {
				return ClassifyCenter.SPAM;
			}
		}
		return ClassifyCenter.WHITE;
	}

	//一个最长字串匹配算法
	private boolean checkKeyword(String body, String key) {
		char[] bodyA = body.toCharArray();
		char[] keyA = key.toCharArray();
		int[][] map = new int[bodyA.length][keyA.length];
		int len = 0;
		for (int i = 0; i < bodyA.length; i++) {
			for (int j = 0; j < keyA.length; j++) {
				if (bodyA[i] == keyA[j]) {
					if (i == 0 || j == 0) {
						map[i][j] = 1;
					} else {
						map[i][j] = map[i - 1][j - 1] + 1;
					}
				} else {
					map[i][j] = 0;
				}
				if (map[i][j] > len) {
					len = map[i][j];
				}
			}
		}
		System.out.println();
		return keyA.length == len;
	}
}
