package android.com.smshelper.entity;

/**
 * Created by admin on 15-1-22.
 */
public class NameValue<K, V> {
	private K mName;
	private V mValue;

	public NameValue(K name, V value) {
		mName = name;
		mValue = value;
	}

	public K getKey() {
		return mName;
	}

	public V getValue() {
		return mValue;
	}
}
