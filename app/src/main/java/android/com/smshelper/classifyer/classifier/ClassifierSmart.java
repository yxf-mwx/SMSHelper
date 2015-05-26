package android.com.smshelper.classifyer.classifier;

import android.com.smshelper.classifyer.ClassifyCenter;
import android.com.smshelper.manager.SmartKeyListManager;
import android.content.Context;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yxf
 * @date 5/7/15
 * @time 9:34 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 智能分类的类
 */
public class ClassifierSmart implements Classifier {
	private final static Double LAMBDA = 0.0000001;
	private Context mContext;

	public ClassifierSmart(Context context) {
		mContext = context;
	}

	@Override
	public int classify(final String body, String address) {
		List<String> tokens = divide(body);

		//分词属性映射表
		Map<String, Integer> spamReflect = SmartKeyListManager.getInstance().getSpamReflect();
		Map<String, Integer> nonSpamReflect = SmartKeyListManager.getInstance().getNonSpamReflect();
		//分词属性概率列表
		List<Double> spamRadioList = new ArrayList<>();
		List<Double> nonSpamRadioList = new ArrayList<>();

		//计算两个分词列表的大小
		final double spamSize = calculateMapSize(spamReflect);
		final double nonSpamSize = calculateMapSize(nonSpamReflect);
		//计算两个列表对应的概率
		for (String token : tokens) {
			final Integer countSpam = spamReflect.get(token);
			final double spamRadio = (countSpam + LAMBDA) / spamSize;
			spamRadioList.add(spamRadio);
			final Integer countNonSpam = nonSpamReflect.get(token);
			final double nonSpamRadio = (countNonSpam + LAMBDA) / nonSpamSize;
			nonSpamRadioList.add(nonSpamRadio);
		}

		//计算垃圾邮件的概率
		final double spamRadio = calculateSpamRadio(spamRadioList, nonSpamRadioList);
		return spamRadio > 0.5 ? ClassifyCenter.SPAM : ClassifyCenter.WHITE;
	}

	private List<String> divide(String body) {
		List<String> tokens = new ArrayList<>();
		StringReader sr = new StringReader(body);
		IKSegmenter iks = new IKSegmenter(sr, true);
		Lexeme lex = null;
		try {
			while ((lex = iks.next()) != null) {
				tokens.add(lex.getLexemeText());
			}
			return tokens;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sr != null) {
				sr.close();
			}
		}
		return null;
	}

	private double calculateMapSize(Map<String, Integer> mapSpam) {
		double result = 0;
		Set<Map.Entry<String, Integer>> entitySet = mapSpam.entrySet();
		Iterator<Map.Entry<String, Integer>> iterator = entitySet.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> entry = iterator.next();
			result += entry.getValue();
			result += LAMBDA;
		}
		return result;
	}

	private double calculateSpamRadio(List<Double> spamRadioList, List<Double> nonSpamRadioList) {
		double up = 0.0;
		double down = 0.0;
		for (double d : spamRadioList) {
			up *= d;
		}
		for (double d : nonSpamRadioList) {
			down *= d;
		}
		return up / (down + up);
	}

}
