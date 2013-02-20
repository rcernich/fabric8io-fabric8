package org.fusesource.fabric.internal;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataStoreHelpers {
	public static byte[] toBytes(Properties source) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		source.store(baos, null);
		return baos.toByteArray();
	}

	public static Properties toProperties(byte[] source) throws IOException {
		Properties rc = new Properties();
		if (source != null) {
			rc.load(new ByteArrayInputStream(source));
		}
		return rc;
	}

	public static Map<String, String> toMap(Properties source) {
		Map<String, String> rc = new HashMap<String, String>();
		for (Map.Entry<Object, Object> entry : source.entrySet()) {
			rc.put((String) entry.getKey(), (String) entry.getValue());
		}
		return rc;
	}

	public static Properties toProperties(Map<String, String> source) {
		Properties rc = new Properties();
		for (Map.Entry<String, String> entry : source.entrySet()) {
			rc.put(entry.getKey(), entry.getValue());
		}
		return rc;
	}

	public static String stripSuffix(String value, String suffix) throws IOException {
		if (value.endsWith(suffix)) {
			return value.substring(0, value.length() - suffix.length());
		} else {
			return value;
		}
	}

	public static String toString(Properties source) throws IOException {
		StringWriter writer = new StringWriter();
		source.store(writer, null);
		return writer.toString();
	}

	public static Properties toProperties(String source) throws IOException {
		Properties rc = new Properties();
		rc.load(new StringReader(source));
		return rc;
	}
}
