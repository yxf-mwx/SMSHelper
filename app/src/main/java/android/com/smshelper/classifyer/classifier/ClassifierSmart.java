package android.com.smshelper.classifyer.classifier;

import android.com.smshelper.classifyer.ClassifyCenter;
import android.com.smshelper.manager.SmartKeyListManager;
import android.content.Context;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yxf
 * @date 5/7/15
 * @time 9:34 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 智能分类的类
 */
public class ClassifierSmart implements Classifier {
	private Context mContext;

	public ClassifierSmart(Context context) {
		mContext = context;
	}

	@Override
	public int classify(final String body, String address) {
		List<String> tokens = divide(body);
		Map<String, Integer> spamReflect = SmartKeyListManager.getInstance().getSpamReflect();
		Map<String, Integer> nonSpamReflect = SmartKeyListManager.getInstance().getNonSpamReflect();
		List<Double> spamRadioList = new ArrayList<>();
		List<Double> nonSpamRadioList = new ArrayList<>();
		for (String token : tokens) {
			final Integer countSpam = spamReflect.get(token);
			final double spamRadio = countSpam * 1.0 / spamReflect.size();
			spamRadioList.add(spamRadio);
			final Integer countNonSpam = nonSpamReflect.get(token);
			final double nonSpamRadio = countNonSpam * 1.0 / spamReflect.size();
			nonSpamRadioList.add(nonSpamRadio);
		}

		return ClassifyCenter.WHITE;
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



}
