package org.jbpm.ee.support;

public class StringUtils {

	public static boolean isBlank(String str) {
		return (str == null || str.length()==0);
	}
	
	public static String defaultOnEmpty(String str, String dft) {
		if(isBlank(str)) {
			return dft;
		}
		return str;
	}
}
