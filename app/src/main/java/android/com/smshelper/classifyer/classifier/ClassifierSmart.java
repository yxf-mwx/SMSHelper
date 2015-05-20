package android.com.smshelper.classifyer.classifier;

import android.content.Context;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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
		List<String> tokens = new ArrayList<>();
		StringReader sr = new StringReader(body);
		IKSegmenter iks = new IKSegmenter(sr, true);
		Lexeme lex = null;
		try {
			while ((lex = iks.next()) != null) {
				tokens.add(lex.getLexemeText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sr != null) {
				sr.close();
			}
		}
//		Map<String, Integer> spamReflect = SmartKeyListManager.getInstance().getSpamReflect();
//		Map<String, Integer> nonSpamReflect = SmartKeyListManager.getInstance().getNonSpamReflect();
		List<Double> spamRadioList = new ArrayList<>();
		for (String token : tokens) {
//			final Integer count = spamReflect.get(token);
			System.out.println(token);
//			final double radio = count * 1.0 / spamReflect.size();
//			spamRadioList.add(radio);
		}

		return 5;
	}

}
